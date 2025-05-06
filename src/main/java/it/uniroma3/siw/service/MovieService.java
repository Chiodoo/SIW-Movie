package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired  //Fa in modo di instanziare un oggetto di una classe che implementa questa interfaccia, in modo che non ci pensi io (lo fa il framework)
    private MovieRepository movieRepository;

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).get();
    }

    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    public void addDirectorToMovie(Long movieId, Long directorId) {
        movieRepository.addDirectorToMovie(movieId, directorId);
    }

    public void addActorToMovie(Long movieId, Long actorId) {
        movieRepository.addActorToMovie(movieId, actorId);
    }
}
