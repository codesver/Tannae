package codesver.tannae.controller;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.dto.ServiceResponseDTO;
import codesver.tannae.service.RequestProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    private final SimpMessageSendingOperations smso;
    private final RequestProcessor processor;

    @PostMapping("/request")
    public ServiceResponseDTO request(@RequestBody ServiceRequestDTO requestDTO) {
        log.info("[CONTROLLER-SERVICE : REQUEST] /service/request body={}", requestDTO);
        DRO<Process> dro = processor.processRequest(requestDTO);
        Process process = dro.get();
        return dro.getFlag() > 0 ? new ServiceResponseDTO(dro.getFlag(), process.getVehicle().getVsn(), requestDTO.getUsn(), process.getSummary(), dro.getPath().toString())
                : new ServiceResponseDTO(dro.getFlag());
    }

    @MessageMapping("/connect")
    public void connect(@Payload String id) {
        log.info("[SOCKET-CONTROLLER-SERVICE : CONNECT] User {} is connected to STOMP", id);
    }

    @MessageMapping("/request")
    public void request(@Payload String payload) {
        log.info("[SOCKET-CONTROLLER-SERVICE : REQUEST] Request={}", payload);
        JSONObject message = new JSONObject(payload);
        smso.convertAndSend("/sub/vehicle/" + message.getInt("vsn"), message.toString());
    }
}
