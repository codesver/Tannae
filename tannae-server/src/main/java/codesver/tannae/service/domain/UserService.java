package codesver.tannae.service.domain;

import codesver.tannae.domain.User;
import codesver.tannae.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Boolean isDuplicateId(String id) {
        log.info("[SERVICE-USER {} : CHECK_DUPLICATE_ID] ID={}", Thread.currentThread().getId(), id);
        Optional<User> optionalUser = userRepository.findById(id);
        log.info("[SERVICE-USER {} : CHECK_DUPLICATE_ID_RESULT] DUPLICATED={}", Thread.currentThread().getId(), optionalUser.isPresent());
        return optionalUser.isPresent();
    }

    public Boolean checkDuplicatePrivate(String name, String rrn) {
        log.info("[SERVICE-USER {} : CHECK_PRIVATE] NAME={} RRN={}", Thread.currentThread().getId(), name, rrn);
        Optional<User> optionalUser = userRepository.findByPrivate(name, rrn);
        log.info("[SERVICE-USER {} : CHECK_PRIVATE_RESULT] DUPLICATED={}", Thread.currentThread().getId(), optionalUser.isPresent());
        return optionalUser.isPresent();
    }
}
