package com.yuvaraj.security.providers;

import com.google.common.base.Preconditions;
import com.yuvaraj.security.helpers.Constants;
import com.yuvaraj.security.services.cipher.symmetric.SimpleSymmetricCipher;
import com.yuvaraj.security.services.cipher.symmetric.SymmetricKeyAESCipher;

import java.util.ServiceLoader;

import static com.yuvaraj.security.helpers.Constants.EnvironmentVariables.INIT_VECTOR_KEY;
import static com.yuvaraj.security.helpers.Constants.EnvironmentVariables.SYMMETRIC_SECRET_KEY;

public class SimpleSymmetricCipherProvider implements ServiceLoader.Provider<SimpleSymmetricCipher> {

    private final String symmetricSecretKey = Preconditions.checkNotNull(Constants.getEnvOrProperty(SYMMETRIC_SECRET_KEY), SYMMETRIC_SECRET_KEY + " need to be configured");
    private final String initVectorKey = Preconditions.checkNotNull(Constants.getEnvOrProperty(INIT_VECTOR_KEY), INIT_VECTOR_KEY + " need to be configured");

    @Override
    public Class<? extends SimpleSymmetricCipher> type() {
        return null;
    }

    @Override
    public SimpleSymmetricCipher get() {
        SymmetricKeyAESCipher symmetricKeyAESCipher = new SymmetricKeyAESCipher();
        symmetricKeyAESCipher.setKey(this.symmetricSecretKey);
        symmetricKeyAESCipher.setInitVector(this.initVectorKey);
        return symmetricKeyAESCipher;
    }
}
