package com.example.fetchwhatever;

import com.example.fetchwhatever.dtos.AgeDTO;
import com.example.fetchwhatever.dtos.AllDTO;
import com.example.fetchwhatever.dtos.GenderDTO;
import com.example.fetchwhatever.dtos.NationDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import reactor.core.publisher.Flux;

@Configuration
public class RemoteApiTester implements CommandLineRunner {

    List<String> names = Arrays.asList("lars", "peter", "mikkel", "martin", "david", "maja");


    private Mono<String> callSlowEndpoint() {
        Mono<String> slowResponse = WebClient.create()
                .get()
                .uri("https://localhost:8080/random-string-slow")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> System.out.println("UUUPS : " + e.getMessage()));
        return slowResponse;
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        String randomString = callSlowEndpoint().block();
        System.out.println(randomString);

         */
        //callEndpointBlocking();
        //callSlowEndpointNonBlocking();
        //getGenderForName("mikkel");
        //getGendersBlocking();
        //getGendersNonBlocking();
        getAllNonBlocking();
    }


    public Mono<GenderDTO> getGenderForName(String name) {
        WebClient client = WebClient.create();
        Mono<GenderDTO> gender = client.get()
                .uri("https://api.genderize.io?name=" + name)
                .retrieve()
                .bodyToMono(GenderDTO.class);
        return gender;
    }

    public Mono<NationDTO> getNationForName(String name) {
        WebClient client = WebClient.create();
        Mono<NationDTO> nation = client.get()
                .uri("https://api.nationalize.io?name=" + name)
                .retrieve()
                .bodyToMono(NationDTO.class);
        return nation;
    }

    public Mono<AgeDTO> getAgeForName(String name) {
        WebClient client = WebClient.create();
        Mono<AgeDTO> age = client.get()
                .uri("https://api.agify.io?name=" + name)
                .retrieve()
                .bodyToMono(AgeDTO.class);
        return age;
    }

    public Mono<AllDTO> getAllStatsForName(String name) {
        GenderDTO genderDTO = getGenderForName(name).block();
        NationDTO nationDTO = getNationForName(name).block();
        AgeDTO ageDTO = getAgeForName(name).block();
        //NationDTO nationDTO = new NationDTO();

        AllDTO allDTO = new AllDTO(name
                ,ageDTO.getCount()
                ,genderDTO.getCount()
                ,nationDTO.getCount()
                ,ageDTO.getAge()
                ,genderDTO.getGender()
                ,genderDTO.getProbability()
                ,nationDTO.getCountry());

        return Mono.just(allDTO);
    }

    public void getGendersNonBlocking() {
        long start = System.currentTimeMillis();
        var genders = names.stream().map(name -> getGenderForName(name)).toList();
        Flux<GenderDTO> flux = Flux.merge(Flux.concat(genders));
        //List<GenderDTO> res = flux.collectList().block();
        long end = System.currentTimeMillis();
        System.out.println("Time for six external gender requests, NON-BLOCKING: " + (end-start));
    }

    public void getNationsNonBlocking() {
        long start = System.currentTimeMillis();
        var nations = names.stream().map(name -> getNationForName(name)).toList();
        Flux<NationDTO> flux = Flux.merge(Flux.concat(nations));
        long end = System.currentTimeMillis();
        System.out.println("Time for six external nation requests, NON-BLOCKING " + (end-start));
    }

    public void getAgesNonBlocking() {
        long start = System.currentTimeMillis();
        var ages = names.stream().map(name -> getAgeForName(name)).toList();
        Flux<AgeDTO> flux = Flux.merge(Flux.concat(ages));
        long end = System.currentTimeMillis();
        System.out.println("Time for six external age requests, NON-BLOCKING " + (end-start));
    }

    public void getAllNonBlocking() {
        long start = System.currentTimeMillis();
        var all = names.stream().map(name -> getAllStatsForName(name)).toList();
        Flux<AllDTO> flux = Flux.merge(Flux.concat(all));
        long end = System.currentTimeMillis();
        System.out.println("Time for six external all requests, NON-BLOCKING " + (end-start));
        all.stream().forEach(obj -> System.out.println(obj.block() + "\n"));
    }

    /*
    public void getGendersBlocking() {
        long start = System.currentTimeMillis();
        List<GenderDTO> genders = names.stream().map(name -> getGenderForName(name).block()).toList();
        long end = System.currentTimeMillis();
        System.out.println("Time for six external requests, BLOCKING: " + (end-start));
        System.out.println(genders);
    }


    public void callSlowEndpointNonBlocking(){
        long start = System.currentTimeMillis();
                Mono<String> sr1 = callSlowEndpoint();
                Mono<String> sr2 = callSlowEndpoint();
                Mono<String> sr3 = callSlowEndpoint();

                var rs = Mono.zip(sr1, sr2, sr3).map(tuple3 -> {
                    List<String> randomStrings = new ArrayList<>();
                    randomStrings.add(tuple3.getT1());
                    randomStrings.add(tuple3.getT2());
                    randomStrings.add(tuple3.getT3());
                    long end = System.currentTimeMillis();
                    randomStrings.add(0, "Time spent NON-BLOCKING (ms): " + (end-start));
                    return randomStrings;
        });
                List<String> randoms = rs.block();
        System.out.println(randoms.stream().collect(Collectors.joining(",")));
    }

    public void callEndpointBlocking(){
        long start = System.currentTimeMillis();
        List<String> randomStrings = new ArrayList<>();
        Mono<String> slowResponse = callSlowEndpoint();
        randomStrings.add(slowResponse.block());
        slowResponse = callSlowEndpoint();
        randomStrings.add(slowResponse.block());
        slowResponse = callSlowEndpoint();
        randomStrings.add(slowResponse.block());
        long end = System.currentTimeMillis();
        randomStrings.add(0, "Time spend BLOCKING (ms): " + (end-start));
        System.out.println(String.join(",", randomStrings));
    }


     */


}
