package com.Junit.junitAndMockito.repository;

import com.Junit.junitAndMockito.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenera(String s);

}
