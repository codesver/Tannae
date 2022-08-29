package codesver.tannae.service;

import codesver.tannae.dto.CheckAvailableDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleFinderService {

    public void findVehicle(CheckAvailableDTO dto) {
        Boolean share = dto.getShare();
    }
}
