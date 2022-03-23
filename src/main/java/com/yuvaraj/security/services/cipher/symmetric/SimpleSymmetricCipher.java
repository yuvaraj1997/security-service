package com.yuvaraj.security.services.cipher.symmetric;

import com.yuvaraj.security.services.cipher.SimpleCipher;

public interface SimpleSymmetricCipher extends SimpleCipher {

    void setKey(String var1);

    void setInitVector(String var1);
}
