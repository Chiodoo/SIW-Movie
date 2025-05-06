package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Movie;
import jakarta.transaction.Transactional;

public interface MovieRepository extends CrudRepository<Movie, Long>{


    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.director.id = :artistId WHERE m.id = :movieId AND m.director IS NULL")
    public void addDirectorToMovie(Long movieId, Long artistId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO movie_actors VALUES (:artistId, :movieId)", nativeQuery = true)
    public void addActorToMovie(Long movieId, Long artistId);
}
//Ora abbiamo a disposizione i metodi CrudRepository