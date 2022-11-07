package codesver.tannae.repository.lost;

import codesver.tannae.domain.Lost;

import java.util.List;

public interface LostRepository {

    List<Lost> findAll();
}
