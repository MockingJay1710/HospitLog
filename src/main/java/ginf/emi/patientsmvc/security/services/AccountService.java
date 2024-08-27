package ginf.emi.patientsmvc.security.services;

import ginf.emi.patientsmvc.security.entities.AppRole;
import ginf.emi.patientsmvc.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loadUserByUsername(String username);
}
