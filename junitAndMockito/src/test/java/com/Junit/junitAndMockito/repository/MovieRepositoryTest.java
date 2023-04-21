package com.Junit.junitAndMockito.repository;

import com.Junit.junitAndMockito.model.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    private Movie avatarMovie;
    private Movie titanicMovie;
    @BeforeEach
    void setupBeforeEach(){
         avatarMovie=new Movie();
        avatarMovie.setName("avatar");
        avatarMovie.setGenera("action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));


         titanicMovie=new Movie();
        titanicMovie.setName("titanic");
        titanicMovie.setGenera("romance");
        titanicMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));


    }
    @Test
    @DisplayName("check jpa save")
    void save(){


        Movie newMovie=movieRepository.save(avatarMovie);

        assertNotNull(newMovie);
        assertThat(newMovie.getId()).isNotEqualTo(null);

    }

    @Test
    void getAllMovies(){


        movieRepository.save(avatarMovie);

        movieRepository.save(titanicMovie);

        List<Movie>allMovies= movieRepository.findAll();

        assertNotNull(allMovies);
        assertEquals(2,allMovies.size());


    }

    @Test
    void movieById(){

        movieRepository.save(avatarMovie);

        Movie movie1=movieRepository.findById(avatarMovie.getId()).get();

        assertNotNull(movie1);
        assertEquals("action",movie1.getGenera());
    }

    @Test
    void updateMovie(){

        movieRepository.save(avatarMovie);

        Movie movie1=movieRepository.findById(avatarMovie.getId()).get();

        movie1.setGenera("adventure");
        movie1.setName("fast and furious");

         Movie newMovie=movieRepository.save(movie1);

         assertEquals("adventure",newMovie.getGenera());
         assertEquals("fast and furious",newMovie.getName());

    }

    @Test
    void deleteMovie(){

        movieRepository.save(avatarMovie);
        Long id=avatarMovie.getId();




        movieRepository.save(titanicMovie);

        movieRepository.delete(avatarMovie);

        List<Movie> movies=movieRepository.findAll();
        Optional<Movie>deletedMovie=movieRepository.findById(id);

        assertEquals(1, movies.size());
        assertThat(deletedMovie).isEmpty();

        movieRepository.delete(titanicMovie);
        List<Movie> moviesall=movieRepository.findAll();
        assertEquals(0, moviesall.size());


    }

    @Test
    void getMoviesByGenera(){

        movieRepository.save(avatarMovie);


        movieRepository.save(titanicMovie);

       List<Movie> movie1=movieRepository.findByGenera("action");
       assertEquals(1,movie1.size());
       assertEquals("avatar",movie1.get(0).getName());
    }

}
