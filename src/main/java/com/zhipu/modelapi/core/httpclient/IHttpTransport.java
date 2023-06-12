package com.zhipu.modelapi.core.httpclient;


import com.zhipu.modelapi.core.request.RawRequest;
import com.zhipu.modelapi.core.response.RawResponse;

public interface IHttpTransport {

    RawResponse executePost(RawRequest request) throws Exception;

    RawResponse executeGet(RawRequest request) throws Exception;

    RawResponse sseExecute(RawRequest request) throws Exception;
}
