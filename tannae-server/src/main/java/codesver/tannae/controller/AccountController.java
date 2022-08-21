package codesver.tannae.controller;

import codesver.tannae.service.user.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AccountController {

    private final SignUpService signUpService;

    @GetMapping("/check-id")
    public Boolean checkId(@RequestParam String id) {
        log.info("[SERVER] Checking id={}", id);
        boolean isAvailable = signUpService.isAvailableId(id);
        log.info("[SERVER] ID availability={}", isAvailable);
        return isAvailable;
    }

    @GetMapping("/check-private")
    public Boolean check(@RequestParam String name, @RequestParam String rrn) {
        log.info("[SERVER] Checking user={}({})", name, rrn);
        boolean isAvailable = signUpService.isAvailableUser(name, rrn);
        log.info("[SERVER] User availability={}", isAvailable);
        return isAvailable;
    }
}
