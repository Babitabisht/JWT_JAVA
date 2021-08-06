package com.jwtauth.jwtauth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddRoleToUserDto {
    private String userName;
    private String roleName;
}
