package com.yuvaraj.security.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.security.models.token.RefreshToken;

public class RefreshVerifier {

    final RefreshToken refreshToken;

    public RefreshVerifier(JsonNode jsonNode) {
        this.refreshToken = new ObjectMapper().convertValue(jsonNode, RefreshToken.class);
    }

    public void verify() throws Exception {
        if (null == refreshToken.getCustomerId() || refreshToken.getCustomerId().isEmpty()) {
            throw new Exception("Invalid token customer id not found");
        }
    }
}
