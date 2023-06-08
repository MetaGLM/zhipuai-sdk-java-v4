package com.zhipu.oapi.core.httpclient;

import com.google.gson.Gson;
import com.zhipu.oapi.core.request.RawRequest;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.utils.WuDaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ApacheHttpClientTransport extends BaseHttpTransport {

    private static final Logger logger = LoggerFactory.getLogger(ApacheHttpClientTransport.class);

    @Override
    public RawResponse executePost(RawRequest request) throws Exception {
        Map<String, Object> resultMap = WuDaoUtils.executePost(request.getReqUrl(), request.getToken(), request.getBody());
        if (logger.isDebugEnabled()) {
            Gson gson = new Gson();
            String resultStr = gson.toJson(resultMap);
            logger.debug("http transport result: %s", resultStr);
        }
        RawResponse resp = extractResp(resultMap);
        return resp;
    }

    @Override
    public RawResponse executeGet(RawRequest request) throws Exception {
        Map<String, Object> resultMap = WuDaoUtils.executeGet(request.getReqUrl(), request.getToken(), request.getQueryStr());
        if (logger.isDebugEnabled()) {
            Gson gson = new Gson();
            String resultStr = gson.toJson(resultMap);
            logger.debug("http transport result: %s", resultStr);
        }
        return extractResp(resultMap);
    }

    @Override
    public RawResponse sseExecute(RawRequest request) throws Exception {
        // apache httpclient 暂不支持sse
        throw new UnsupportedOperationException("apache http client sse not supported");
    }


}
