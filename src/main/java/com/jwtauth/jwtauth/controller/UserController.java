package com.jwtauth.jwtauth.controller;

import com.jwtauth.jwtauth.Entity.AppUser;
import com.jwtauth.jwtauth.Entity.Role;
import com.jwtauth.jwtauth.dto.AddRoleToUserDto;
import com.jwtauth.jwtauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
@Autowired
private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers (){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveUser(Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public  ResponseEntity<?>  addRoleToUser(AddRoleToUserDto addRoleToUserDto){
        userService.addRoleToUser(addRoleToUserDto.getUserName(), addRoleToUserDto.getRoleName());
        return ResponseEntity.ok().build();
    }


}
