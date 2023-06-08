package com.zhipu.oapi.demo;

import com.google.gson.Gson;
import com.zhipu.oapi.Client;
import com.zhipu.oapi.core.httpclient.ApacheHttpClientTransport;
import com.zhipu.oapi.service.v2.ChatGlm6BReq;
import com.zhipu.oapi.service.v2.ChatGlm6BResp;
import com.zhipu.oapi.service.v2.QueryRequestTaskResultRequest;
import com.zhipu.oapi.service.v2.QueryRequestTaskResultResponse;

import java.util.ArrayList;
import java.util.List;

public class V2ApacheClientTest {

    public static void main(String[] args) throws Exception {
        //testGlm6();
        testQueryResult();
        //testGlm6AndQueryResult();
    }

    private static void testGlm6() {
        Client.Builder builder = new Client.Builder(TestConstants.API_KEY, TestConstants.PUBLIC_KEY)
                .httpTransport(new ApacheHttpClientTransport());
        Client client = builder.build();
        ChatGlm6BReq req = new ChatGlm6BReq();
        req.setPrompt("你都可以做些什么事");
        List<ChatGlm6BReq.QA> history = new ArrayList<ChatGlm6BReq.QA>();
        history.add(new ChatGlm6BReq.QA("你好", "我是人工智能助手"));
        history.add(new ChatGlm6BReq.QA("你叫什么名字", "我叫chatGLM"));
        req.setHistory(history);
        ChatGlm6BResp resp = client.chatGlm6B(req);
        System.out.println(new Gson().toJson(resp));
    }

    private static void testQueryResult() {

    }

    private static void testGlm6AndQueryResult() {
        Client.Builder builder = new Client.Builder(TestConstants.API_KEY, TestConstants.PUBLIC_KEY)
                .httpTransport(new ApacheHttpClientTransport());
        Client client = builder.build();
        ChatGlm6BReq req = new ChatGlm6BReq();
        req.setPrompt("你都可以做些什么事");
        List<ChatGlm6BReq.QA> history = new ArrayList<ChatGlm6BReq.QA>();
        history.add(new ChatGlm6BReq.QA("你好", "我是人工智能助手"));
        history.add(new ChatGlm6BReq.QA("你叫什么名字", "我叫chatGLM"));
        req.setHistory(history);
        ChatGlm6BResp resp = client.chatGlm6B(req);
        System.out.println(new Gson().toJson(resp));

        String taskOrderNo = resp.getData().getTaskOrderNo();
        QueryRequestTaskResultRequest req2 = new QueryRequestTaskResultRequest();
        req2.setTaskOrderNo(taskOrderNo);
        QueryRequestTaskResultResponse resp2 = client.queryRequestTaskResult(req2);
        System.out.println(new Gson().toJson(resp2));
    }
}
