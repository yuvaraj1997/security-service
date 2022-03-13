package com.yuvaraj.securityservice.helpers;

import static com.yuvaraj.securityservice.helpers.Constants.EnvironmentVariables.SYMMETRIC_SECRET_KEY;

public class Constants {

    public static String getEnvOrProperty(String identifier) {
        String value = System.getProperty(identifier);
        if(null == value){
            value = System.getenv(identifier);
        }
        return value;
    }

    public static class EnvironmentVariables{

        public static final String SYMMETRIC_SECRET_KEY = "SYMMETRIC_SECRET_KEY";
        public static final String INIT_VECTOR_KEY = "INIT_VECTOR_KEY";
        public static final String ASYMMETRIC_PUBLIC_KEY = "ASYMMETRIC_PUBLIC_KEY";
        public static final String ASYMMETRIC_PRIVATE_KEY = "ASYMMETRIC_PRIVATE_KEY";
        public static final String REFRESH_TOKEN_EXPIRY = "REFRESH_TOKEN_EXPIRY";
        public static final String SESSION_TOKEN_EXPIRY = "SESSION_TOKEN_EXPIRY";

    }
}
