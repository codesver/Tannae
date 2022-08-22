package codesver.tannae.service.user;

import codesver.tannae.dto.user.SignUpUserDTO;
import codesver.tannae.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;

    public boolean isAvailableId(String id) {
        log.info("[SERVICE-USER-SIGN_UP : IS_AVAILABLE_ID] Check availability of ID={}", id);
        return userRepository.countById(id) == 0;
    }

    public boolean isAvailableUser(String name, String rrn) {
        log.info("[SERVICE-USER-SIGN_UP : IS_AVAILABLE_USER] Check availability of user name={} rrn={}", name, rrn);
        return userRepository.countByPrivate(name, rrn) == 0;
    }

    public boolean signUpSuccessfully(SignUpUserDTO dto) {
        log.info("[SERVICE-USER-SIGN_UP : SIGN_UP_SUCCESSFULLY] Signing new user ID={} name={}", dto.getId(), dto.getName());
        return userRepository.save(dto.toUser());
    }
}
