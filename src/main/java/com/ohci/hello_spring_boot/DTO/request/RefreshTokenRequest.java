package com.ohci.hello_spring_boot.DTO.request;

import lombok.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {
    private String token;
}
