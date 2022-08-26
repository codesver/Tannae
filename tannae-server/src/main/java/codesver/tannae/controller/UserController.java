package codesver.tannae.controller;

import codesver.tannae.domain.User;
import codesver.tannae.dto.user.FoundAccountDTO;
import codesver.tannae.dto.user.LoginDTO;
import codesver.tannae.dto.user.SignUpUserDTO;
import codesver.tannae.repository.user.UserRepository;
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

    @GetMapping("/check-id")
    public Boolean checkId(@RequestParam String id) {
        log.info("[CONTROLLER-USER : CHECK_ID] /users/check-id?id={}", id);
        return userRepository.countById(id) == 0;
    }

    @GetMapping("/check-private")
    public Boolean checkPrivate(@RequestParam String name, @RequestParam String rrn) {
        log.info("[CONTROLLER-USER : CHECK_PRIVATE] /users/check-private?name={}&rrn={}", name, rrn);
        return userRepository.countByPrivate(name, rrn) == 0;
    }

    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody SignUpUserDTO dto) {
        log.info("[CONTROLLER-USER : SIGN_UP] /users/sign-up body={}", dto);
        return userRepository.save(dto.toUser());
    }

    @GetMapping("/find-account")
    public FoundAccountDTO findAccount(@RequestParam String name, @RequestParam String rrn) {
        log.info("[CONTROLLER-USER : FIND_ACCOUNT] /users/find-account?name={}&rrn={}", name, rrn);
        Optional<User> foundUser = userRepository.findByNameRrn(name, rrn);
        User user = foundUser.orElse(new User());
        return new FoundAccountDTO(user.getId(), user.getPw(), foundUser.isPresent());
    }

    @GetMapping("/login")
    public LoginDTO login(@RequestParam String id, @RequestParam String pw) {
        log.info("[CONTROLLER-USER : LOGIN] /users/login?id={}&pw={}", id, pw);
        Optional<User> loggedUser = userRepository.findByIdPw(id, pw);
        return new LoginDTO(loggedUser.orElse(new User()), loggedUser.isPresent());
    }

    @PatchMapping("/{usn}/charge")
    public Integer charge(@PathVariable Integer usn, @RequestParam Integer point) {
        log.info("[CONTROLLER-USER : CHARGE] /users/{}/point={}", usn, point);
        return userRepository.chargePoint(usn, point);
    }
}