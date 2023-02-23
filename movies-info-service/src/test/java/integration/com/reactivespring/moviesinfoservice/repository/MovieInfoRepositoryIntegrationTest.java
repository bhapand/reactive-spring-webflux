package com.reactivespring.moviesinfoservice.repository;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
@AutoConfigureDataMongo
@TestPropertySource(properties = "spring.mongodb.embedded.version=6.0.1")
class MovieInfoRepositoryIntegrationTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieInfos = List.of(new MovieInfo(null, "Terminator", 2005, List.of("Mark", "Steven"), LocalDate.of(2005, 12, 3)),
                new MovieInfo(null, "Titanic", 2015, List.of("Charlie", "John"), LocalDate.of(2015, 9, 23))
        );
        movieInfoRepository.saveAll(movieInfos)
                .blockLast();
        /*Mockito.when(movieInfoRepository.findAll())
                .thenReturn(Flux.fromIterable(movieInfos));*/
    }

    @Test
    void findAll() {
        //given

        //when
        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll();

        //then
        StepVerifier.create(movieInfoFlux)
                .expectNextCount(2)
                .verifyComplete();

    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll();
    }
}