package com.zhipu.oapi.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtilClient {

	private static final Integer TIME_OUT = 300000;

	public static String sendGet(String url, Map<String, String> params, Map<String, String> header) throws Exception {
		HttpGet httpGet = null;
		String body = "";
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig()).build();
			List<String> mapList = new ArrayList<>();
			if (params != null) {
				for (Entry<String, String> entry : params.entrySet()) {
					mapList.add(entry.getKey() + "=" + entry.getValue());
				}
			}
			if (!mapList.isEmpty()) {
				url = url + "?";
				String paramsStr = String.join("&", mapList);
				url = url + paramsStr;
			}

			httpGet = new HttpGet(url);
			httpGet.setHeader("Content-type", "application/json; charset=utf-8");
			httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			if (header != null) {
				for (Entry<String, String> entry : header.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResponse response = httpClient.execute(httpGet);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("请求失败");
			} else {
				body = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
		}
		return body;
	}

	public static String sendPostJson(String url, String json) throws Exception {

		return sendPostJson(url, json, null);
	}

	public static String sendPostJson(String url, String json, Map<String, String> header) throws Exception {
		HttpPost httpPost = null;
		String body = "";
		try {
			CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(getRequestConfig()).build();
			httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "application/json; charset=utf-8");
			httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			if (header != null) {
				for (Entry<String, String> entry : header.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			StringEntity entity = new StringEntity(json, Charset.forName("UTF-8"));
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("请求失败");
			} else {
				body = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return body;
	}

	public static String sendPostForm(String url, Map<String, String> params) throws Exception {

		return sendPostForm(url, params, null);
	}

	public static String sendPostForm(String url, Map<String, String> params, Map<String, String> header)
			throws Exception {
		HttpPost httpPost = null;
		String body = "";
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
			httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			if (header != null) {
				for (Entry<String, String> entry : header.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			List<NameValuePair> nameValuePairs = new ArrayList<>();
			if (params != null) {
				for (Entry<String, String> entry : params.entrySet()) {
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			// 设置参数到请求对象中
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("请求失败");
			} else {
				body = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return body;
	}

	private static RequestConfig getRequestConfig() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT).build();
		return requestConfig;
	}
}
