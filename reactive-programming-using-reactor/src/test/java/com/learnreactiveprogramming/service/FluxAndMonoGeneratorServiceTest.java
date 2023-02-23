package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX", "BEN", "CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
        //given

        //when
        Flux<String> name = fluxAndMonoGeneratorService.namesFlux_immutability();

        //then
        StepVerifier.create(name)
                .expectNext("alex", "ben", "chloe")
                .verifyComplete();
    }

    @Test
    void namesFlux_filter() {
        //given

        //when
        Flux<String> flux_filter = fluxAndMonoGeneratorService.namesFlux_filter(3);

        //then
        StepVerifier.create(flux_filter)
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void nameMono() {
        //given

        //when

        //then
    }

    @Test
    void namesFlux_flatMap() {
        //given

        //when
        Flux<String> namesFlux_flatMap = fluxAndMonoGeneratorService.namesFlux_flatMap(4);

        //then
        StepVerifier.create(namesFlux_flatMap)
                .expectNext("C", "H", "L", "O", "E")
                .verifyComplete();

    }

    @Test
    void namesFlux_flatMap_async() {
        //given

        //when
        Flux<String> namesFlux_flatMap_async = fluxAndMonoGeneratorService.namesFlux_flatMap_async(3);

        //then
        StepVerifier.create(namesFlux_flatMap_async)
                //.expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_flatMap_async_iterable() {
        //given

        //when
        Flux<String> namesFlux_flatMap_async_iterable = fluxAndMonoGeneratorService.namesFlux_flatMap_async_iterable(4);

        //then
        StepVerifier.create(namesFlux_flatMap_async_iterable)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void namesFlux_flatMap_groupBy() {
        //given

        //when
        Flux<GroupedFlux<Flux<String>, String>> groupedFluxFlux = fluxAndMonoGeneratorService.namesFlux_flatMap_groupBy();

        //then
        StepVerifier.create(groupedFluxFlux)
                .expectNext(new GroupedFlux[]{});
    }
}