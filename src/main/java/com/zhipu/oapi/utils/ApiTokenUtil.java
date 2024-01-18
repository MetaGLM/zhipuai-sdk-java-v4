package com.zhipu.oapi.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ApiTokenUtil {

    public static void main(String[] args) {
        System.out.println(generateClientToken("120dd8b9ce50a9dd7b0583909dd49822.N3VMdpTH7RqkHIFT"));
    }

    public static String generateClientToken(String apikey) {
        String[] apiKeyParts = apikey.split("\\.");
        String api_key = apiKeyParts[0];
        String secret = apiKeyParts[1];

        Map<String, Object> header = new HashMap<>();
        header.put("alg", SignatureAlgorithm.HS256);
        header.put("sign_type", "SIGN");
        Map<String, Object> payload = new HashMap<>();
        payload.put("api_key", api_key);
        payload.put("exp", System.currentTimeMillis() + 5 * 600 * 1000);
        payload.put("timestamp", System.currentTimeMillis());
        String token = null;
        try {
            token = Jwts.builder().setHeader(header)
                    .setPayload(JSON.toJSONString(payload))
                    .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                    .compact();
        } catch (Exception e) {
            System.out.println();
        }

        return token;
    }



    public static String generateClientPartnerToken(String partnerkey) {
        String[] partnerKeyParts = partnerkey.split("\\.");
        String partner_key = partnerKeyParts[0];
        String secret = partnerKeyParts[1];

        Map<String, Object> header = new HashMap<>();
        header.put("alg", SignatureAlgorithm.HS256);
        header.put("sign_type", "SIGN");
        Map<String, Object> payload = new HashMap<>();
        payload.put("partner_key", partner_key);
        payload.put("exp", System.currentTimeMillis() + 5 * 600 * 1000);
        payload.put("timestamp", System.currentTimeMillis());
        String token = null;
        try {
            token = Jwts.builder().setHeader(header)
                    .setPayload(JSON.toJSONString(payload))
                    .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                    .compact();
        } catch (Exception e) {
            System.out.println();
        }
        return token;
    }


}
