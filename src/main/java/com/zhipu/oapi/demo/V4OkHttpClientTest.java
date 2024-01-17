package com.zhipu.oapi.demo;

import com.alibaba.fastjson.JSON;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.httpclient.OkHttpTransport;
import com.zhipu.oapi.service.v4.*;
import okhttp3.OkHttpClient;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;



public class V4OkHttpClientTest {



    // 请先填写自己的apiKey再调用demo查看效果
    public static OkHttpClient getInstance() {
        OkHttpClient okHttpClient=new OkHttpClient.Builder()//构建器
                .proxy(Proxy.NO_PROXY) //来屏蔽系统代理
                .retryOnConnectionFailure(true)
                .connectTimeout(300, TimeUnit.SECONDS)//连接超时
                .writeTimeout(300, TimeUnit.SECONDS)//写入超时
                .readTimeout(300, TimeUnit.SECONDS)//读取超时
                .build();
        okHttpClient.dispatcher().setMaxRequestsPerHost(200); //设置最大并发请求数，避免等待延迟
        okHttpClient.dispatcher().setMaxRequests(200);
        return okHttpClient;
    }

    private static ClientV4 client = new ClientV4.Builder(TestConstants.onlineKeyV3, TestConstants.onlineSecretV3)
        .httpTransport(new OkHttpTransport(getInstance()))
        //.devMode(true)
        .build();

    // 请自定义自己的业务id
    private static final String requestIdTemplate = "mycompany-%d";

    public static void main(String[] args) throws Exception {
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");

        // 1. sse-invoke调用模型，使用标准Listener，直接返回结果
        testSseInvoke();

        // 2. invoke调用模型,直接返回结果
          testInvoke();

        // 3. 异步调用
//         String taskId = testAsyncInvoke();
        // 4.异步查询
         testQueryResult("559916980273155058305988497333548975");

    }


    /**
     * sse调用
     */
     private static void testSseInvoke() {
         List<ChatMessage> messages = new ArrayList<>();
         ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGPT和你哪个更强大");
         messages.add(chatMessage);
         String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
         List<ChatTool> chatToolList = new ArrayList<>();
         ChatTool chatTool = new ChatTool();
         chatTool.setType(ChatToolType.FUNCTION.value());
         ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
         chatFunctionParameters.setType("object");
         Map<String,Object> properties = new HashMap<>();
         properties.put("location",new HashMap<String,Object>(){{
             put("type","string");
             put("description","城市，如：北京");
         }});
         properties.put("unit",new HashMap<String,Object>(){{
             put("type","string");
             put("enum",new ArrayList<String>(){{add("celsius");add("fahrenheit");}});
         }});
         chatFunctionParameters.setProperties(properties);
         ChatFunction chatFunction = ChatFunction.builder()
                 .name("get_weather")
                 .description("Get the current weather of a location")
                 .parameters(chatFunctionParameters)
                 .build();
         chatTool.setFunction(chatFunction);
         chatToolList.add(chatTool);

         ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                 .model(Constants.ModelChatGLM4)
                 .stream(Boolean.TRUE)
                 .messages(messages)
                 .requestId(requestId)
                 .tools(chatToolList)
                 .toolChoice("auto")
                 .build();
         ModelApiResponse sseModelApiResp = client.invokeModelApi(chatCompletionRequest);
         System.out.println("model output:"+ JSON.toJSONString(sseModelApiResp));
     }


    /**
     * 同步调用
     */
    private static void testInvoke() {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGPT和你哪个更强大");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String,Object> properties = new HashMap<>();
        properties.put("location",new HashMap<String,Object>(){{
            put("type","string");
            put("description","城市，如：北京");
        }});
        properties.put("unit",new HashMap<String,Object>(){{
            put("type","string");
            put("enum",new ArrayList<String>(){{add("celsius");add("fahrenheit");}});
        }});
        chatFunctionParameters.setProperties(properties);
        ChatFunction chatFunction = ChatFunction.builder()
                .name("get_weather")
                .description("Get the current weather of a location")
                .parameters(chatFunctionParameters)
                .build();
        chatTool.setFunction(chatFunction);
        chatToolList.add(chatTool);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .requestId(requestId)
                .tools(chatToolList)
                .toolChoice("auto")
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:"+ JSON.toJSONString(invokeModelApiResp));
    }


    /**
     * 异步调用
     *
     */
    private static String testAsyncInvoke() {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGPT和你哪个更强大");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String,Object> properties = new HashMap<>();
        properties.put("location",new HashMap<String,Object>(){{
            put("type","string");
            put("description","城市，如：北京");
        }});
        properties.put("unit",new HashMap<String,Object>(){{
            put("type","string");
            put("enum",new ArrayList<String>(){{add("celsius");add("fahrenheit");}});
        }});
        chatFunctionParameters.setProperties(properties);
        ChatFunction chatFunction = ChatFunction.builder()
                .name("get_weather")
                .description("Get the current weather of a location")
                .parameters(chatFunctionParameters)
                .build();
        chatTool.setFunction(chatFunction);
        chatToolList.add(chatTool);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethodAsync)
                .messages(messages)
                .requestId(requestId)
                .tools(chatToolList)
                .toolChoice("auto")
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:"+ JSON.toJSONString(invokeModelApiResp));
        return invokeModelApiResp.getData().getTaskId();
    }


    /**
     * 查询异步结果
     * @param taskId
     */
    private static void testQueryResult(String taskId) {
        QueryModelResultRequest request = new QueryModelResultRequest();
        request.setTaskId(taskId);
        QueryModelResultResponse queryResultResp = client.queryModelResult(request);
        System.out.println("model output:"+JSON.toJSONString(queryResultResp));
    }
}

