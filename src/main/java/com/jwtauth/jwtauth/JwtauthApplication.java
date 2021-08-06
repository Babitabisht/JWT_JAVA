package com.jwtauth.jwtauth;

import com.jwtauth.jwtauth.Entity.AppUser;
import com.jwtauth.jwtauth.Entity.Role;
import com.jwtauth.jwtauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@Slf4j
public class JwtauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtauthApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		log.info("passwordEncoder---------->");
		return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return  args -> {
			userService.saveRole(new Role(1,"ROLE_USER"));
			userService.saveRole(new Role(2,"ROLE_MANAGER"));
			userService.saveRole(new Role(3,"ROLE_ADMIN"));
			userService.saveRole(new Role(4,"ROLE_SUPER_ADMIN"));

			userService.saveUser(new AppUser(1, "babita","bisht", "1234", new ArrayList<>()));
			/*userService.addRoleToUser("babita","ROLE_USER");*/
			/*userService.addRoleToUser("sia","ROLE_MANAGER");
			userService.addRoleToUser("kate","ROLE_ADMIN");*/
			/*userService.addRoleToUser("marry","ROLE_USER");*/

			/*



			userService.addRoleToUser("babita",  "ROLE_USER");
			userService.addRoleToUser("c",  "ROLE_MANAGER");
			userService.addRoleToUser("sia",  "ROLE_ADMIN");
			userService.addRoleToUser("hayden",  "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("kate",  "ROLE_USER");*/

		};
	}

}
