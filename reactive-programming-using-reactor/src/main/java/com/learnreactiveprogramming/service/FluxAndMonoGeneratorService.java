package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.service.model.ContactInfo;
import com.learnreactiveprogramming.service.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

public class FluxAndMonoGeneratorService {

    public static void main(String[] args) {
//        new FluxAndMonoGeneratorService().namesFlux_flatMap_groupBy().subscribe(fluxStringGroupedFlux -> {
//            fluxStringGroupedFlux.key().log().subscribe(str -> System.out.println(str));
//        });

//        new FluxAndMonoGeneratorService().namesFlux_flatMap_groupBy1()
//                .log()
//                .subscribe(name -> {
//                    System.out.println(name.key());
//                });


    }
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe")).log(); // db or a remote service call
    }

    public Mono<String> nameMono() {
        return Mono.just("alex").log();
    }

    public Flux<String> namesFlux_map() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase).log(); // db or a remote service call
    }

    public Flux<String> namesFlux_immutability() {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        namesFlux.map(String::toUpperCase).log(); // db or a remote service call
        return namesFlux;
    }

    public Flux<String> namesFlux_filter(int length) {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        return namesFlux.map(String::toUpperCase)
                .filter(name -> name.length() > length) //4-ALEX, 5-CHLOE
                .map(name -> name.length() + "-" + name).log(); // db or a remote service call
    }

    //flatMap --> transforms one source element to a flux of 1 to N elements.
    //"ALEX" --> Flux.just("A","L","E","X")
    public Flux<String> namesFlux_flatMap(int length) {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        return namesFlux.map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .flatMap(element -> splitString(element));
    }

    private Flux<String> splitString(String element) {
        return Flux.fromArray(element.split(""));
    }

    private Flux<String> splitString_withDelay(String element) {
        int delay = new Random().nextInt(1000);
        return Flux.fromArray(element.split(""))
                .delayElements(Duration.ofMillis(delay));
    }

    public Flux<String> namesFlux_flatMap_async(int length) {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        return namesFlux.map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .flatMap(this::splitString_withDelay,1,3 ).log();
    }

    public Flux<String> namesFlux_flatMap_async_iterable(int length) {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        return namesFlux.map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .flatMapIterable(name -> Arrays.asList(name.split("")),1).log();
    }

    public Flux<GroupedFlux<Flux<String>, String>> namesFlux_flatMap_groupBy() {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        return namesFlux.map(String::toUpperCase)
                .groupBy(name -> Flux.just(name.substring(0,1),name.substring(2)));
    }

    public Flux<GroupedFlux<Integer, String>> namesFlux_flatMap_groupBy1() {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        return namesFlux.map(String::toUpperCase)
                .groupBy(String::length);
    }



}
