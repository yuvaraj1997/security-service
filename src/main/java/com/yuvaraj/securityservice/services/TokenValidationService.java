package com.yuvaraj.securityservice.services;

import com.yuvaraj.securityservice.models.DefaultToken;

import java.util.List;

public interface TokenValidationService {

    void verifyToken(DefaultToken defaultToken, List<String> tokenTypes) throws Exception;
}
