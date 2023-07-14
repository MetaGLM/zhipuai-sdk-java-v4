package com.zhipu.oapi.demo;

import com.google.gson.Gson;
import com.zhipu.oapi.Client;
import com.zhipu.oapi.service.v2.ChatGlm6BReq;
import com.zhipu.oapi.service.v2.ChatGlm6BResp;
import com.zhipu.oapi.service.v2.QueryRequestTaskResultRequest;
import com.zhipu.oapi.service.v2.QueryRequestTaskResultResponse;

import java.util.ArrayList;
import java.util.List;

public class V2OkHttpClientTest {
    public static void main(String[] args) throws Exception {
        //testGlm6();
        testQueryResult();
        //testGlm6AndQueryResult();
    }

    private static void testGlm6() {
        Client.Builder builder = new Client.Builder(TestConstants.API_KEY, TestConstants.PUBLIC_KEY);
        Client client = builder.build();

        // 1. 发起6b模型调用
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
        String taskOrderNo = "75550824942899442967580334428090869692";
        Client.Builder builder = new Client.Builder(TestConstants.API_KEY, TestConstants.PUBLIC_KEY);
        Client client = builder.build();
        QueryRequestTaskResultRequest req = new QueryRequestTaskResultRequest();
        req.setTaskOrderNo(taskOrderNo);
        QueryRequestTaskResultResponse resp = client.queryRequestTaskResult(req);
        System.out.println(new Gson().toJson(resp));
    }

    private static void testGlm6AndQueryResult() {

    }
}
