package codesver.tannae.service;

import codesver.tannae.domain.DRO;
import codesver.tannae.domain.Process;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.repository.process.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessFinder {

    private final ProcessRepository processRepository;

    public DRO<Process> findProcess(ServiceRequestDTO dto) {
        log.info("[SERVICE-PROCESS-FINDER : FIND_PROCESSES] Find processes gender={} share={}", dto.getGender(), dto.getShare());
        List<Process> processes = processRepository.findByGenderShare(dto.getGender(), dto.getShare());
        if (processes.isEmpty()) {
            return new DRO<>(2);
        }





        return null;
    }
}
