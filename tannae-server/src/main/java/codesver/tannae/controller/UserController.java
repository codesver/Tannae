package codesver.tannae.controller;

import codesver.tannae.dto.user.SignUpUserDTO;
import codesver.tannae.service.user.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final SignUpService signUpService;

    @GetMapping("/check-id")
    public Boolean checkId(@RequestParam String id) {
        log.info("[CONTROLLER-USER : CHECK_ID] /users/check-id?id={}", id);
        return signUpService.isAvailableId(id);
    }

    @GetMapping("/check-private")
    public Boolean checkPrivate(@RequestParam String name, @RequestParam String rrn) {
        log.info("[CONTROLLER-USER : CHECK_PRIVATE] /users/check-private?name={}&rrn={}", name, rrn);
        return signUpService.isAvailableUser(name, rrn);
    }

    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody SignUpUserDTO dto) {
        log.info("[CONTROLLER-USER : SIGN_UP] /users/sign-up body={}", dto);
        return signUpService.signUpSuccessfully(dto);
    }
}
