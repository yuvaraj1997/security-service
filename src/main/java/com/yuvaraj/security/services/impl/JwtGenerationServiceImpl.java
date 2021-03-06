package com.yuvaraj.security.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.yuvaraj.security.helpers.Constants;
import com.yuvaraj.security.models.AuthSuccessfulResponse;
import com.yuvaraj.security.models.DefaultToken;
import com.yuvaraj.security.models.token.RefreshToken;
import com.yuvaraj.security.models.token.SessionToken;
import com.yuvaraj.security.services.JwtGenerationService;
import com.yuvaraj.security.services.JwtManagerService;
import com.yuvaraj.security.services.cipher.symmetric.SymmetricKeyAESCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.yuvaraj.security.helpers.Constants.EnvironmentVariables.*;
import static com.yuvaraj.security.helpers.DateHelper.convertDateForEndResult;
import static com.yuvaraj.security.helpers.DateHelper.convertTimeStampToDate;

@Service
public class JwtGenerationServiceImpl implements JwtGenerationService {

    private final JwtManagerService jwtManagerService;
    private final String symmetricSecretKey;
    private final String initVectorKey;
    private final long refreshTokenExpiryInMs;
    private final long sessionTokenExpiryInMs;

    @Autowired
    public JwtGenerationServiceImpl(JwtManagerService jwtManagerService) {
        this.jwtManagerService = jwtManagerService;
        this.symmetricSecretKey = Preconditions.checkNotNull(Constants.getEnvOrProperty(SYMMETRIC_SECRET_KEY), SYMMETRIC_SECRET_KEY + " need to be configured");
        this.initVectorKey = Preconditions.checkNotNull(Constants.getEnvOrProperty(INIT_VECTOR_KEY), INIT_VECTOR_KEY + " need to be configured");
        this.refreshTokenExpiryInMs = Long.parseLong(Preconditions.checkNotNull(Constants.getEnvOrProperty(REFRESH_TOKEN_EXPIRY), REFRESH_TOKEN_EXPIRY + " need to be configured"));
        this.sessionTokenExpiryInMs = Long.parseLong(Preconditions.checkNotNull(Constants.getEnvOrProperty(SESSION_TOKEN_EXPIRY), SESSION_TOKEN_EXPIRY + " need to be configured"));
    }

    @Override
    public AuthSuccessfulResponse generateRefreshToken(String customerId) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Preconditions.checkArgument(null != customerId && !customerId.isEmpty(), "customerId cannot be null");
        DefaultToken defaultToken = new DefaultToken(convertToJsonAndEncrypt(new RefreshToken(customerId)), this.refreshTokenExpiryInMs == -1 ? this.refreshTokenExpiryInMs : this.refreshTokenExpiryInMs * 1000);
        return buildAuthSuccessfulResponse(jwtManagerService.generateJwtToken(new ObjectMapper().convertValue(defaultToken, Map.class)), defaultToken.getCreateTime(), defaultToken.getExpiryInMs(), customerId);
    }

    private AuthSuccessfulResponse buildAuthSuccessfulResponse(String token, long createTime, long expiryInMs, String customerId) {
        return new AuthSuccessfulResponse(customerId, token, createTime, expiryInMs, convertDateForEndResult(convertTimeStampToDate(createTime)));
    }

    @Override
    public AuthSuccessfulResponse generateSessionToken(String customerId) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Preconditions.checkArgument(null != customerId && !customerId.isEmpty(), "customerId cannot be null");
        DefaultToken defaultToken = new DefaultToken(convertToJsonAndEncrypt(new SessionToken(customerId)), this.sessionTokenExpiryInMs == -1 ? this.sessionTokenExpiryInMs : this.sessionTokenExpiryInMs * 1000);
        return buildAuthSuccessfulResponse(jwtManagerService.generateJwtToken(new ObjectMapper().convertValue(defaultToken, Map.class)), defaultToken.getCreateTime(), defaultToken.getExpiryInMs(), customerId);
    }

    private String convertToJsonAndEncrypt(Object object) throws JsonProcessingException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException {
        try {
            SymmetricKeyAESCipher symmetricKeyAESCipher = new SymmetricKeyAESCipher();
            symmetricKeyAESCipher.setKey(this.symmetricSecretKey);
            symmetricKeyAESCipher.setInitVector(this.initVectorKey);
            return symmetricKeyAESCipher.encrypt(new ObjectMapper().writeValueAsString(object));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
