package com.jwtauth.jwtauth.service;

import com.jwtauth.jwtauth.Entity.AppUser;
import com.jwtauth.jwtauth.Entity.Role;
import com.jwtauth.jwtauth.repo.AppUserRepo;
import com.jwtauth.jwtauth.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl  implements   UserService, UserDetailsService{
    @Autowired
    private  AppUserRepo appUserRepo;

    @Autowired
    private  RoleRepo roleRepo;


    private final PasswordEncoder passwordEncoder;


    @Override
    public AppUser saveUser(AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser user = appUserRepo.findByUserName(userName);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String userName) {
        return appUserRepo.findByUserName(userName);
    }

    @Override
    public List<AppUser> getUsers() {
        return appUserRepo.findAll();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepo.findByUserName(username);
        if(user==null){
            log.error("User {} not found in the database ",user);
            throw  new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User found in the database {}",user.getUserName());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));});
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),authorities );
    }
}
