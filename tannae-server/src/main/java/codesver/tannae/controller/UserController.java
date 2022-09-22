package codesver.tannae.controller;

import codesver.tannae.domain.User;
import codesver.tannae.dto.FoundAccountDTO;
import codesver.tannae.dto.LoginDTO;
import codesver.tannae.dto.SignUpUserDTO;
import codesver.tannae.repository.user.UserRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    @GetMapping("/check-id")
    public Boolean checkId(@RequestParam String id) {
        log.info("[CONTROLLER-USER {} : CHECK_ID] /users/check-id?id={}", Thread.currentThread().getId(), id);
        return userRepository.countById(id) == 0;
    }

    @GetMapping("/check-private")
    public Boolean checkPrivate(@RequestParam String name, @RequestParam String rrn) {
        log.info("[CONTROLLER-USER {} : CHECK_PRIVATE] /users/check-private?name={}&rrn={}", Thread.currentThread().getId(), name, rrn);
        return userRepository.countByPrivate(name, rrn) == 0;
    }

    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody SignUpUserDTO dto) {
        log.info("[CONTROLLER-USER {}: SIGN_UP] /users/sign-up body={}", Thread.currentThread().getId(), dto);
        return userRepository.save(dto.toUser());
    }

    @GetMapping("/find-account")
    public FoundAccountDTO findAccount(@RequestParam String name, @RequestParam String rrn) {
        log.info("[CONTROLLER-USER {}: FIND_ACCOUNT] /users/find-account?name={}&rrn={}", Thread.currentThread().getId(), name, rrn);
        Optional<User> foundUser = userRepository.findByNameRrn(name, rrn);
        User user = foundUser.orElse(new User());
        return new FoundAccountDTO(user.getId(), user.getPw(), foundUser.isPresent());
    }

    @GetMapping("/login")
    public LoginDTO login(@RequestParam String id, @RequestParam String pw) {
        log.info("[CONTROLLER-USER {} : LOGIN] /users/login?id={}&pw={}", Thread.currentThread().getId(), id, pw);
        Optional<User> loggedUser = userRepository.findByIdPw(id, pw);
        if (loggedUser.isPresent()) {
            User user = loggedUser.get();
            int vsn = user.getIsDriver() ? vehicleRepository.findVehicleByUsn(user.getUsn()).get().getVsn() : 0;
            return new LoginDTO(user, vsn, true);
        }
        return new LoginDTO(null, 0, false);
    }

    @PatchMapping("/{usn}/charge")
    public Integer charge(@PathVariable Integer usn, @RequestParam Integer point) {
        log.info("[CONTROLLER-USER {} : CHARGE] /users/{}/point={}", Thread.currentThread().getId(), usn, point);
        return userRepository.chargePoint(usn, point);
    }

    @PatchMapping("/rate")
    public Boolean rate(@RequestParam Integer vsn, @RequestParam float rate) {
        log.info("[CONTROLLER-USER {} : RATE] /users/rate?vsn={}&rate={}", Thread.currentThread().getId(), vsn, rate);
        Integer usn = vehicleRepository.findUserOfVehicle(vsn);
        userRepository.rate(usn, rate);
        return true;
    }
}
