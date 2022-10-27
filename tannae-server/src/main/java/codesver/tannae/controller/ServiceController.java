package codesver.tannae.controller;

import codesver.tannae.domain.DSO;
import codesver.tannae.domain.Process;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.dto.ServiceResponseDTO;
import codesver.tannae.service.algorithm.RequestHandler;
import codesver.tannae.service.algorithm.Transporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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

    private final RequestHandler processor;
    private final Transporter transporter;

    @PostMapping("/request")
    public ServiceResponseDTO request(@RequestBody ServiceRequestDTO requestDTO) {
        log.info("[CONTROLLER-SERVICE {} : REQUEST] POST /service/request body={}", Thread.currentThread().getId(), requestDTO);
        DSO<Process> DSO = processor.handleRequest(requestDTO);
        Process process = DSO.get();
        return DSO.getFlag() > 0 ? new ServiceResponseDTO(DSO.getFlag(), process.getVehicle().getVsn(), requestDTO.getUsn(),
                process.getPath(), DSO.getGuides().toString(), process.getPassed())
                : new ServiceResponseDTO(DSO.getFlag());
    }

    @MessageMapping("/connect")
    public void connect(@Payload String id) {
        log.info("[SOCKET-CONTROLLER-SERVICE {} : CONNECT] User {} is connected to STOMP", Thread.currentThread().getId(), id);
    }

    @MessageMapping("/request")
    public void request(@Payload String payload) {
        log.info("[SOCKET-CONTROLLER-SERVICE {} : REQUEST] Request={}", Thread.currentThread().getId(), payload);
        JSONObject message = new JSONObject(payload);
        smso.convertAndSend("/sub/vehicle/" + message.getInt("vsn"), message.toString());
    }

    @MessageMapping("/transfer")
    public void transfer(@Payload String requestMessage) {
        log.info("[SOCKET-CONTROLLER-SERVICE {} : TRANSFER] Transfer vehicle to next point", Thread.currentThread().getId());
        JSONObject request = new JSONObject(requestMessage);
        int vsn = request.getInt("vsn");
        JSONObject response = transporter.transport(request, vsn);
        smso.convertAndSend("/sub/vehicle/" + vsn, response.toString());
    }
}
