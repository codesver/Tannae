package codesver.tannae.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    @GetMapping("/checkId")
    public Boolean checkId(@RequestParam String id) {
        log.info("ID={}", id);
        return true;
    }
}
