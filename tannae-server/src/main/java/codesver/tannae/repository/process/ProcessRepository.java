package codesver.tannae.repository.process;

import codesver.tannae.domain.Process;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository {

    void save(Process process);

    List<Process> findByGenderShare(boolean gender, boolean share);

    void updatePath(Process process);

    Optional<Process> findProcessByVsn(int vsn);
}
