package com.yuvaraj.security.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuvaraj.security.models.AuthSuccessfulResponse;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface JwtGenerationService {

    AuthSuccessfulResponse generateRefreshToken(String customerId) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException;

    AuthSuccessfulResponse generateSessionToken(String customerId) throws JsonProcessingException, InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException;
}
