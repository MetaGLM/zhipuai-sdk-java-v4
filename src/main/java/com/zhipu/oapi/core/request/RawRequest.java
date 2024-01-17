package com.zhipu.oapi.core.request;

import java.util.*;

import com.zhipu.oapi.core.Config;
import com.zhipu.oapi.utils.StringUtils;

public class RawRequest {
    private int statusCode;
    private String contentType;
    private Map<String, List<String>> headers = new HashMap<>();
    private Map<String, Object> body = new HashMap<>();
    private Map<String, String> queryStr = new HashMap<>();
    private String reqUrl;
    private String httpMethod;
    private Config config;

    private String token;
    private Map<String, String> pathParams = new HashMap<>();


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        List<String> l = new ArrayList();
        l.add(value);
        this.headers.put(key, l);
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public Map<String, String> getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(Map<String, String> queryStr) {
        this.queryStr = queryStr;
    }

    public String getReqUrl() {
        if (pathParams.size() == 0) {
            return reqUrl;
        }
        return StringUtils.formatString(reqUrl, pathParams);
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }

    public void setPathParams(Map<String, String> pathParams) {
        this.pathParams = pathParams;
    }

}
