package codesver.tannae.repository.lost;

import codesver.tannae.domain.Lost;
import codesver.tannae.dto.LostDTO;

import java.util.List;

public interface LostRepository {

    List<LostDTO> findAll();

    void registerLost(String lost, int vsn);
}
