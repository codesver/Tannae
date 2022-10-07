package codesver.tannae.repository.process;

import codesver.tannae.domain.Process;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ProcessJpaRepository implements ProcessRepository {

    private final ProcessSpringDataJpaRepository repository;

    @Override
    public void save(Process process) {
        log.info("[REPOSITORY-PROCESS {} : SAVE] INSERT INTO PROCESS VALUES({})", Thread.currentThread().getId(), process);
        repository.save(process);
        log.info("[REPOSITORY-PROCESS {} : SAVE_RESULT] SAVED", Thread.currentThread().getId());
    }

    @Override
    public List<Process> findByGenderShare(boolean gender, boolean share) {
        log.info("[REPOSITORY-PROCESS {} : FIND_BY_GENDER_SHARE] SELECT * FROM PROCESS p LEFT OUTER JOIN VEHICLE v ON p.vsn=? WHERE p.gender={} AND p.share={} and v.num < {}", Thread.currentThread().getId(), gender, share, 3);
        List<Process> processes = repository.findProcessesByVehicle_GenderAndVehicle_ShareAndVehicle_NumLessThan(gender, share, 3);
        log.info("[REPOSITORY-PROCESS {} : FIND_BY_GENDER_SHARE_RESULT] FOUND PROCESS NUM={}", Thread.currentThread().getId(), processes.size());
        return processes;
    }

    @Override
    public void updatePath(Process process) {
        log.info("[REPOSITORY-PROCESS {} : UPDATE_PATH] UPDATE PROCESS SET PATH={} WHERE PSN={}", Thread.currentThread().getId(), process.getPath(), process.getPsn());
        Optional<Process> optionalProcess = repository.findById(process.getPsn());
        optionalProcess.get().setPath(process.getPath());
        log.info("[REPOSITORY-PROCESS {} : UPDATE_PATH_RESULT] UPDATED", Thread.currentThread().getId());
    }

    @Override
    public Process increasePassed(int vsn) {
        log.info("[REPOSITORY-PROCESS {} : INCREASE_PASSED] UPDATE PROCESS SET PASSED = PASSED + 1 WHERE VSN={}", Thread.currentThread().getId(), vsn);
        Optional<Process> optionalProcess = repository.findProcessByVehicle_Vsn(vsn);
        Process process = optionalProcess.get();
        process.setPassed(process.getPassed() + 1);
        log.info("[REPOSITORY-PROCESS {} : INCREASE_PASSED_RESULT] INCREASED PASSED={}", Thread.currentThread().getId(), process.getPassed());
        return process;
    }

    @Override
    public void deleteProcess(int vsn) {
        log.info("[REPOSITORY-PROCESS {} : DELETE_PROCESS] DELETE FROM PROCESS WHERE VSN={}", Thread.currentThread().getId(), vsn);
        repository.deleteProcessByVehicle_Vsn(vsn);
        log.info("[REPOSITORY-PROCESS {} : DELETE_PROCESS_RESULT] DELETED", Thread.currentThread().getId());
    }
}
