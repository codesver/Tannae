package codesver.tannae.controller;

import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.dto.ServiceResponseDTO;
import codesver.tannae.service.VehicleFinder;
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
    private final VehicleFinder finder;

    @PostMapping("/request")
    public ServiceResponseDTO request(@RequestBody ServiceRequestDTO dto) {
        log.info("[CONTROLLER-SERVICE : REQUEST] /service/request body={}", dto);
        Optional<Vehicle> vehicle = finder.findVehicle(dto);
        if (vehicle.isPresent()) {
            // Kakao api communication
            // Update database
            // Create response data
            // return
            return new ServiceResponseDTO();
        } else {
            return new ServiceResponseDTO();
        }
    }

    @MessageMapping("/hello")
    public void message() {
        smso.convertAndSend("/sub/vehicle/1", "HELLO STOMP");
    }
}
