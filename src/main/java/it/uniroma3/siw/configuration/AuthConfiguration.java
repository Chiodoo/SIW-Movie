package it.uniroma3.siw.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import it.uniroma3.siw.model.Credentials;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    // Configurazione globale dell'autenticazione (connessione al database per utenti e ruoli)
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            // Query per ottenere i ruoli dell'utente (ruolo direttamente dal database)
            .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
            // Query per ottenere le credenziali dell'utente
            .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    // Definizione del PasswordEncoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configurazione della sicurezza HTTP
    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) 
            throws Exception {
        httpSecurity
            // Configurazione di CSRF (abilitato di default) e CORS (disabilitato)
            .csrf(withDefaults()).cors(cors -> cors.disable())
            .authorizeHttpRequests(requests -> requests
                // Chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                .requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/css/**", "/images/**", "favicon.ico").permitAll()
                // Chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
                .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                // Solo gli utenti con ruolo ADMIN possono accedere alle pagine admin
                .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority(Credentials.ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority(Credentials.ADMIN_ROLE)
                // Tutti gli utenti autenticati possono accedere alle pagine rimanenti 
                .anyRequest().authenticated()
            )
            // Configurazione del login
            .formLogin(login -> login
                .loginPage("/login")       // Pagina di login personalizzata
                .permitAll()               // Accessibile a chiunque
                .defaultSuccessUrl("/success", true)  // Pagina di successo dopo il login
                .failureUrl("/login?error=true")      // Pagina di errore in caso di login fallito
            )
            // Configurazione del logout
            .logout(logout -> logout
                .logoutUrl("/logout")                  // Il logout è attivato con una richiesta GET a "/logout"
                .logoutSuccessUrl("/")                 // In caso di successo, si viene reindirizzati alla home
                .invalidateHttpSession(true)           // Invalidazione della sessione
                .deleteCookies("JSESSIONID")           // Eliminazione del cookie di sessione
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Matcher per la richiesta di logout
                .clearAuthentication(true)             // Pulizia dell'autenticazione
                .permitAll()                           // Accessibile a chiunque
            );
        return httpSecurity.build();
    }
}
