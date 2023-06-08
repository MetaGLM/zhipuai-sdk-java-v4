package com.zhipu.oapi.core.httpclient;


import com.zhipu.oapi.core.request.RawRequest;
import com.zhipu.oapi.core.response.RawResponse;

public interface IHttpTransport {

    RawResponse executePost(RawRequest request) throws Exception;

    RawResponse executeGet(RawRequest request) throws Exception;

    RawResponse sseExecute(RawRequest request) throws Exception;
}
