package org.agoncal.quarkus.microservices.book;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
  info = @Info(title = "Book API",
    description = "Creates books",
    version = "0.1",
    contact = @Contact(name = "@tctnhan", url = "https://axonactive.com")),
  externalDocs = @ExternalDocumentation(url = "https://axonactive.com", description = "All the Microservice course code"),
  tags = {
    @Tag(name = "api", description = "Public API that can be used by anybody"),
    @Tag(name = "books", description = "Anybody interested in books")
  }
)
public class BookMicroservice extends Application {
}
