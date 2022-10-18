package org.agoncal.quarkus.microservices.book;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.bind.annotation.JsonbProperty;

@RegisterRestClient
public class IsbnThirteen {
    @JsonbProperty("ISBN_13")
    private String isbn13;

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }
}
