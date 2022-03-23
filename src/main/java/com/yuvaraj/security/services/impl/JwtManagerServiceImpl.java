package com.yuvaraj.security.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.yuvaraj.security.helpers.Constants;
import com.yuvaraj.security.services.JwtManagerService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;

import static com.yuvaraj.security.helpers.Constants.EnvironmentVariables.*;
import static com.yuvaraj.security.helpers.KeyHelper.getPrivateKey;
import static com.yuvaraj.security.helpers.KeyHelper.getPublicKey;

@Service
public class JwtManagerServiceImpl implements JwtManagerService {

    private final Algorithm algorithm;
    private final String privateKey;
    private final String publicKey;

    public JwtManagerServiceImpl() throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.publicKey = Preconditions.checkNotNull(Constants.getEnvOrProperty(ASYMMETRIC_PUBLIC_KEY), ASYMMETRIC_PUBLIC_KEY + " need to be configured");
        this.privateKey = Preconditions.checkNotNull(Constants.getEnvOrProperty(ASYMMETRIC_PRIVATE_KEY), ASYMMETRIC_PRIVATE_KEY + " need to be configured");
        this.algorithm = Algorithm.RSA256(getPublicKey(this.publicKey), getPrivateKey(this.privateKey));
    }

    @Override
    public String generateJwtToken(Map<String, ?> payload) {
        return JWT.create().withPayload(payload).sign(algorithm);
    }

    @Override
    public Object extractJwtPayload(String token, Class<?> payloadClass) throws IOException {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return new ObjectMapper().readValue(Base64.getDecoder().decode(decodedJWT.getPayload()), payloadClass);
    }
}
