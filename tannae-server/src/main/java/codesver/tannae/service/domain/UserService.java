package codesver.tannae.service.domain;

import codesver.tannae.domain.User;
import codesver.tannae.domain.Vehicle;
import codesver.tannae.dto.AccountDTO;
import codesver.tannae.dto.FoundAccountDTO;
import codesver.tannae.repository.user.UserRepository;
import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public Boolean join(User user) {
        log.info("[SERVICE-USER {} JOIN] USER={}", Thread.currentThread().getId(), user);
        userRepository.save(user);
        log.info("[SERVICE-USER {} JOIN_RESULT] JOINED", Thread.currentThread().getId());
        return true;
    }

    public AccountDTO login(String id, String pw) {
        log.info("[SERVICE-USER {} LOGIN] LOGIN WITH ID={} PW={}", Thread.currentThread().getId(), id, pw);
        Optional<User> optionalUser = userRepository.findByIdPw(id, pw);
        int vsn = 0;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (optionalUser.get().getIsDriver()) {
                Optional<Vehicle> optionalVehicle = vehicleRepository.findVehicleByUsn(user.getUsn());
                vsn = optionalVehicle.isPresent() ? optionalVehicle.get().getVsn() : 0;
            }
        }
        log.info("[SERVICE-USER {} LOGIN_RESULT] LOGIN={}", Thread.currentThread().getId(), optionalUser.isPresent());
        return new AccountDTO(optionalUser.orElse(new User()).convertToDTO(), vsn, optionalUser.isPresent());
    }

    public FoundAccountDTO findAccount(String name, String rrn) {
        log.info("[SERVICE-USER {} FIND_ACCOUNT] FIND ACCOUNT BY NAME={} RRN={}", Thread.currentThread().getId(), name, rrn);
        Optional<User> optionalUser = userRepository.findByNameRrn(name, rrn);
        boolean founded = optionalUser.isPresent();
        log.info("[SERVICE-USER {} FIND_ACCOUNT_RESULT] FOUNDED={}", Thread.currentThread().getId(), founded);
        return new FoundAccountDTO(founded ? optionalUser.get().getId() : null,
                founded ? optionalUser.get().getPw() : null, founded);
    }

    public Boolean isDuplicateId(String id) {
        log.info("[SERVICE-USER {} : IS_DUPLICATE_ID] ID={}", Thread.currentThread().getId(), id);
        Optional<User> optionalUser = userRepository.findById(id);
        log.info("[SERVICE-USER {} : IS_DUPLICATE_ID_RESULT] DUPLICATED={}", Thread.currentThread().getId(), optionalUser.isPresent());
        return optionalUser.isPresent();
    }

    public Boolean isDuplicatePrivate(String name, String rrn) {
        log.info("[SERVICE-USER {} : IS_DUPLICATE_PRIVATE] NAME={} RRN={}", Thread.currentThread().getId(), name, rrn);
        Optional<User> optionalUser = userRepository.findByPrivate(name, rrn);
        log.info("[SERVICE-USER {} : IS_DUPLICATE_PRIVATE_RESULT] DUPLICATED={}", Thread.currentThread().getId(), optionalUser.isPresent());
        return optionalUser.isPresent();
    }

    public Integer charge(int usn, int point) {
        log.info("[SERVICE-USER {} : CHARGE] CHARGING POINT {} TO USER {}", Thread.currentThread().getId(), point, usn);
        Integer chargedPoint = userRepository.chargePoint(usn, point);
        log.info("[SERVICE-USER {} : CHARGE_RESULT] POINT={}", Thread.currentThread().getId(), chargedPoint);
        return chargedPoint;
    }
}
