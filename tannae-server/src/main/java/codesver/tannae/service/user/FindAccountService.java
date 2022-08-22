package codesver.tannae.service.user;

import codesver.tannae.domain.User;
import codesver.tannae.dto.user.FoundAccountDTO;
import codesver.tannae.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindAccountService {

    private final UserRepository userRepository;

    public FoundAccountDTO findAccount(String name, String rrn) {
        log.info("[SERVICE-USER-FIND-ACCOUNT : FIND_ACCOUNT] Finding account by name={} rrn={}", name, rrn);
        Optional<User> foundUser = userRepository.findByNameRrn(name, rrn);
        User user = foundUser.orElse(new User());
        return new FoundAccountDTO(user.getId(), user.getPw(), foundUser.isPresent());
    }
}
