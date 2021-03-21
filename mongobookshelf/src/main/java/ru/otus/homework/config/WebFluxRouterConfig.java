package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebFluxRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookRepository repository) {
        return route()
                .nest(path("/api/v2/book/"), builder -> builder
                        .GET("/all", accept(APPLICATION_JSON),
                                request -> ok().contentType(APPLICATION_JSON).body(repository.findAll(), Book.class)
                        )
                        .GET("/{id}", accept(APPLICATION_JSON),
                                request -> ok().contentType(APPLICATION_JSON).body(repository.findById(request.pathVariable("id")), Book.class))
                        .POST("/", accept(APPLICATION_JSON),
                                request ->
                                        request.bodyToMono(Book.class)
                                                .flatMap(repository::save)
                                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)))
                        )
                        .PUT("/{id}", accept(APPLICATION_JSON),
                                request ->
                                        request.bodyToMono(Book.class)
                                                .flatMap(repository::save)
                                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book))))
                        .DELETE(
                                "/{id}",
                                request -> {
                                    repository.deleteById(request.pathVariable("id"));
                                    return ok().build();
                                }
                        )
                        .build())
                .build();
    }

}
