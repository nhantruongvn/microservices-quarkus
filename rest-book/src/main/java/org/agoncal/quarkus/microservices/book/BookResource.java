package org.agoncal.quarkus.microservices.book;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;

@Path("/api/books")
@Tag(name = "Book REST Endpoint")
public class BookResource {
    @Inject
    @RestClient
    NumberProxy numberProxy;

    @Inject
    Logger logger;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(summary = "Creates a new book", description = "Fill out book info")
    @APIResponses(
            {
                    @APIResponse(responseCode = "201", description = "A new book created")
            }
    )
    public Response createBook(@FormParam("title") String title, @FormParam("author") String author, @FormParam("year") Integer year, @FormParam("genre") String genre) {
        Book book = new Book();
        book.setIsbn13(numberProxy.generateIsbnNumbers().getIsbn13());
        book.setTitle(title);
        book.setAuthor(author);
        book.setYearOfPublication(year);
        book.setGenre(genre);
        book.setCreationDate(Instant.now());

        logger.info("New book created: " + book);

        return Response.status(201).entity(book).build();
    }
}