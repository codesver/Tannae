package codesver.tannae.service.domain;

import codesver.tannae.repository.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public Boolean switchRunningState(Integer vsn, Boolean running) {
        vehicleRepository.switchRun(vsn, running);
        return running;
    }
}
