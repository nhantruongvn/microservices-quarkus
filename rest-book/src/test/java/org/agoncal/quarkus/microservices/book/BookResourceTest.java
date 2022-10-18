package org.agoncal.quarkus.microservices.book;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
public class BookResourceTest {

    @Test
    public void testCreateBook_shouldCreateABookSuccessfully() {
        given()
            .formParam("title", "Just another book")
            .formParam("author", "John Doe")
            .formParam("year", 2022)
            .formParam("genre", "fiction")
        .when().post("/api/books")
        .then()
            .statusCode(201)
            .body("ISBN_13", startsWith("13-"))
            .body("title", startsWith("Just"))
            .body("author", startsWith("John"))
            .body("year_of_publication", is(2022))
            .body("genre", startsWith("fic"))
            .body("creation_date", startsWith("2022"));

    }
}