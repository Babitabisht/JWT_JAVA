package com.jwtauth.jwtauth.repo;

import com.jwtauth.jwtauth.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String username);
}
