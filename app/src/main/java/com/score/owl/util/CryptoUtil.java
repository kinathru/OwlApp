package com.score.owl.util;

import android.content.Context;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {

    // key types
    private static final String PUBLIC_KEY = "PUBLIC_KEY";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";
    private static final String SESSION_KEY = "SESSION_KEY";

    // size of RSA keys
    private static final int RSA_KEY_SIZE = 1024;
    private static final int SESSION_KEY_SIZE = 128;

    public static void initRSAKeyPair(Context context) throws NoSuchProviderException, NoSuchAlgorithmException {
        // generate keypair
        KeyPairGenerator keyPairGenerator;
        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(RSA_KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // get public key from keypair
        // save public key in shared preference
        byte[] pubKeyEncoded = keyPair.getPublic().getEncoded();
        String pubKeyStream = Base64.encodeToString(pubKeyEncoded, Base64.DEFAULT).replaceAll("\n", "").replaceAll("\r", "");
        PreferenceUtil.saveKey(context, pubKeyStream, CryptoUtil.PUBLIC_KEY);

        // get private key from keypair
        // save private key in shared preference
        byte[] priKeyEncoded = keyPair.getPrivate().getEncoded();
        String priKeyStream = Base64.encodeToString(priKeyEncoded, Base64.DEFAULT).replaceAll("\n", "").replaceAll("\r", "");
        PreferenceUtil.saveKey(context, priKeyStream, CryptoUtil.PRIVATE_KEY);
    }

    private static PublicKey getPublicKey(Context context) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        // get key string from shared preference
        String keyString = PreferenceUtil.getKey(context, CryptoUtil.PUBLIC_KEY);

        // convert to string key public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(keyString, Base64.DEFAULT));
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePublic(spec);
    }

    private static PrivateKey getPrivateKey(Context context) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // get key string from shared preference
        String keyString = PreferenceUtil.getKey(context, CryptoUtil.PRIVATE_KEY);

        // convert to string key public key
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(keyString, Base64.DEFAULT));
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePrivate(spec);
    }

    public static String encryptRSA(Context context, String payload) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        // load public key
        PublicKey publicKey = CryptoUtil.getPublicKey(context);

        // encrypt payload
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = cipher.doFinal(payload.getBytes());
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static String decryptRSA(Context context, String payload) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        // load private key
        PrivateKey privateKey = CryptoUtil.getPrivateKey(context);

        // todo decrypt
        // todo decode base64 and return
        return payload;
    }

    public static void initAESSecretKey(Context context) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(SESSION_KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();

        // save session key in share preference
        String keyStream = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT).replaceAll("\n", "").replaceAll("\r", "");
        PreferenceUtil.saveKey(context, keyStream, SESSION_KEY);
    }

    private static SecretKey getAESSecretKey(Context context) {
        // get key string from shared preference
        String keyStream = PreferenceUtil.getKey(context, CryptoUtil.SESSION_KEY);

        byte[] key = Base64.decode(keyStream, Base64.DEFAULT);
        return new SecretKeySpec(key, 0, key.length, "AES");
    }

    public static String encryptAES(Context context, String payload) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        // load secret key
        SecretKey secretKey = getAESSecretKey(context);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] data = cipher.doFinal(payload.getBytes());
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static String decryptAES(Context context, String payload) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        // load secret key
        SecretKey secretKey = getAESSecretKey(context);

        // todo decrypt
        // todo decode base64 and return
        return payload;
    }

    public static String sign(Context context, String payload) throws SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        // load private key
        PrivateKey privateKey = CryptoUtil.getPrivateKey(context);

        // todo sign payload and return signature
        return "digsig";
    }

    public static boolean verifySign(Context context, String payload, String signedPayload) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        // load public key
        PublicKey publicKey = CryptoUtil.getPublicKey(context);

        // todo verify signature and return validity
        return true;
    }

    public static String hashSHA256(String in) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(in.getBytes());
        return Base64.encodeToString(hash, Base64.DEFAULT).replaceAll("\n", "").replaceAll("\r", "");
    }
}
