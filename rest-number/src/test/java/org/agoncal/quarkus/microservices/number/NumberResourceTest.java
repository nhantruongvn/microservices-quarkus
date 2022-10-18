package org.agoncal.quarkus.microservices.number;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class NumberResourceTest {

    @Test
    public void testGenerateIsbnNumbers_shouldReturnCorrectJsonBody() {
        given()
        .when().get("/api/numbers")
        .then()
            .statusCode(200)
            .body("ISBN_13", startsWith("13-"))
            .body("ISBN_10", startsWith("10-"))
            .body(not(hasKey("generationDate")));
    }

}