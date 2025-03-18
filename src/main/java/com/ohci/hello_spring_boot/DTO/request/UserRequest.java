package com.ohci.hello_spring_boot.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String roleName="USER";
    private String userName;
    private String password;
    private String fullName;
    private String address;
    private String email;
    private String phone;
    private String gender;
    private Integer point;
    private String rank;
//    private Integer isActive;
}
