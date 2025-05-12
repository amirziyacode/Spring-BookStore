package org.example.bookstoreapp.chatAi;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/ai")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping(value = "ask-bot", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<String>> sendMassage(@RequestParam String message) {
        return ResponseEntity.status(HttpStatus.OK).body(chatService.sendMassage(message));
    }
}
