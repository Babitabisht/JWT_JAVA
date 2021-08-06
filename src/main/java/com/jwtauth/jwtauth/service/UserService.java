package com.jwtauth.jwtauth.service;

import com.jwtauth.jwtauth.Entity.AppUser;
import com.jwtauth.jwtauth.Entity.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser( AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    AppUser getUser(String userName);
    List<AppUser> getUsers();
}
