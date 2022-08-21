package codesver.tannae.service.user;

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
        return userRepository.countById(id) == 0;
    }

    public boolean isAvailableUser(String name, String rrn) {
        return userRepository.countByPrivate(name, rrn) == 0;
    }
}
