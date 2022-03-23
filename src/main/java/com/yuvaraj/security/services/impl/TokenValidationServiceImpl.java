package com.yuvaraj.security.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.security.helpers.JsonHelper;
import com.yuvaraj.security.helpers.RefreshVerifier;
import com.yuvaraj.security.helpers.SessionVerifier;
import com.yuvaraj.security.helpers.TokenType;
import com.yuvaraj.security.models.DefaultToken;
import com.yuvaraj.security.providers.SimpleSymmetricCipherProvider;
import com.yuvaraj.security.services.TokenValidationService;
import com.yuvaraj.security.services.cipher.symmetric.SimpleSymmetricCipher;
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

@Service
public class TokenValidationServiceImpl implements TokenValidationService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final SimpleSymmetricCipher simpleSymmetricCipher;

    public TokenValidationServiceImpl() {
        this.simpleSymmetricCipher = new SimpleSymmetricCipherProvider().get();
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
            return simpleSymmetricCipher.decrypt(encryptedToken);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
