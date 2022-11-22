package codesver.tannae.repository.process;

import codesver.tannae.entity.Process;

import java.util.List;

public interface ProcessRepository {

    void save(Process process);

    List<Process> findByGenderShare(boolean gender, boolean share);

    void updatePath(Process process);

    Process increasePassed(int vsn);

    void deleteProcess(int vsn);
}
