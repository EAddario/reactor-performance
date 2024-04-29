package org.addario.blockinggateway;

import lombok.extern.java.Log;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log
public class BlockingRestConsumer {
    private static final RestTemplate restTemplate = restTemplate(new RestTemplateBuilder());

    private static RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8080/rest")
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(300))
                .errorHandler(new BlockingResponseErrorHandler())
                .build();
    }

    public static String getBlockingFibonacci(Long num) {
        ResponseEntity<String> response = restTemplate.getForEntity("/blocking/fibonacci/{num}", String.class, num);

        return response.getBody();
    }

    public static String getBlockingRandomFibonacci() {
        ResponseEntity<String> response = restTemplate.getForEntity("/blocking/fibonacci/random", String.class);

        return response.getBody();
    }

    public static String getReactiveFibonacci(Long num) {
        ResponseEntity<String> response = restTemplate.getForEntity("/reactive/fibonacci/{num}", String.class, num);

        return response.getBody();
    }

    public static String getReactiveRandomFibonacci() {
        ResponseEntity<String> response = restTemplate.getForEntity("/reactive/fibonacci/random", String.class);

        return response.getBody();
    }

    public static String getBlockingName(int num) {
        ResponseEntity<String> response = restTemplate.getForEntity("/blocking/name/{num}", String.class, num);

        return response.getBody();
    }

    public static List<String> getBlockingNamesList() {
        ResponseEntity<String[]> response = restTemplate.getForEntity("/blocking/nameslist", String[].class);

        return Arrays.stream(Objects.requireNonNull(response.getBody())).toList();
    }

    public static String getReactiveName(int num) {
        ResponseEntity<String> response = restTemplate.getForEntity("/reactive/name/{num}", String.class, num);

        return response.getBody();
    }

    public static List<String> getReactiveNamesList() {
        ResponseEntity<String[]> response = restTemplate.getForEntity("/reactive/nameslist", String[].class);

        return Arrays.stream(Objects.requireNonNull(response.getBody())).toList();
    }
}
