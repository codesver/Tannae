package codesver.tannae.service.user;

import codesver.tannae.domain.User;
import codesver.tannae.dto.user.LoginDTO;
import codesver.tannae.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    public LoginDTO login(String id, String pw) {
        log.info("[SERVICE-USER-LOGIN : LOGIN] Login account id={} pw={}", id, pw);
        Optional<User> foundUser = userRepository.findByIdPw(id, pw);
        return new LoginDTO(foundUser.orElse(new User()), foundUser.isPresent());
    }
}
