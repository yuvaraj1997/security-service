package com.yuvaraj.security.services.cipher.symmetric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SymmetricKeyAESCipher implements SimpleSymmetricCipher {

    protected static final String AES = "AES";
    protected static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";

    private String key;
    private String initVector;


    @Override
    public void setKey(String var1) {
        this.key = var1;
    }

    @Override
    public void setInitVector(String var1) {
        this.initVector = var1;
    }

    @Override
    public String encrypt(String var1) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, this.getSecretKeySpec(), this.getIvParameterSpec());
        byte[] encrypted = cipher.doFinal(var1.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private IvParameterSpec getIvParameterSpec() throws UnsupportedEncodingException {
        return new IvParameterSpec(this.initVector.getBytes(StandardCharsets.US_ASCII));
    }

    private SecretKeySpec getSecretKeySpec() {
        return new SecretKeySpec(this.key.getBytes(StandardCharsets.UTF_16), 0, 16, AES);
    }

    @Override
    public String decrypt(String var1) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, this.getSecretKeySpec(), this.getIvParameterSpec());
        byte[] encrypted = cipher.doFinal(Base64.getDecoder().decode(var1));
        return new String(encrypted, StandardCharsets.UTF_8);
    }
}
