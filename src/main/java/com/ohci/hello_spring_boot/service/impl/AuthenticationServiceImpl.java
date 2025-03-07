package com.ohci.hello_spring_boot.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.ohci.hello_spring_boot.DTO.request.AuthenticationRequest;
import com.ohci.hello_spring_boot.DTO.request.IntrospectRequest;
import com.ohci.hello_spring_boot.DTO.request.LogoutRequest;
import com.ohci.hello_spring_boot.DTO.request.RefreshTokenRequest;
import com.ohci.hello_spring_boot.DTO.respone.AuthenticationResponse;
import com.ohci.hello_spring_boot.DTO.respone.IntrospectResponse;
import com.ohci.hello_spring_boot.Exception.AppException;
import com.ohci.hello_spring_boot.Exception.ErrorCode;
import com.ohci.hello_spring_boot.repository.Entity.InvalidationTokenEntity;
import com.ohci.hello_spring_boot.repository.Entity.UserEntity;
import com.ohci.hello_spring_boot.repository.InvalidationTokenRepository;
import com.ohci.hello_spring_boot.repository.UserRepository;
import com.ohci.hello_spring_boot.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InvalidationTokenRepository invalidationTokenRepository;

    //    @NonFinal
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;
    //
//    @NonFinal
    @Value("${jwt.valid-duration}")
    long VALID_DURATION;

    //    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    //    public String generateToken(AuthenticationRequest request) {
    //    Learn more
    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isRefresh = true;
        try{
            verifyToken(token, isRefresh);
        }catch (AppException e){
            isRefresh = false;
        }
        return IntrospectResponse.builder().valid(isRefresh).build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            SignedJWT signedJWT = verifyToken(request.getToken(),false);

            String JId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

            InvalidationTokenEntity invalidationTokenEntity = InvalidationTokenEntity.builder()
                    .id(JId).expiryTime(expirationDate).build();

            invalidationTokenRepository.save(invalidationTokenEntity);
        }catch (AppException e){
            log.error("Token expired");
        }

    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(),false);
        String JId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidationTokenEntity invalidationTokenEntity = InvalidationTokenEntity.builder()
                .id(JId).expiryTime(expirationDate).build();

        invalidationTokenRepository.save(invalidationTokenEntity);
        var user = userRepository.findByUserName(request.getToken())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String token =generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    public String generateToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("Baggio")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("SCOPE", buildscope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh) ? signedJWT.getJWTClaimsSet().getExpirationTime() :
                new Date(signedJWT.getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION,ChronoUnit.SECONDS).toEpochMilli());

        var verified = signedJWT.verify(verifier);
        if(!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);
        if(invalidationTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }

    private String buildscope(UserEntity user) {
        StringBuilder builder = new StringBuilder();
        if(!CollectionUtils.isEmpty(user.getRoles())){
           user.getRoles().forEach(role -> {
                builder.append("SCOPE_").append(role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission-> builder.append(permission.getName()));
                }
                builder.append(" ");
           });
        }
        return builder.toString().trim();
    }
}

