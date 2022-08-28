package codesver.tannae.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompController {

    private final SimpMessageSendingOperations smso;

    @MessageMapping("/hello")
    public void message() {
        smso.convertAndSend("/sub/vehicle/1", "HELLO STOMP");
    }
}
