package codesver.tannae.controller;

import codesver.tannae.repository.vehicle.VehicleRepository;
import codesver.tannae.service.domain.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    @PatchMapping("/{vsn}")
    public Boolean switchRun(@PathVariable Integer vsn, @RequestParam Boolean running) {
        log.info("[CONTROLLER-VEHICLE {} : SWITCH_RUN] PATCH /vehicles/{}?running={}", Thread.currentThread().getId(), vsn, running);
        vehicleRepository.switchRun(vsn, running);
        return running;
    }

    @PostMapping("/{vsn}/users/score")
    public Boolean postUserScore(@PathVariable Integer vsn, @RequestBody Float score) {
        log.info("[CONTROLLER-VEHICLE {} : POST_USER_SCORE] POST /vehicles/{}/users/score body={}", Thread.currentThread().getId(), vsn, score);
        return userService.rateDriver(vsn, score);
    }
}
