package com.yuvaraj.securityservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.yuvaraj.securityservice.helpers.*;
import com.yuvaraj.securityservice.models.DefaultToken;
import com.yuvaraj.securityservice.services.TokenValidationService;
import com.yuvaraj.securityservice.services.cipher.symmetric.SymmetricKeyAESCipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.yuvaraj.securityservice.helpers.Constants.EnvironmentVariables.INIT_VECTOR_KEY;
import static com.yuvaraj.securityservice.helpers.Constants.EnvironmentVariables.SYMMETRIC_SECRET_KEY;

@Service
public class TokenValidationServiceImpl implements TokenValidationService {

    private final String symmetricSecretKey;
    private final String initVectorKey;
    Logger logger = LoggerFactory.getLogger(getClass());

    public TokenValidationServiceImpl() {
        this.symmetricSecretKey = Preconditions.checkNotNull(Constants.getEnvOrProperty(SYMMETRIC_SECRET_KEY), SYMMETRIC_SECRET_KEY + " need to be configured");
        this.initVectorKey = Preconditions.checkNotNull(Constants.getEnvOrProperty(INIT_VECTOR_KEY), INIT_VECTOR_KEY + " need to be configured");
    }

    @Override
    public void verifyToken(DefaultToken defaultToken, List<String> tokenTypes) throws Exception {
        logger.info("verifyingToken defaultToken={}, tokenTypes={}", JsonHelper.toJson(defaultToken), tokenTypes);
        validateExpiry(defaultToken.getCreateTime(), defaultToken.getExpiryInMs());
        JsonNode jsonNode = new ObjectMapper().readTree(decrypt(defaultToken.getSecret()));
        String tokenType = validateAndGetTokenType(jsonNode, tokenTypes);
        switch (TokenType.valueOf(tokenType)) {
            case REFRESH:
                new RefreshVerifier(jsonNode).verify();
                break;
            case SESSION:
                new SessionVerifier(jsonNode).verify();
                break;
            default:
                throw new Exception("Token Type is not handle");
        }
    }

    private String validateAndGetTokenType(JsonNode jsonNode, List<String> tokenTypes) throws Exception {
        String tokenType = jsonNode.has("type") ? jsonNode.get("type").asText() : null;
        if (null == tokenType || tokenType.isEmpty() || !tokenTypes.contains(tokenType)) {
            throw new Exception("Invalid token, tokenType cannot be null or empty or not matched as requested");
        }
        return tokenType;
    }

    private void validateExpiry(long createTime, long expiryInMs) throws Exception {
        if (expiryInMs == -1) {
            return;
        }
        if ((createTime + expiryInMs) <= System.currentTimeMillis()) {
            throw new Exception("Token expired");
        }
    }

    private String decrypt(String encryptedToken) throws JsonProcessingException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException {
        try {
            SymmetricKeyAESCipher symmetricKeyAESCipher = new SymmetricKeyAESCipher();
            symmetricKeyAESCipher.setKey(this.symmetricSecretKey);
            symmetricKeyAESCipher.setInitVector(this.initVectorKey);
            return symmetricKeyAESCipher.decrypt(encryptedToken);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
