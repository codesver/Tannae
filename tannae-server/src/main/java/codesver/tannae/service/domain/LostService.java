package codesver.tannae.service.domain;

import codesver.tannae.dto.LostDTO;
import codesver.tannae.dto.RegisterLostDTO;
import codesver.tannae.repository.lost.LostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LostService {

    private final LostRepository lostRepository;

    public List<LostDTO> getLosts() {
        return lostRepository.findAll();
    }

    @Transactional
    public Boolean postLost(RegisterLostDTO dto) {
        lostRepository.registerLost(dto.getLost(), dto.getVsn());
        return true;
    }
}
