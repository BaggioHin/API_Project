package com.ohci.hello_spring_boot.service;

import com.nimbusds.jose.JOSEException;
import com.ohci.hello_spring_boot.DTO.request.AuthenticationRequest;
import com.ohci.hello_spring_boot.DTO.request.IntrospectRequest;
import com.ohci.hello_spring_boot.DTO.request.LogoutRequest;
import com.ohci.hello_spring_boot.DTO.request.RefreshTokenRequest;
import com.ohci.hello_spring_boot.DTO.respone.AuthenticationResponse;
import com.ohci.hello_spring_boot.DTO.respone.IntrospectResponse;

import java.text.ParseException;

//@Service
public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;
}
