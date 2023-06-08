package com.zhipu.oapi.core.httpclient;

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
        Double code = (Double) resultMap.get(Constants.resultKeyStatusCode);
        resp.setStatusCode(code.intValue());
        if (code.intValue() == 200) {
            Gson gson = new Gson();
            String data = gson.toJson(resultMap.get("data"));
            resp.setBody(data);
            resp.setSuccess(true);
        } else {
            String msg = (String) resultMap.get(Constants.resultKeyMsg);
            resp.setMsg(msg);
            resp.setSuccess(false);
        }
        return resp;
    }
}
