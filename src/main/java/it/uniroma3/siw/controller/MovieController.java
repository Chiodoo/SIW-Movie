package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;






@Controller
public class MovieController {

    @Autowired MovieService movieService;       //Mi crei l'oggetto e me lo metti a disposizione automaticamente


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
    public String newMovie(@ModelAttribute("movie") Movie movie) {
        this.movieService.save(movie);
        return "redirect:/movie/"+movie.getId();
    }
}