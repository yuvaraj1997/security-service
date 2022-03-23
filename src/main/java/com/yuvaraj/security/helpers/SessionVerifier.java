package com.yuvaraj.security.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.security.models.token.SessionToken;

public class SessionVerifier {

    final SessionToken sessionToken;

    public SessionVerifier(JsonNode jsonNode) {
        this.sessionToken = new ObjectMapper().convertValue(jsonNode, SessionToken.class);
    }

    public void verify() throws Exception {
        if (null == sessionToken.getCustomerId() || sessionToken.getCustomerId().isEmpty()) {
            throw new Exception("Invalid token customer id not found");
        }
    }
}
