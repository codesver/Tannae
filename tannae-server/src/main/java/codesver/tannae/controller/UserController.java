package codesver.tannae.controller;

import codesver.tannae.dto.AccountDTO;
import codesver.tannae.dto.FoundAccountDTO;
import codesver.tannae.dto.SignUpUserDTO;
import codesver.tannae.service.domain.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public AccountDTO getUser(@RequestParam String id, @RequestParam String pw) {
        log.info("[CONTROLLER-USER {} : GET_USER] GET /users?id={}&pw={}", Thread.currentThread().getId(), id, pw);
        return userService.login(id, pw);
    }

    @PostMapping
    public Boolean postUser(@RequestBody SignUpUserDTO dto) {
        log.info("[CONTROLLER-USER {}: POST_USER] POST /users body={}", Thread.currentThread().getId(), dto);
        return userService.join(dto.convertToEntity());
    }

    @GetMapping("/account")
    public FoundAccountDTO getAccount(@RequestParam String name, @RequestParam String rrn) {
        log.info("[CONTROLLER-USER {}: GET_ACCOUNT] /users/account?name={}&rrn={}", Thread.currentThread().getId(), name, rrn);
        return userService.findAccount(name, rrn);
    }

    @PatchMapping("/{usn}/point")
    public Integer patchPoint(@PathVariable Integer usn, @RequestBody Integer point) {
        log.info("[CONTROLLER-USER {} : PATCH_POINT] PATCH /users/{}/point body={}", Thread.currentThread().getId(), usn, point);
        return userService.charge(usn, point);
    }

    @GetMapping("/duplicate-id")
    public Boolean duplicateId(@RequestParam String id) {
        log.info("[CONTROLLER-USER {} : DUPLICATE_ID] GET /users/duplicate-id?id={}", Thread.currentThread().getId(), id);
        return userService.isDuplicateId(id);
    }

    @GetMapping("/duplicate-private")
    public Boolean duplicatePrivate(@RequestParam String name, @RequestParam String rrn) {
        log.info("[CONTROLLER-USER {} : DUPLICATE_PRIVATE] GET /users/duplicate-private?name={}&rrn={}", Thread.currentThread().getId(), name, rrn);
        return userService.isDuplicatePrivate(name, rrn);
    }
}
