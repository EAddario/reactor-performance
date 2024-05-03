package org.addario.backendservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = BackendServiceApplication.class)
class BackendServiceApplicationTests {
    private static final List<String> namesList = Names.namesList;
    WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    @Test
    public void funcBlockingFibonacci() {
        assertThat(BlockingFibonacci.calculate(20L)).isEqualTo(6765L);
    }

    @Test
    public void funcBlockingName() {
        assertThat(BlockingNames.getName(256)).isIn(namesList);
    }

    @Test
    public void funcBlockingNamesList() {
        assertThat(BlockingNames.getNamesList()).containsAll(namesList);
    }

    @Test
    public void funcReactiveFibonacci() {
        StepVerifier.create(ReactiveFibonacci.calculate(20L))
                .expectNext(6765L)
                .verifyComplete();
    }

    @Test
    public void funcReactiveName() {
        StepVerifier.create(ReactiveNames.getName(256))
                .expectNextMatches(namesList::contains)
                .verifyComplete();
    }

    @Test
    public void funcReactiveNamesList() {
        StepVerifier.create(ReactiveNames.getNamesList())
                .thenConsumeWhile(namesList::contains)
                .verifyComplete();
    }

    @Test
    public void webBlockingFibonacci() {
        webTestClient.get().uri("/rest/blocking/fibonacci/20")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json;charset=UTF-8")
                .expectBody(String.class).isEqualTo("6,765");
    }

    @Test
    public void webBlockingFibonacciErrorCeil() {
        webTestClient.get().uri("/rest/blocking/fibonacci/41")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class).isEqualTo("41 is not between allowed range (0 and 40)");
    }

    @Test
    public void webBlockingFibonacciErrorFloor() {
        webTestClient.get().uri("/rest/blocking/fibonacci/-1")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class).isEqualTo("-1 is not between allowed range (0 and 40)");
    }

    @Test
    public void webBlockingName() {
        webTestClient.get().uri("/rest/blocking/name/256")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(namesList)
                        .contains(new String(Objects.requireNonNull(response.getResponseBody()))));
    }

    @Test
    public void webBlockingNameErrorCeil() {
        webTestClient.get().uri("/rest/blocking/name/1000001")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class).isEqualTo("1000001 is not between allowed range (1 and 1,000,000)");
    }

    @Test
    public void webBlockingNameErrorFloor() {
        webTestClient.get().uri("/rest/blocking/name/-1")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class).isEqualTo("-1 is not between allowed range (1 and 1,000,000)");
    }

    @Test
    public void webBlockingNamesList() {
        webTestClient.get().uri("/rest/blocking/nameslist")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(String.class)
                .contains(namesList.toString()
                        .replace("[", "[\"")
                        .replace("]", "\"]")
                        .replace(", ", "\",\""));
    }

    @Test
    public void webReactiveFibonacci() {
        webTestClient.get().uri("/functional/reactive/fibonacci/20")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("6,765");
    }

    @Test
    public void webReactiveName() {
        webTestClient.get().uri("/functional/reactive/name/256")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(namesList)
                        .contains(new String(Objects.requireNonNull(response.getResponseBody()))));
    }

    @Test
    public void webReactiveNamesList() {
        webTestClient.get().uri("/functional/reactive/nameslist")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(String.class)
                .contains(namesList.toString()
                        .replace("[", "[\"")
                        .replace("]", "\"]")
                        .replace(", ", "\",\""));
    }
}
