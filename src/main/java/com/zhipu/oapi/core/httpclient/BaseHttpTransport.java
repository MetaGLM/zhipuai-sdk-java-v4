package com.zhipu.oapi.core.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.response.RawResponse;

import java.util.Map;

public abstract class BaseHttpTransport implements IHttpTransport{

    protected RawResponse extractResp(String resultDataStr) {
        Gson gson = new Gson();
        Map<String, Object> resultMap = gson.fromJson(resultDataStr, Map.class);
        return extractResp(resultMap);
    }

    protected RawResponse extractResp(Map<String, Object> resultMap) {
        RawResponse resp = new RawResponse();
        if (!resultMap.containsKey(Constants.resultKeyError)) {
            resp.setBody(JSON.toJSONString(resultMap));
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
