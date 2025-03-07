package com.ohci.hello_spring_boot.DTO.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String RoleName;
    private String userName;
    private String fullName;
    private String gender;
    Set<RoleResponse> roles;
}
