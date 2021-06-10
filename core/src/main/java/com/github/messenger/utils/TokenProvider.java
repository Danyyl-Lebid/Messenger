package com.github.messenger.utils;

import com.github.messenger.exceptions.BadRequest;
import com.github.messenger.payload.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;

public class TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String SECRET_KEY = "SpacersChoice";

    private static final String SALT = "ServletChat";

    private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    public static String encode(Token token) {
        if (token == null) {
            throw new BadRequest("Empty token!");
        }
        String str = JsonHelper.toJson(token).orElseThrow(InternalError::new);
        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = getSecretKeySpec();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("Error while encrypting: " + e);
        }
        log.info("Cipher token!");
        return null;
    }

    public static Token decode(String str) {
        Token newT = null;

        try {
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = getSecretKeySpec();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            newT = JsonHelper.fromJson(new String(cipher.doFinal(Base64.getDecoder().decode(str))), Token.class).orElseThrow(BadRequest::new);
        } catch (Exception e) {
            log.error("Error while decrypting: " + e);
        }
        return newT;
    }

    private static SecretKeySpec getSecretKeySpec() throws InvalidKeySpecException, NoSuchAlgorithmException {
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    public static boolean validateToken(Token token) {
        if (token == null) {
            return false;
        }
        return token.getExpireIn().compareTo(new Date()) >= 0;
    }

}
