package com.zhipu.oapi.demo;

import com.google.gson.Gson;
import com.zhipu.oapi.ClientV3;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class V3OkHttpClientTest {

    // 请先填写自己的apiKey再调用demo查看效果
    private static ClientV3 client = new ClientV3.Builder(TestConstants.onlineKeyV3, TestConstants.onlineSecretV3)
            //.devMode(true)
            .build();
    // 请自定义自己的业务id
    private static final String requestIdTemplate = "mycompany-%d";

    public static void main(String[] args) throws Exception {

        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");

        // 1. async-invoke调用模型，再调用查询结果接口
        // 1.1 invoke model async
        //String taskId = testAsyncInvoke();
        // 1.2 query model result
        // 根据模型不同，异步调用可能需要等待约30s
        // testQueryResult();

        // 2. sse-invoke调用模型，使用标准Listener，直接返回结果
        // testSseInvoke();

        // 3. parallel sse invoke
        //parallelSseInvoke();

        // 4. sse-invoke english
        testSseEnglishInvoke();
    }

    private static void parallelSseInvoke() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
                    testSseInvoke();
                    latch.countDown();
                }
            });
            t.run();
        }

        latch.await();
        System.out.println("parallel sse invoke finished");
    }

     private static String testAsyncInvoke() {
         ModelApiRequest modelApiRequest = asyncRequest();
         ModelApiResponse modelApiResp = client.invokeModelApi(modelApiRequest);
         System.out.println(String.format("call model api finished, method: %s", modelApiRequest.getInvokeMethod()));
         System.out.println(String.format("invoke api code: %d", modelApiResp.getCode()));
         String taskId = modelApiResp.getData().getTaskId();
         System.out.println(String.format("taskId: %s", taskId));
         return taskId;
     }

     private static void testQueryResult(String taskId) {
         QueryModelResultRequest request = queryRequest(taskId);
         QueryModelResultResponse queryResultResp = client.queryModelResult(request);
         System.out.println(String.format("call query result finished, code: %d", queryResultResp.getCode()));
         System.out.println("model output:");
         System.out.println(queryResultResp.getData().getChoices().get(0).getContent());
     }

    private static void testQueryResult() {
        String taskId = "75931252186628016897638021336447918085";
        QueryModelResultRequest request = queryRequest(taskId);
        QueryModelResultResponse queryResultResp = client.queryModelResult(request);
        System.out.println(String.format("call query result finished, code: %d", queryResultResp.getCode()));
        System.out.println("model output:");
        System.out.println(queryResultResp.getData().getChoices().get(0).getContent());
    }

     private static void testSseInvoke() {
         ModelApiRequest sseModelApiRequest = sseRequest();
         ModelApiResponse sseModelApiResp = client.invokeModelApi(sseModelApiRequest);
         System.out.println(String.format("call model api finished, method: %s", sseModelApiRequest.getInvokeMethod()));
         System.out.println(String.format("invoke api code: %d", sseModelApiResp.getCode()));
         System.out.println("model output:");
         System.out.println(sseModelApiResp.getData().getChoices().get(0).getContent());
     }

    private static void testSseEnglishInvoke() {
        ModelApiRequest sseModelApiRequest = sseEnglishRequest();
        ModelApiResponse sseModelApiResp = client.invokeModelApi(sseModelApiRequest);
        System.out.println(String.format("call model api finished, method: %s", sseModelApiRequest.getInvokeMethod()));
        System.out.println(String.format("invoke api code: %d", sseModelApiResp.getCode()));
        System.out.println("model output:");
        System.out.println(sseModelApiResp.getData().getChoices().get(0).getContent());
        System.out.println("usage:");
        String usage = new Gson().toJson(sseModelApiResp.getData().getUsage(), Usage.class);
        System.out.println(usage);
        System.out.println("task_id: " + sseModelApiResp.getData().getTaskId());
    }

    private static ModelApiRequest asyncRequest() {
        ModelApiRequest modelApiRequest = new ModelApiRequest();
        modelApiRequest.setModelId(Constants.ModelChatGLM6B);
        modelApiRequest.setInvokeMethod(Constants.invokeMethodAsync);
        ModelApiRequest.Prompt prompt = new ModelApiRequest.Prompt(ModelConstants.roleUser, "ChatGPT和你哪个更强大");
        List<ModelApiRequest.Prompt> prompts = new ArrayList<>();
        prompts.add(prompt);
        modelApiRequest.setPrompt(prompts);
        // 自定义业务id，需保证唯一性
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        modelApiRequest.setRequestId(requestId);
        return modelApiRequest;
    }

    private static ModelApiRequest sseEnglishRequest() {
        ModelApiRequest modelApiRequest = new ModelApiRequest();
        modelApiRequest.setModelId(Constants.ModelChatGLM6B);
        modelApiRequest.setInvokeMethod(Constants.invokeMethodSse);

        // 可自定义sse listener
        StandardEventSourceListener listener = new StandardEventSourceListener();
        listener.setIncremental(false);
        modelApiRequest.setSseListener(listener);
        modelApiRequest.setIncremental(false);

        ModelApiRequest.Prompt prompt = new ModelApiRequest.Prompt(ModelConstants.roleUser, "tell me something about C Ronaldo in English");
        List<ModelApiRequest.Prompt> prompts = new ArrayList<>();
        prompts.add(prompt);
        modelApiRequest.setPrompt(prompts);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        modelApiRequest.setRequestId(requestId);
        return modelApiRequest;
    }
    private static QueryModelResultRequest queryRequest(String taskId) {
        QueryModelResultRequest queryModelResultRequest = new QueryModelResultRequest();
        queryModelResultRequest.setTaskId(taskId);
        return queryModelResultRequest;
    }

    private static ModelApiRequest sseRequest() {
        ModelApiRequest modelApiRequest = new ModelApiRequest();
        modelApiRequest.setModelId(Constants.ModelChatGLM6B);
        modelApiRequest.setInvokeMethod(Constants.invokeMethodSse);
        // 可自定义sse listener
        modelApiRequest.setSseListener(new StandardEventSourceListener());
        ModelApiRequest.Prompt prompt = new ModelApiRequest.Prompt(ModelConstants.roleUser, "ChatGPT和你哪个更强大");
        List<ModelApiRequest.Prompt> prompts = new ArrayList<>();
        prompts.add(prompt);
        modelApiRequest.setPrompt(prompts);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        modelApiRequest.setRequestId(requestId);
        return modelApiRequest;
    }
}

