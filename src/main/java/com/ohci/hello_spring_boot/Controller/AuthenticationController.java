package com.ohci.hello_spring_boot.Controller;

import com.nimbusds.jose.JOSEException;
import com.ohci.hello_spring_boot.DTO.request.AuthenticationRequest;
import com.ohci.hello_spring_boot.DTO.request.IntrospectRequest;
import com.ohci.hello_spring_boot.DTO.request.LogoutRequest;
import com.ohci.hello_spring_boot.DTO.request.RefreshTokenRequest;
import com.ohci.hello_spring_boot.DTO.respone.ApiResponse;
import com.ohci.hello_spring_boot.DTO.respone.AuthenticationResponse;
import com.ohci.hello_spring_boot.DTO.respone.IntrospectResponse;
import com.ohci.hello_spring_boot.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refreshToken")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refreshToken(request))
                .build();
    }

}
