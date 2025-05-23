package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.service.ArtistService;
import it.uniroma3.siw.service.MovieService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;






@Controller
public class MovieController {

    @Autowired MovieService movieService;       //Mi crei l'oggetto e me lo metti a disposizione automaticamente

    @Autowired ArtistService artistService;


    //Metto a disposizione di chi genera la risposta un metodo (secondo parametro) per ricevere la risposta, 
    //che metto dentro alla variabile "movie" (o "movies") che passo come primo parametro
    
    @GetMapping("/movie/{id}")
    public String getMovie(@PathVariable("id") Long id, Model model) {
        model.addAttribute("movie", this.movieService.getMovieById(id));    
        return "movie.html";
    }

    @GetMapping("/movie")
    public String showMovies(Model model) {
        model.addAttribute("movies", this.movieService.getAllMovies());
        return "movies.html";
    }
    
    @GetMapping("/formNewMovie")
    public String formNewMovie(Model model) {
        model.addAttribute("movie", new Movie());
        return "formNewMovie.html";
    }
    @PostMapping("/movie")
    public String newMovie(@Valid @ModelAttribute("movie") Movie movie,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {         //Sono emersi errori nel binding
            model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
            return "formNewMovie.html";
        } else {
            this.movieService.save(movie);
            model.addAttribute("movie", movie);
            return "redirect:/movie/"+movie.getId();
        }
    }

    @GetMapping("/aggiornaFilm")
    public String homeAggiornaFilm(Model model) {
        model.addAttribute("movies",this.movieService.getAllMovies());
        return "aggiornaFilm";
    }

    @GetMapping("/cancellaFilm/{id}")
    public String deleteMovie(@PathVariable("id") Long id, Model model) {
        this.movieService.deleteById(id);
        return "redirect:/aggiornaFilm";
    }


    @GetMapping("/modificaFilm/{id}")
    public String modificaFilm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("movie", this.movieService.getMovieById(id));
        return "modificaFilm.html";
    }

    @GetMapping("/registaPerFilm/{id}")
    public String aggiungiRegistaPerFilm(@PathVariable("id") Long id, Model model){
        model.addAttribute("artists", this.artistService.getAllArtists());
        model.addAttribute("movie",this.movieService.getMovieById(id));
        return "registaPerFilm.html";
    }

    @GetMapping("/aggiungiRegistaFilm/{movieId}/{artistId}")
    public String aggiungiRegista(@PathVariable("movieId") Long movieId, @PathVariable("artistId") Long artistId, Model model) {
        this.movieService.addDirectorToMovie(movieId, artistId);
        model.addAttribute("movie",this.movieService.getMovieById(movieId));
        return"redirect:/modificaFilm/" + movieId;
    }

    @GetMapping("/aggiornaAttori/{id}")
    public String aggiornaAttoriPerFilm(@PathVariable("id") Long id, Model model) {
        Movie movie=this.movieService.getMovieById(id);
        model.addAttribute("movie",movie);
        model.addAttribute("attoriFilm", movie.getActors());
        List<Artist> liberi = (List<Artist>) this.artistService.getAllArtists(); //Tutti gli artisti presenti nel sistema
        liberi.removeAll(movie.getActors()); //Attori che non sono già associati a nessun film
        model.addAttribute("attoriLiberi", liberi);
        return "aggiornaAttori";
    }

    @GetMapping("/aggiungiAttoreAFilm/{movieId}/{artistId}")
    public String aggiungiAttore(@PathVariable("movieId") Long movieId, @PathVariable("artistId") Long artistId, Model model) {
        this.movieService.addActorToMovie(movieId, artistId);
        Movie movie=this.movieService.getMovieById(movieId);
        model.addAttribute("movie",movie);
        model.addAttribute("attoriFilm", movie.getActors());
        List<Artist> liberi = (List<Artist>) this.artistService.getAllArtists(); //Tutti gli artisti presenti nel sistema
        liberi.removeAll(movie.getActors()); //Attori che non sono già associati a nessun film
        model.addAttribute("attoriLiberi", liberi);
        return "redirect:/aggiornaAttori/"+movieId;
    }
}