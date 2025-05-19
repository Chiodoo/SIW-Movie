package it.uniroma3.siw.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;
//import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//public  class WebSecurityConfig {
	public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

//     @Bean
//     protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception{
//         httpSecurity
//                 .csrf(withDefaults()).cors(cors -> cors.disable())
//                 .authorizeHttpRequests(requests -> requests
// //                .requestMatchers("/**").permitAll()
//                         // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
//                         .requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/css/**", "/images/**", "favicon.ico").permitAll()
//                         // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
//                         .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
//                         .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
//                         .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
//                         // tutti gli utenti autenticati possono accere alle pagine rimanenti 
//                         .anyRequest().authenticated()).formLogin(login -> login
//                 .loginPage("/login")
//                 .permitAll()
//                 .defaultSuccessUrl("/success", true)
//                 .failureUrl("/login?error=true"))
//                 .logout(logout -> logout
//                         // il logout è attivato con una richiesta GET a "/logout"
//                         .logoutUrl("/logout")
//                         // in caso di successo, si viene reindirizzati alla home
//                         .logoutSuccessUrl("/")
//                         .invalidateHttpSession(true)
//                         .deleteCookies("JSESSIONID")
//                         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                         .clearAuthentication(true).permitAll());
//         return httpSecurity.build();
//     }

@Bean
protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrf -> csrf.disable())  // Disabilita CSRF solo se necessario (form non sicuri)
        .cors(cors -> cors.disable())
        .authorizeHttpRequests(requests -> requests
            // Pagine pubbliche e risorse statiche
            .requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/login", "/css/**", "/images/**", "/favicon.ico", "/js/**", "/webjars/**").permitAll()
            // Registrazione e login aperti a tutti (POST)
            .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
            // Area amministrativa solo per admin
            .requestMatchers("/admin/**").hasAuthority(ADMIN_ROLE)
            // Tutte le altre richieste devono essere autenticate
            .anyRequest().authenticated()
        )
        .formLogin(login -> login
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/success", true)
            .failureUrl("/login?error=true")
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .clearAuthentication(true)
            .permitAll()
        );

    return httpSecurity.build();
}

}