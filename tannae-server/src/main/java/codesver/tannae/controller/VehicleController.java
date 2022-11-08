package codesver.tannae.controller;

import codesver.tannae.service.domain.UserService;
import codesver.tannae.service.domain.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final UserService userService;
    private final VehicleService vehicleService;

    @PatchMapping("/{vsn}/running")
    public Boolean patchRunning(@PathVariable Integer vsn, @RequestBody Boolean running) {
        log.info("[CONTROLLER-VEHICLE {} : PATCH_RUNNING] PATCH /vehicles/{}/running body={}", Thread.currentThread().getId(), vsn, running);
        return vehicleService.switchRunningState(vsn, running);
    }

    @PatchMapping("/{vsn}/users/score")
    public Boolean patchUserScore(@PathVariable Integer vsn, @RequestBody Float score) {
        log.info("[CONTROLLER-VEHICLE {} : PATCH_USER_SCORE] PATCH /vehicles/{}/users/score body={}", Thread.currentThread().getId(), vsn, score);
        return userService.rateDriver(vsn, score);
    }
}
