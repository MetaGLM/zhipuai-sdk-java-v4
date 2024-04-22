package com.zhipu.oapi.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WuDaoUtils {
	public static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 获取鉴权token
	 * 
	 * @param createTokenUrl 获取token
	 * @param apiKey
	 * @param publicKey
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public static Map<String, Object> getToken(String createTokenUrl, String apiKey, String publicKey)
			throws Exception {
		String timestamp = String.valueOf(System.currentTimeMillis());
		String encrypt = RSAUtil.encrypt(timestamp, publicKey);
		Map<String, String> paramsMap = new HashMap<>(2);
		paramsMap.put("encrypted", encrypt);
		paramsMap.put("apiKey", apiKey);

		String response = HttpUtilClient.sendPostJson(createTokenUrl, objectMapper.writeValueAsString(paramsMap));
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMap = objectMapper.readValue(response, Map.class);
		return resultMap;
	}

	public static void main(String[] args) {
		String timestamp = String.valueOf(System.currentTimeMillis());
		String encrypt = RSAUtil.encrypt(timestamp, "c21lEFUg1MxMKpJp");
		System.out.println(encrypt);
	}

	/**
	 * 引擎请求
	 *
	 * @param engineUrl 引擎请求地址
	 * @param authToken 鉴权token
	 * @param paramsMap
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public static Map<String, Object> executePost(String engineUrl, String authToken, Map<String, Object> paramsMap)
			throws Exception {
		Map<String, String> header = new HashMap<>(1);
		header.put("Authorization", authToken);

		String response = HttpUtilClient.sendPostJson(engineUrl,
				objectMapper.writeValueAsString(paramsMap),
				header);
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMap = objectMapper.readValue(response, Map.class);
		return resultMap;
	}

	public static Map<String, Object> executeGet(String url, String authToken, Map<String, String> paramsMap)
			throws Exception {
		Map<String, String> header = new HashMap<>(1);
		header.put("Authorization", authToken);
		String response = HttpUtilClient.sendGet(url, paramsMap, header);
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMap = objectMapper.readValue(response, Map.class);
		return resultMap;
	}

}
