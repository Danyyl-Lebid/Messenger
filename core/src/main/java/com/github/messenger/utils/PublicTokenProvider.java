package com.github.messenger.utils;

import com.github.messenger.exceptions.BadRequest;
import com.github.messenger.payload.PublicToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PublicTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(PublicTokenProvider.class);

    private static final String SECRET_KEY = "NotTheBestChoice";

    private static final String SALT = "";

//    private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static String encode(PublicToken token) {
        if (token == null) {
            throw new BadRequest("Empty token!");
        }
        String str = JsonHelper.toJson(token).orElseThrow(InternalError::new);
        try {
//            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = getSecretKeySpec();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("Error while encrypting: " + e);
        }
        log.info("Cipher token!");
        return null;
    }

    public static PublicToken decode(String str) {

        PublicToken token = null;

        try {
//            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = getSecretKeySpec();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            token = JsonHelper.fromJson(new String(cipher.doFinal(Base64.getDecoder().decode(str))), PublicToken.class).orElseThrow(BadRequest::new);
        } catch (Exception e) {
            log.error("Error while decrypting: " + e);
        }
        return token;
    }

    private static SecretKeySpec getSecretKeySpec() throws InvalidKeySpecException, NoSuchAlgorithmException {
//        IvParameterSpec ivspec = new IvParameterSpec(iv);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

}
