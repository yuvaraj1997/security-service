package com.yuvaraj.securityservice.services;

import java.io.IOException;
import java.util.Map;

public interface JwtManagerService {

    String generateJwtToken(Map<String, ?> payload);

    Object extractJwtPayload(String token, Class<?> payloadClass) throws IOException;
}
