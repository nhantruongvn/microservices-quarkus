package org.agoncal.quarkus.microservices.book;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    @APIResponses({
            @APIResponse(responseCode = "201", description = "A new book created"),
            @APIResponse(responseCode = "206", description = "A new book created without ISBN Number")
    })
    @Retry(maxRetries = 1, delay = 2000)
    @Fallback(fallbackMethod = "fallbackOnCreateBook")
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

    public Response fallbackOnCreateBook(String title, String author, Integer year, String genre) throws FileNotFoundException {
        Book book = new Book();
        book.setIsbn13("13-xxxxxxxx");
        book.setTitle(title);
        book.setAuthor(author);
        book.setYearOfPublication(year);
        book.setGenre(genre);
        book.setCreationDate(Instant.now());

        saveOnDisk(book);
        logger.warn("New book saved on disk: " + book);

        return Response.status(206).entity(book).build();
    }

    private void saveOnDisk(Book book) throws FileNotFoundException {
        String bookJson = JsonbBuilder.create().toJson(book);
        try (PrintWriter out = new PrintWriter("book-" + Instant.now().toEpochMilli() + ".json")) {
            out.println(bookJson);
        }
    }
}