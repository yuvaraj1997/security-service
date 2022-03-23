package com.yuvaraj.security.services;

import com.yuvaraj.security.models.DefaultToken;

import java.util.List;

public interface TokenValidationService {

    void verifyToken(DefaultToken defaultToken, List<String> tokenTypes) throws Exception;
}
