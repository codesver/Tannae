package codesver.tannae.controller;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.dto.ServiceResponseDTO;
import codesver.tannae.repository.history.HistoryRepository;
import codesver.tannae.repository.process.ProcessRepository;
import codesver.tannae.repository.user.UserRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import codesver.tannae.service.Calculator;
import codesver.tannae.service.Guider;
import codesver.tannae.service.NaviRequester;
import codesver.tannae.service.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ProcessRepository processRepository;
    private final HistoryRepository historyRepository;

    private final RequestHandler processor;
    private final Calculator calculator;
    private final Guider guider;

    @PostMapping("/request")
    public ServiceResponseDTO request(@RequestBody ServiceRequestDTO requestDTO) {
        log.info("[CONTROLLER-SERVICE {} : REQUEST ] /service/request body={}", Thread.currentThread().getId(), requestDTO);
        DRO<Process> dro = processor.handleRequest(requestDTO);
        Process process = dro.get();
        return dro.getFlag() > 0 ? new ServiceResponseDTO(dro.getFlag(), process.getVehicle().getVsn(), requestDTO.getUsn(),
                process.getPath(), dro.getGuides().toString(), process.getPassed())
                : new ServiceResponseDTO(dro.getFlag());
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

        Process process = processRepository.increasePassed(vsn);
        JSONArray path = new JSONArray(process.getPath());
        JSONArray guides = guider.updatesGuides(new JSONArray(request.getString("guides")));
        JSONObject point = path.getJSONObject(process.getPassed() + 1);

        int usn = point.getInt("usn");
        boolean type = point.getBoolean("type");
        Vehicle vehicle = vehicleRepository.transfer(vsn, type, point);

        if (type) {
            historyRepository.updateBoardingTime(usn);
        } else {
            historyRepository.updateArrivalTime(usn);
            JSONObject realData = calculator.calculate(path, usn);
            historyRepository.updateRealData(usn, realData.getInt("fare"), realData.getInt("distance"), realData.getInt("duration"));
            userRepository.usePoint(usn, realData.getInt("fare"));
            userRepository.changeBoardState(usn, false);
        }

        JSONObject response = new JSONObject().put("vsn", vsn)
                .put("usn", usn)
                .put("type", type)
                .put("path", path.toString())
                .put("guides", guides.toString())
                .put("passed", process.getPassed());
        smso.convertAndSend("/sub/vehicle/" + vsn, response.toString());

        if (vehicle.getNum() == 0)
            processRepository.deleteProcess(vsn);
    }
}
