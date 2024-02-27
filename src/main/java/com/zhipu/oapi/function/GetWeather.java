package com.zhipu.oapi.function;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Date;

/**
 * 心知天气API城市级V3调用demo
 * 官方：https://www.seniverse.com/
 * 使用文档：https://seniverse.yuque.com/hyper_data/api_v3/sdnhw8
 * demo地址：https://github.com/seniverse/seniverse-api-demos/blob/master/java/DemoJava.java
 * 选用的原因一是因为是国内的天气API，二是因为可以直接传入“城市名或拼音”进行城市级的API调用（大多数天气免费API需要传入地区编码，是区块级的）
 */
public class GetWeather implements Functions{

    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    //官网注册-》免费版-》私钥，替换成你自己的
    private String TIANQI_API_SECRET_KEY = "";
    //官网注册-》免费版-》公钥，替换成你自己的
    private String TIANQI_API_USER_ID = "";


    /**
     * Generate HmacSHA1 signature with given data string and key
     *
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new sun.misc.BASE64Encoder().encode(rawHmac);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    /**
     * Generate the URL to get diary weather
     *
     * @param location
     * @param language
     * @param unit
     * @param start
     * @param days
     * @return
     */
    public String generateGetDiaryWeatherURL(
            String location,
            String language,
            String unit,
            String start,
            String days
    ) throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=1800&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
    }


    public static void main(String args[]) {
        GetWeather demo = new GetWeather();
        try {
            String url = demo.generateGetDiaryWeatherURL(
                    "shanghai",
                    "zh-Hans",
                    "c",
                    "1",
                    "1"
            );
            System.out.println("URL:" + url);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }

    @Override
    public String invoke(JsonNode arguments) {
        //{"function":{"name":"get_weather","arguments":"{\r\n  \"location\" : \"杭州\",\r\n  \"unit\" : \"celsius\"\r\n}"}
        GetWeather demo = new GetWeather();

        try {
            //String url = demo.generateGetDiaryWeatherURL(
            //        "shanghai",
            //        "zh-Hans",
            //        "c",
            //        "1",
            //        "1"
            //);
            String url = demo.generateGetDiaryWeatherURL(
                    arguments.get("location").asText(),
                    "zh-Hans",
                    arguments.get("unit").asText().substring(0,1),
                    "1",
                    "1"
            );
            String s = HttpUtil.get(url);
            System.out.println("URL:" + url);
            return s;
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return null;
    }
}

