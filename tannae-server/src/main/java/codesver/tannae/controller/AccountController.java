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
        return signUpService.isAvailableId(id);
    }

}
