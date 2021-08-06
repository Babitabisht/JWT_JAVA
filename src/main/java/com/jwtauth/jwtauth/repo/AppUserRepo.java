package com.jwtauth.jwtauth.repo;
import com.jwtauth.jwtauth.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository <AppUser, Long> {
    AppUser findByUserName(String username);

}
