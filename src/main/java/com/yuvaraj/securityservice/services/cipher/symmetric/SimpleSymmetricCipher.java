package com.yuvaraj.securityservice.services.cipher.symmetric;

import com.yuvaraj.securityservice.services.cipher.SimpleCipher;

public interface SimpleSymmetricCipher extends SimpleCipher {

    void setKey(String var1);

    void setInitVector(String var1);
}
