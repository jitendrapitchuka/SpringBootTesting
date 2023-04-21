package com.Junit.junitAndMockito.controller;

import com.Junit.junitAndMockito.model.Movie;
import com.Junit.junitAndMockito.service.MovieService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class MovieControllerTest {
    @MockBean
    private MovieService movieService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewMovie() throws Exception {
        Movie avatarMovie=new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("avatar");
        avatarMovie.setGenera("action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        when(movieService.save(any(Movie.class))).thenReturn(avatarMovie);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",is("avatar")));



    }

    @Test
    void getAllMovies() throws Exception {
       Movie avatarMovie=new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("avatar");
        avatarMovie.setGenera("action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));


       Movie titanicMovie=new Movie();
        titanicMovie.setId(2L);
        titanicMovie.setName("titanic");
        titanicMovie.setGenera("romance");
        titanicMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        List<Movie> allMovies=new ArrayList<>();
        allMovies.add(avatarMovie);
        allMovies.add(titanicMovie);

        when(movieService.getAllMovies()).thenReturn(allMovies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(2)));

    }

    @Test
    void MovieById() throws Exception {
        Movie avatarMovie=new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("avatar");
        avatarMovie.setGenera("action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        when(movieService.getMovieById(anyLong())).thenReturn(avatarMovie);

        mockMvc.perform(get("/movies/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("avatar")));
    }

    @Test
    void deleteMovie() throws Exception {
        Movie avatarMovie=new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("avatar");
        avatarMovie.setGenera("action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        doNothing().when(movieService).deleteMovie(anyLong());

        mockMvc.perform(delete("/movies/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateMovie() throws Exception {
        Movie avatarMovie=new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setName("avatar");
        avatarMovie.setGenera("action");
        avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL,22));

        when(movieService.updateMovie(any(Movie.class),anyLong())).thenReturn(avatarMovie);

        mockMvc.perform(put("/movies/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("avatar")));

    }
}
