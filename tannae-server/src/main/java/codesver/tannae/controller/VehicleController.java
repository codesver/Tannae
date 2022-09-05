package codesver.tannae.controller;

import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    @PatchMapping("/{vsn}/switch-run")
    public Boolean switchRun(@PathVariable Integer vsn, @RequestParam Boolean run) {
        log.info("[CONTROLLER-VEHICLE : SWITCH_RUN] /vehicles/{}/switch-run?run={}", vsn, run);
        vehicleRepository.switchRun(vsn, run);
        return run;
    }
}
