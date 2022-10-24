package codesver.tannae.controller;

import codesver.tannae.repository.vehicle.VehicleRepository;
import codesver.tannae.service.domain.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    @PatchMapping("/{vsn}/running")
    public Boolean patchRunning(@PathVariable Integer vsn, @RequestBody Boolean running) {
        log.info("[CONTROLLER-VEHICLE {} : PATCH_RUNNING] PATCH /vehicles/{}/running body={}", Thread.currentThread().getId(), vsn, running);
        vehicleRepository.switchRun(vsn, running);

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Coordinates.txt"));
            for (int i = 1; i <= 100; i++) {
                String infos = reader.readLine();
                String[] info = infos.split(",");
                vehicleRepository.setPositions(i, Double.parseDouble(info[1]), Double.parseDouble(info[2]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return running;
    }

    @PatchMapping("/{vsn}/users/score")
    public Boolean patchUserScore(@PathVariable Integer vsn, @RequestBody Float score) {
        log.info("[CONTROLLER-VEHICLE {} : PATCH_USER_SCORE] PATCH /vehicles/{}/users/score body={}", Thread.currentThread().getId(), vsn, score);
        return userService.rateDriver(vsn, score);
    }
}
