package ginf.emi.patientsmvc.security.repositories;

import ginf.emi.patientsmvc.security.entities.AppRole;
import ginf.emi.patientsmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}
