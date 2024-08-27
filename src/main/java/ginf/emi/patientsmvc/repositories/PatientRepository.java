package ginf.emi.patientsmvc.repositories;

import ginf.emi.patientsmvc.entities.Patient;
import org.hibernate.query.criteria.JpaCteCriteriaType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByNameContains(String kw, Pageable pageable);
}
