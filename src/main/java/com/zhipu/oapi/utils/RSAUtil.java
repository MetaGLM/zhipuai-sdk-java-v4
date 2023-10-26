package com.zhipu.oapi.utils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAUtil {

    /**
     * 公钥加密
     *
     * @param data                  待加密数据
     * @param base64StringPublicKey base64编码后的公钥串
     * @return String
     */
    public static String encrypt(String data, String base64StringPublicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(Base64.decodeBase64(base64StringPublicKey));
            PublicKey publicKey = keyFactory.generatePublic(x509);
            Cipher cipher = Cipher.getInstance("RSA"); //等同于RSA/ECB/PKCS1Padding
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64String(cipher.doFinal(data.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
