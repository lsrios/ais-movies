package com.rls.ais.resources;

import com.rls.ais.models.Movie;
import com.rls.ais.repositories.FakeMoviesRepository;
import com.rls.ais.repositories.Repository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.NoSuchElementException;

@Path("/movies")
public class MoviesResource {

    private static final Repository<Movie> moviesRepository = new FakeMoviesRepository();

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies() {
        return Response
                .ok(moviesRepository.findAll())
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovie(@PathParam("id") long id) {
        try {
            return Response
                    .ok(moviesRepository.findById(id))
                    .build();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postMovie(Movie movie) {
        moviesRepository.add(movie);
        return Response.created(uriInfo.getRequestUri()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putMovie(Movie movie) {
        try {
            moviesRepository.update(movie);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") long id) {
        moviesRepository.remove(id);
        return Response.ok().build();
    }
}
