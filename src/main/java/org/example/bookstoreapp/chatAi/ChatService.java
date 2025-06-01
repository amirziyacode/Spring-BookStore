package org.example.bookstoreapp.chatAi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final WebClient webClient;
    public Flux<String> sendMassage(String message) {
        String body  = """
            {
              "model": "gemma3",
              "messages": [
                {"role": "user", "content": "%s"}
              ],
              "stream": true
            }
            """.formatted(message);

        return webClient.post()
                .uri("api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class);
    }

}
