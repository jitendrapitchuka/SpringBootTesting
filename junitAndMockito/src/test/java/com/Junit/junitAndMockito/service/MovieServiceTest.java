package com.Junit.junitAndMockito.service;

import com.Junit.junitAndMockito.model.Movie;
import com.Junit.junitAndMockito.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MovieServiceTest {
    @InjectMocks
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;

    private Movie avatarMovie;
    private Movie titanicMovie;
    @BeforeEach
    void setUpBeforeEach(){
        avatarMovie=new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("avatar");
        avatarMovie.setGenera("action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));


        titanicMovie=new Movie();
        titanicMovie.setId(2L);
        titanicMovie.setName("titanic");
        titanicMovie.setGenera("romance");
        titanicMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));
    }
    @Test
    void save(){


        when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);

        Movie movie1=movieService.save(avatarMovie);

        assertNotNull(movie1);
        assertThat(movie1.getName()).isEqualTo("avatar");

    }

    @Test
    void getAll(){


        List<Movie> allMovies=new ArrayList<>();
        allMovies.add(avatarMovie);
        allMovies.add(titanicMovie);

        when(movieRepository.findAll()).thenReturn(allMovies);
        assertEquals(2,movieService.getAllMovies().size());
        assertEquals("titanic",movieService.getAllMovies().get(1).getName());
    }

    @Test
    void MovieById(){

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));

        Movie movie1=movieService.getMovieById(1L);

        assertEquals(1L,movie1.getId());
        assertEquals("avatar",movie1.getName());



    }

    @Test
    void getMovieByIdException(){


        when(movieRepository.findById(1L)).thenReturn(Optional.of(avatarMovie));

        assertThrows(RuntimeException.class,()->{movieService.getMovieById(2L);});

    }

    @Test
    void UpdateMovie(){


        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);

        avatarMovie.setName("titanic");
        avatarMovie.setGenera("romance");

        Movie movie1=movieService.updateMovie(avatarMovie,1L);
        assertNotNull(movie1);
        assertEquals("titanic",movie1.getName());
        assertEquals("romance",movie1.getGenera());
    }

    @Test
    void delete(){


        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));

        doNothing().when(movieRepository).delete(any(Movie.class));

        movieService.deleteMovie(1L);

        verify(movieRepository,times(1)).delete(avatarMovie);


    }


}
