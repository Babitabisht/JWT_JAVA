package com.jwtauth.jwtauth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtauth.jwtauth.Entity.AppUser;
import com.jwtauth.jwtauth.Entity.Role;
import com.jwtauth.jwtauth.dto.AddRoleToUserDto;
import com.jwtauth.jwtauth.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ApiOperation(value="For getting user list",notes = "The API return entire record of user present in the system")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }


    @PostMapping("/role/save")
    public ResponseEntity<Role> saveUser(Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(AddRoleToUserDto addRoleToUserDto) {
        userService.addRoleToUser(addRoleToUserDto.getUserName(), addRoleToUserDto.getRoleName());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            String authorizationHeader = request.getHeader(AUTHORIZATION);
            String refresh_token = authorizationHeader.substring("Bearer ".length());
            
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decoded = verifier.verify(refresh_token);
            String Username = decoded.getSubject();
            AppUser user = userService.getUser(Username);


            String access_token = JWT.create().withSubject(user.getUserName()).withExpiresAt(new Date(System.currentTimeMillis())).withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList())).sign(algorithm);


            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);

            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);


        } catch (Exception ex) {
            response.setHeader("error", ex.getMessage());
            response.setStatus(FORBIDDEN.value());
            /*  response.sendError(  FORBIDDEN.value());*/
            Map<String, String> error = new HashMap<>();
            error.put("error_message", ex.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);

        }
    }


}
