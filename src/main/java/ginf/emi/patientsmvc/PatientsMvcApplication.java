package ginf.emi.patientsmvc;

import ginf.emi.patientsmvc.entities.Patient;
import ginf.emi.patientsmvc.repositories.PatientRepository;
import ginf.emi.patientsmvc.security.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientsMvcApplication.class, args);
	}

	//@Bean
	CommandLineRunner start(PatientRepository patientRepository) {
		return args -> {
			patientRepository.save(new Patient(null, "Otmane", new Date(), false, 12));
			patientRepository.save(new Patient(null, "Adam", new Date(), true, 14));
			patientRepository.save(new Patient(null, "James", new Date(), false, 15));

			patientRepository.findAll().forEach(p -> {
					System.out.println(p.getName());
			});
		};
	}

	//@Bean
	CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
		return args -> {
			UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user1");
			if (u1==null)
				jdbcUserDetailsManager.createUser(
						User.withUsername("user11").password(passwordEncoder().encode("password1")).authorities("USER").build()
				);
			UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user2");
			if (u2==null)
			jdbcUserDetailsManager.createUser(
					User.withUsername("user22").password(passwordEncoder().encode("password2")).authorities("USER").build()
			);
			UserDetails u3 = jdbcUserDetailsManager.loadUserByUsername("admin");
			if (u3==null)
			jdbcUserDetailsManager.createUser(
					User.withUsername("admin2").password(passwordEncoder().encode("admin")).authorities("USER", "ADMIN").build()
			);

		};
	}

	//@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService) {
		return args -> {
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			accountService.addNewUser("user1","1234","user1@user.com","1234");
			accountService.addNewUser("user2","1234","user2@user.com","1234");
			accountService.addNewUser("admin","admin","admin@admin.com","admin");
			accountService.addRoleToUser("user1","USER");
			accountService.addRoleToUser("user2","USER");
			accountService.addRoleToUser("admin","USER");
			accountService.addRoleToUser("admin","ADMIN");
		};
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
