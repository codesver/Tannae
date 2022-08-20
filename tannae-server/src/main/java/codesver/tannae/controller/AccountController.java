package codesver.tannae.controller;

import codesver.tannae.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AccountController {

    private final UserRepository userRepository;

    @GetMapping("/checkId")
    public Boolean checkId(@RequestParam String id) {
        return userRepository.checkId(id);
    }
}
