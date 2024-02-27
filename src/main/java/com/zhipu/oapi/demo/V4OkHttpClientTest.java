package com.zhipu.oapi.demo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.function.Functions;
import com.zhipu.oapi.function.GetWeather;
import com.zhipu.oapi.service.v4.embedding.EmbeddingApiResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.file.FileApiResponse;
import com.zhipu.oapi.service.v4.file.QueryFileApiResponse;
import com.zhipu.oapi.service.v4.file.QueryFilesRequest;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.service.v4.image.CreateImageRequest;
import com.zhipu.oapi.service.v4.image.ImageApiResponse;
import com.zhipu.oapi.service.v4.model.*;
import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class V4OkHttpClientTest {

    private static final String API_KEY = "";

    private static final String API_SECRET = "";

    private static final ClientV4 client = new ClientV4.Builder(API_KEY,API_SECRET).build();

    private static final ObjectMapper mapper = defaultObjectMapper();


    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.addMixIn(ChatFunction.class, ChatFunctionMixIn.class);
        mapper.addMixIn(ChatCompletionRequest.class, ChatCompletionRequestMixIn.class);
        mapper.addMixIn(ChatFunctionCall.class, ChatFunctionCallMixIn.class);
        return mapper;
    }

    // 请自定义自己的业务id
    private static final String requestIdTemplate = "mycompany-%d";

    public static void main(String[] args) throws Exception {
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
        // 1. sse-invoke调用模型，使用标准Listener，直接返回结果
        //testSseInvoke();

        // 2. invoke调用模型,直接返回结果
        //testInvoke();

        // 3. 异步调用
//         String taskId = testAsyncInvoke();
        // 4.异步查询
//         testQueryResult(taskId);

        // 5.文生图
//          testCreateImage();

        // 6. 图生文
//          testImageToWord();

        // 7. 向量模型
//          testEmbeddings();

        // 8.微调-上传微调数据集
//          testUploadFile();

        // 9.微调-查询上传文件列表
//          testQueryUploadFileList();

        // 10.微调-创建微调任务
        //  testCreateFineTuningJob();

        // 11.微调-查询微调任务事件
//          testQueryFineTuningJobsEvents();

        // 12.微调-查询微调任务
//        testRetrieveFineTuningJobs();

        // 13.微调-查询个人微调任务
        //  testQueryPersonalFineTuningJobs();

        // 14.微调-调用微调模型（参考模型调用接口，并替换成要调用模型的编码model）


        // 15. 同步且工具调用模型
        testFunctionInvoke();
    }

    private static void testQueryPersonalFineTuningJobs() {
        QueryPersonalFineTuningJobRequest queryPersonalFineTuningJobRequest = new QueryPersonalFineTuningJobRequest();
        queryPersonalFineTuningJobRequest.setLimit(1);
        QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobApiResponse = client.queryPersonalFineTuningJobs(queryPersonalFineTuningJobRequest);
        System.out.println("model output:" + JSON.toJSONString(queryPersonalFineTuningJobApiResponse));

    }

    private static void testQueryFineTuningJobsEvents() {
        QueryFineTuningJobRequest queryFineTuningJobRequest = new QueryFineTuningJobRequest();
        queryFineTuningJobRequest.setJobId("ftjob-20240119114544390-zkgjb");
//        queryFineTuningJobRequest.setLimit(1);
//        queryFineTuningJobRequest.setAfter("1");
        QueryFineTuningEventApiResponse queryFineTuningEventApiResponse = client.queryFineTuningJobsEvents(queryFineTuningJobRequest);
        System.out.println("model output:" + JSON.toJSONString(queryFineTuningEventApiResponse));
    }

    /**
     * 查询微调任务
     */
    private static void testRetrieveFineTuningJobs() {
        QueryFineTuningJobRequest queryFineTuningJobRequest = new QueryFineTuningJobRequest();
        queryFineTuningJobRequest.setJobId("ftjob-20240119114544390-zkgjb");
//        queryFineTuningJobRequest.setLimit(1);
//        queryFineTuningJobRequest.setAfter("1");
        QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = client.retrieveFineTuningJobs(queryFineTuningJobRequest);
        System.out.println("model output:" + JSON.toJSONString(queryFineTuningJobApiResponse));
    }

    /**
     * 创建微调任务
     */
    private static void testCreateFineTuningJob() {
        FineTuningJobRequest request = new FineTuningJobRequest();
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        request.setRequestId(requestId);
        request.setModel("chatglm3-6b");
        request.setTraining_file("file-20240118082608327-kp8qr");
        CreateFineTuningJobApiResponse createFineTuningJobApiResponse = client.createFineTuningJob(request);
        System.out.println("model output:" + JSON.toJSONString(createFineTuningJobApiResponse));
    }

    /**
     * 微调文件上传列表查询
     */
    private static void testQueryUploadFileList() {
        QueryFilesRequest queryFilesRequest = new QueryFilesRequest();
        QueryFileApiResponse queryFileApiResponse = client.queryFilesApi(queryFilesRequest);
        System.out.println("model output:" + JSON.toJSONString(queryFileApiResponse));
    }


    /**
     * 微调上传数据集
     */
    private static void testUploadFile() {
        String filePath = "/Users/wujianguo/Downloads/transaction-data.jsonl";
        String purpose = "fine-tune";
        FileApiResponse fileApiResponse = client.invokeUploadFileApi(purpose, filePath);
        System.out.println("model output:" + JSON.toJSONString(fileApiResponse));
    }

    private static void testEmbeddings() {
        EmbeddingRequest embeddingRequest = new EmbeddingRequest();
        embeddingRequest.setInput("hello world");
        embeddingRequest.setModel(Constants.ModelEmbedding2);
        EmbeddingApiResponse apiResponse = client.invokeEmbeddingsApi(embeddingRequest);
        System.out.println("model output:" + JSON.toJSONString(apiResponse));
    }

    /**
     * 图生文
     */
    private static void testImageToWord() {
        List<ChatMessage> messages = new ArrayList<>();
        List<Map<String, Object>> contentList = new ArrayList<>();
        Map<String, Object> textMap = new HashMap<>();
        textMap.put("type", "text");
        textMap.put("text", "图里有什么");
        Map<String, Object> typeMap = new HashMap<>();
        typeMap.put("type", "image_url");
        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("url", "https://cdn.bigmodel.cn/enterpriseAc/3f328152-e15c-420c-803d-6684a9f551df.jpeg?attname=24.jpeg");
        typeMap.put("image_url", urlMap);
        contentList.add(textMap);
        contentList.add(typeMap);
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), contentList);
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());


        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4V)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .requestId(requestId)
                .build();
        ModelApiResponse modelApiResponse = client.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:" + JSON.toJSONString(modelApiResponse));

    }

    private static void testCreateImage() {
        CreateImageRequest createImageRequest = new CreateImageRequest();
        createImageRequest.setModel(Constants.ModelCogView);
//        createImageRequest.setPrompt("画一个温顺可爱的小狗");
        ImageApiResponse imageApiResponse = client.createImage(createImageRequest);
        System.out.println("imageApiResponse:" + JSON.toJSONString(imageApiResponse));
    }


    /**
     * sse调用
     */
    private static void testSseInvoke() {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGLM和你哪个更强大");
//         ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "你能帮我查询2024年1月1日从北京南站到上海的火车票吗？");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        // 函数调用参数构建部分
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String, Object> properties = new HashMap<>();
        properties.put("departure", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "出发城市或车站");
        }});
        properties.put("destination", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "目的地城市或车站");
        }});
        properties.put("date", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "要查询的车次日期");
        }});
        List<String> required = new ArrayList<>();
        required.add("departure");
        required.add("destination");
        required.add("date");
        chatFunctionParameters.setProperties(properties);
        ChatFunction chatFunction = ChatFunction.builder()
                .name("query_train_info")
                .description("根据用户提供的信息，查询对应的车次")
                .parameters(chatFunctionParameters)
                .required(required)
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
        if (sseModelApiResp.isSuccess()) {
            AtomicBoolean isFirst = new AtomicBoolean(true);
            ChatMessageAccumulator chatMessageAccumulator = mapStreamToAccumulator(sseModelApiResp.getFlowable())
                    .doOnNext(accumulator -> {
                        {
                            if (isFirst.getAndSet(false)) {
                                System.out.print("Response: ");
                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getTool_calls() != null) {
                                String jsonString = mapper.writeValueAsString(accumulator.getDelta().getTool_calls());
                                System.out.println("tool_calls: " + jsonString);
                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getContent() != null) {
                                System.out.print(accumulator.getDelta().getContent());
                            }
                        }
                    })
                    .doOnComplete(System.out::println)
                    .lastElement()
                    .blockingGet();

            Choice choice = new Choice(chatMessageAccumulator.getChoice().getFinishReason(), 0L, chatMessageAccumulator.getDelta());
            List<Choice> choices = new ArrayList<>();
            choices.add(choice);
            ModelData data = new ModelData();
            data.setChoices(choices);
            data.setUsage(chatMessageAccumulator.getUsage());
            data.setId(chatMessageAccumulator.getId());
            data.setCreated(chatMessageAccumulator.getCreated());
            data.setRequestId(chatCompletionRequest.getRequestId());
            sseModelApiResp.setFlowable(null);
            sseModelApiResp.setData(data);
        }
        System.out.println("model output:" + JSON.toJSONString(sseModelApiResp));
    }

    public static Flowable<ChatMessageAccumulator> mapStreamToAccumulator(Flowable<ModelData> flowable) {
        return flowable.map(chunk -> {
            return new ChatMessageAccumulator(chunk.getChoices().get(0).getDelta(), null, chunk.getChoices().get(0), chunk.getUsage(), chunk.getCreated(), chunk.getId());
        });
    }

    /**
     * 同步调用
     */
    private static void testInvoke() {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGLM和你哪个更强大");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        // 函数调用参数构建部分
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String, Object> properties = new HashMap<>();
        properties.put("location", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "城市，如：北京");
        }});
        properties.put("unit", new HashMap<String, Object>() {{
            put("type", "string");
            put("enum", new ArrayList<String>() {{
                add("celsius");
                add("fahrenheit");
            }});
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
        try {
            System.out.println("model output:" + mapper.writeValueAsString(invokeModelApiResp));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 异步调用
     */
    private static String testAsyncInvoke() {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatLM和你哪个更强大");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        // 函数调用参数构建部分
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String, Object> properties = new HashMap<>();
        properties.put("location", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "城市，如：北京");
        }});
        properties.put("unit", new HashMap<String, Object>() {{
            put("type", "string");
            put("enum", new ArrayList<String>() {{
                add("celsius");
                add("fahrenheit");
            }});
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
        System.out.println("model output:" + JSON.toJSONString(invokeModelApiResp));
        return invokeModelApiResp.getData().getTaskId();
    }


    /**
     * 查询异步结果
     *
     * @param taskId
     */
    private static void testQueryResult(String taskId) {
        QueryModelResultRequest request = new QueryModelResultRequest();
        request.setTaskId(taskId);
        QueryModelResultResponse queryResultResp = client.queryModelResult(request);
        try {
            System.out.println("model output:" + mapper.writeValueAsString(queryResultResp));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * V4-同步function调用
     * function调用流程完整的message list:
     * 1.user message     -杭州今天天气如何，我想跑步
     * 2.assistant message-不知道天气，需要调用，用户编写的get_weather函数去查询天气，入参是“杭州”，tool_call_id=x
     * 3.tool message     -用户自己 拼装的信息 【编写的get_weather函数返回结果（杭州下雨）+tool_call_id=x】
     * 4.结合了function calling的最终回答-杭州今天下雨，不适合跑步
     */
    public static void testFunctionInvoke(){
        // 初始化函数列表，暂时只添加获取天气的函数
        GetWeather getWeather = new GetWeather();
        HashMap<String, Functions> functions = new HashMap<>();
        functions.put("get_weather", getWeather);

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "杭州今天天气如何，我想跑步");
        // 1.user message
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        // 函数调用参数构建部分
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
        // 调用模型
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        ToolCalls toolCalls = invokeModelApiResp.getData().getChoices().get(0).getMessage().getTool_calls().get(0);
        // 2.assistant message(first response)
        // API报content和tool_call_id不能同时为null，所以这里先填一个字符串占位
        messages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(),"空", toolCalls.getFunction().getName(), toolCalls.getId()));
        // 如果触发方法调用
        if (invokeModelApiResp.getData().getChoices().get(0).getFinishReason().equals(Constants.toolCalls)) {
            // 获取函数对象
            ChatFunctionCall chatFunctionCall = toolCalls.getFunction();
            // 获取函数调用结果
            String function_response = functions.get(chatFunctionCall.getName()).invoke(chatFunctionCall.getArguments());
            // 将函数调用结果拼接到first response中
            ChatMessage message = ChatMessage.builder()
                    .role(ChatMessageRole.TOOL.value())
                    .content(function_response)
                    .name(chatFunctionCall.getName())
                    .tool_call_id(toolCalls.getId())
                    .build();
            // 3.tool message
            messages.add(message);
            // 重新调用模型
            chatCompletionRequest.setMessages(messages);
            // 4.结合了function calling的最终回答
            ModelApiResponse modelSecondApiResponse = client.invokeModelApi(chatCompletionRequest);
            try {
                System.out.println("model output:" + mapper.writeValueAsString(modelSecondApiResponse));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}

