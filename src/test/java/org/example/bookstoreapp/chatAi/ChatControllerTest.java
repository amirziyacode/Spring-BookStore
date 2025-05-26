package org.example.bookstoreapp.chatAi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import static org.mockito.Mockito.*;


@WebFluxTest(ChatController.class)
@Import(ChatService.class)
@AutoConfigureWebTestClient
@WithMockUser
class ChatControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ChatService chatService;

    @Test
    void sendMassage_shouldReturnFluxResponse() {
        String message = "hello";

        Flux<String> responseFlux = Flux.just("Hi", "How can I help you?");
        when(chatService.sendMassage(eq(message))).thenReturn(responseFlux);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/ai/ask-bot")
                        .queryParam("message", message)
                        .build())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(String.class)
                .contains("Hi", "How can I help you?");
    }
}