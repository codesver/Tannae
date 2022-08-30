package codesver.tannae.repository.service;

import codesver.tannae.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceSpringDataJpaRepository extends JpaRepository<Service, Integer> {
}
