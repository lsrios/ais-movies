package com.rls.ais;

import com.rls.ais.models.Movie;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class MoviesResourceTest {
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testGetMovies() throws IOException {
        List<Movie> response = target.path("/movies").request().get(List.class);
        assertNotNull(response);
    }

    @Test(expected = NotFoundException.class)
    public void testGetMovie() {
        Movie movie = target.path("/movies/1").request().get(Movie.class);
        assertNotNull(movie);

        movie = target.path("/movies/100").request().get(Movie.class);
        assertNull(movie);
    }

    @Test
    public void testPostMovie() {
        Movie movie = new Movie();
        movie.setName("TestMovie");
        movie.setGenre("Comedy");

        Response response = target.path("/movies").request().post(Entity.entity(movie, MediaType.APPLICATION_JSON));
        assertTrue(response.getStatus() == Response.Status.CREATED.getStatusCode());


    }

    @Test
    public void testPutMovie() {
        Movie movie = target.path("/movies/1").request().get(Movie.class);
        String name = "batatinha";
        movie.setName(name);

        Response response = target.path("/movies").request().put(Entity.entity(movie, MediaType.APPLICATION_JSON));
        movie = target.path("/movies/1").request().get(Movie.class);

        assertEquals(movie.getName(), name);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteMovie() {
        target.path("/movies/0").request().delete();
        Movie movie = target.path("/movies/0").request().get(Movie.class);
    }
}
