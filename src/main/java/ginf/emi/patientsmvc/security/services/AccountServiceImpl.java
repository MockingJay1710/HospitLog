package ginf.emi.patientsmvc.security.services;

import ginf.emi.patientsmvc.security.entities.AppRole;
import ginf.emi.patientsmvc.security.entities.AppUser;
import ginf.emi.patientsmvc.security.repositories.AppRoleRepository;
import ginf.emi.patientsmvc.security.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;
    AppUserRepository userRepository;
    AppRoleRepository roleRepository;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser user = userRepository.findByUsername(username);
        if(user != null) throw new RuntimeException("User already exists");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Passwords do not match");
        user = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if(appRole != null) throw new RuntimeException("Role already exists");
        appRole = AppRole.builder().role(role).build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppRole appRole = appRoleRepository.findById(role).get();
        AppUser appUser = userRepository.findByUsername(username);
        appUser.getRoles().add(appRole);
        //appUserRepository.save(appUser);  pas besoin de faire car la methode est transactionnelle
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppRole appRole = appRoleRepository.findById(role).get();
        AppUser appUser = userRepository.findByUsername(username);
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
