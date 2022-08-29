package codesver.tannae.controller;

import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.service.VehicleFinderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    private final SimpMessageSendingOperations smso;
    private final VehicleFinderService finder;

    @PostMapping("/request")
    public void request(@RequestBody ServiceRequestDTO dto) {
        log.info("[CONTROLLER-SERVICE : REQUEST] /service/request body={}", dto);
        Optional<Vehicle> vehicle = finder.findVehicle(dto);
        if (vehicle.isPresent()) {
            // Kakao api communication
            // Update database
            // Create response data
            // return
        } else {
            // return response data
        }
    }

    @MessageMapping("/hello")
    public void message() {
        smso.convertAndSend("/sub/vehicle/1", "HELLO STOMP");
    }
}
