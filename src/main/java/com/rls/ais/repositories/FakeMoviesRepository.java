package com.rls.ais.repositories;

import com.rls.ais.models.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class FakeMoviesRepository implements Repository<Movie> {
    private static List<Movie> movies;

    public FakeMoviesRepository() {
        movies = new ArrayList<>(Arrays.asList(
                new Movie(0, "Shrek", "Comedy"),
                new Movie(1, "007", "Action"),
                new Movie(2, "The Kissing Booth", "Romance")
        ));
    }

    @Override
    public List<Movie> findAll() {
        return movies;
    }

    @Override
    public Movie findById(long id) throws NoSuchElementException {
        return movies.stream().filter(m -> m.getId() == id).findFirst().get();
    }

    @Override
    public void add(Movie entity) {
        entity.setId(movies.size());
        movies.add(entity);
    }

    @Override
    public void update(Movie entity) throws NoSuchElementException{
        Movie movie = movies.stream().filter(m -> m.getId() == entity.getId()).findFirst().get();

        movie.setName(entity.getName());
        movie.setGenre(entity.getGenre());
    }

    @Override
    public void remove(long id) {
        movies = movies.stream().filter(m -> m.getId() != id).collect(Collectors.toList());
    }
}