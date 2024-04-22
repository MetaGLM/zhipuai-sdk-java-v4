package com.zhipu.oapi.core.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.response.RawResponse;

import java.util.Map;

public abstract class BaseHttpTransport implements IHttpTransport{

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected RawResponse extractResp(String resultDataStr) {
        Map<String, Object> resultMap = null;
        try {
            resultMap = objectMapper.readValue(resultDataStr, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return extractResp(resultMap);
    }

    protected RawResponse extractResp(Map<String, Object> resultMap) {
        RawResponse resp = new RawResponse();
        if (!resultMap.containsKey(Constants.resultKeyError)) {
            try {
                resp.setBody(objectMapper.writeValueAsString(resultMap));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            resp.setStatusCode(200);
            resp.setSuccess(true);
        } else {
            Map<String,Object> error = (Map) resultMap.get(Constants.resultKeyError);
            resp.setMsg(error.get("message").toString());
            resp.setStatusCode(Integer.parseInt(error.get("code").toString()));
            resp.setSuccess(false);
        }
        return resp;
    }
}
