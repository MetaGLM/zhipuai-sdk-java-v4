package com.zhipu.oapi;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zhipu.oapi.service.v4.api.ChatApiService;
import com.zhipu.oapi.service.v4.embedding.EmbeddingApiResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.file.FileApiResponse;
import com.zhipu.oapi.service.v4.file.UploadFileRequest;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.service.v4.image.CreateImageRequest;
import com.zhipu.oapi.service.v4.image.ImageApiResponse;
import com.zhipu.oapi.service.v4.model.*;
import io.reactivex.Flowable;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class V4Test {

    private final static Logger logger = LoggerFactory.getLogger(V4Test.class);
    private static final String API_SECRET_KEY = ".";
    private static final boolean devMode = false;


    private static final ClientV4 client = new ClientV4.Builder(API_SECRET_KEY)
            .devMode(devMode)
            .enableTokenCache()
            .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
            .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
            .build();

    // 请自定义自己的业务id
    private static final String requestIdTemplate = "mycompany-%d";

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

    /**
     * sse-V4：function调用
     */
    @Test
    public void testFunctionSSE() throws JsonProcessingException {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "成都到北京要多久，天气如何");
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
        HashMap<String, Object> extraJson = new HashMap<>();
        extraJson.put("temperature", 0.5);
        extraJson.put("max_tokens", 50);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.TRUE)
                .messages(messages)
                .requestId(requestId)
                .tools(chatToolList)
                .toolChoice("auto")
                .extraJson(extraJson)
                .build();
        ModelApiResponse sseModelApiResp = client.invokeModelApi(chatCompletionRequest);
        if (sseModelApiResp.isSuccess()) {
            AtomicBoolean isFirst = new AtomicBoolean(true);
            List<Choice> choices = new ArrayList<>();
            ChatMessageAccumulator chatMessageAccumulator = mapStreamToAccumulator(sseModelApiResp.getFlowable())
                    .doOnNext(accumulator -> {
                        {
                            if (isFirst.getAndSet(false)) {
                                logger.info("Response: ");
                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getTool_calls() != null) {
                                String jsonString = mapper.writeValueAsString(accumulator.getDelta().getTool_calls());
                                logger.info("tool_calls: {}" , jsonString);
                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getContent() != null) {
                                logger.info(accumulator.getDelta().getContent());
                            }
                            choices.add(accumulator.getChoice());
                        }
                    })
                    .doOnComplete(System.out::println)
                    .lastElement()
                    .blockingGet();


            ModelData data = new ModelData();
            data.setChoices(choices);
            data.setUsage(chatMessageAccumulator.getUsage());
            data.setId(chatMessageAccumulator.getId());
            data.setCreated(chatMessageAccumulator.getCreated());
            data.setRequestId(chatCompletionRequest.getRequestId());
            sseModelApiResp.setFlowable(null);// 打印前置空
            sseModelApiResp.setData(data);
        }
        logger.info("model output: {}" , mapper.writeValueAsString(sseModelApiResp));
    }


    /**
     * sse-V4：非function调用
     */
    @Test
    public void testNonFunctionSSE() throws JsonProcessingException {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGLM和你哪个更强大");
        messages.add(chatMessage);
        HashMap<String, Object> extraJson = new HashMap<>();
        extraJson.put("temperature", 0.5);
        extraJson.put("max_tokens", 3);

        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.TRUE)
                .messages(messages)
                .requestId(requestId)
                .extraJson(extraJson)
                .build();
        ModelApiResponse sseModelApiResp = client.invokeModelApi(chatCompletionRequest);
        // stream 处理方法
        if (sseModelApiResp.isSuccess()) {
            AtomicBoolean isFirst = new AtomicBoolean(true);
            List<Choice> choices = new ArrayList<>();
            ChatMessageAccumulator chatMessageAccumulator = mapStreamToAccumulator(sseModelApiResp.getFlowable())
                    .doOnNext(accumulator -> {
                        {
                            if (isFirst.getAndSet(false)) {
                                logger.info("Response: ");
                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getTool_calls() != null) {
                                String jsonString = mapper.writeValueAsString(accumulator.getDelta().getTool_calls());
                                logger.info("tool_calls: {}", jsonString);
                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getContent() != null) {
                                logger.info("accumulator.getDelta().getContent(): {}", accumulator.getDelta().getContent());
                            }
                            choices.add(accumulator.getChoice());
                        }
                    })
                    .doOnComplete(System.out::println)
                    .lastElement()
                    .blockingGet();


            ModelData data = new ModelData();
            data.setChoices(choices);
            data.setUsage(chatMessageAccumulator.getUsage());
            data.setId(chatMessageAccumulator.getId());
            data.setCreated(chatMessageAccumulator.getCreated());
            data.setRequestId(chatCompletionRequest.getRequestId());
            sseModelApiResp.setFlowable(null);// 打印前置空
            sseModelApiResp.setData(data);
        }
        logger.info("model output: {}", mapper.writeValueAsString(sseModelApiResp));
    }


    /**
     * V4-同步function调用
     */
    @Test
    public void testFunctionInvoke(){
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "北京天气如何");
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


        ChatTool chatTool1 = new ChatTool();
        chatTool1.setType(ChatToolType.WEB_SEARCH.value());
        WebSearch webSearch = new WebSearch();
        webSearch.setSearch_query("清华的升学率");
        webSearch.setSearch_result(true);
        webSearch.setEnable(false);
        chatTool1.setWeb_search(webSearch);

        chatToolList.add(chatTool);
        chatToolList.add(chatTool1);

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
            logger.info("model output: {}" , mapper.writeValueAsString(invokeModelApiResp));
        } catch (JsonProcessingException e) {
            logger.error("model output error", e); 
        }
    }



    /**
     * V4-同步非function调用
     */
    @Test
    public void testNonFunctionInvoke() throws JsonProcessingException {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGLM和你哪个更强大");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());


        HashMap<String, Object> extraJson = new HashMap<>();
        extraJson.put("temperature", 0.5);
        extraJson.put("max_tokens", 3);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .requestId(requestId)
                .extraJson(extraJson)
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        logger.info("model output: {}", mapper.writeValueAsString(invokeModelApiResp));
    }

    /**
     * V4异步调用
     */
    @Test
    public void testAsyncInvoke() throws JsonProcessingException {
        String taskId = getAsyncTaskId();
        testQueryResult(taskId);
    }


    /**
     * 文生图
     */
    @Test
    public void testCreateImage() throws JsonProcessingException {
        CreateImageRequest createImageRequest = new CreateImageRequest();
        createImageRequest.setModel(Constants.ModelCogView);
        createImageRequest.setPrompt("画一个温顺可爱的小狗");
        ImageApiResponse imageApiResponse = client.createImage(createImageRequest);
        logger.info("imageApiResponse: {}", mapper.writeValueAsString(imageApiResponse));
    }


    /**
     * 图生文
     */
    @Test
    public void testImageToWord() throws JsonProcessingException {
        List<ChatMessage> messages = new ArrayList<>();
        List<Map<String,Object>> contentList = new ArrayList<>();
        Map<String,Object> textMap = new HashMap<>();
        textMap.put("type","text");
        textMap.put("text","图里有什么");
        Map<String,Object> typeMap = new HashMap<>();
        typeMap.put("type","image_url");
        Map<String,Object> urlMap = new HashMap<>();
        urlMap.put("url","https://sfile.chatglm.cn/testpath/275ae5b6-5390-51ca-a81a-60332d1a7cac_0.png");
        typeMap.put("image_url",urlMap);
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
        logger.info("model output: {}", mapper.writeValueAsString(modelApiResponse));
    }


    /**
     * 向量模型V4
     */
    @Test
    public void testEmbeddings() throws JsonProcessingException {
        EmbeddingRequest embeddingRequest = new EmbeddingRequest();
        embeddingRequest.setInput("hello world");
        embeddingRequest.setModel(Constants.ModelEmbedding2);
        EmbeddingApiResponse apiResponse = client.invokeEmbeddingsApi(embeddingRequest);
        logger.info("model output: {}", mapper.writeValueAsString(apiResponse));
    }


    /**
     * V4微调上传数据集
     */
    @Test
    public void testUploadFile() throws JsonProcessingException {
        String filePath = "demo.jsonl";

        String path = ClassLoader.getSystemResource(filePath).getPath();
        String purpose = "fine-tune";
        UploadFileRequest request = UploadFileRequest.builder()
                .purpose(purpose)
                .filePath(path)
                .build();

        FileApiResponse fileApiResponse = client.invokeUploadFileApi(request);
        logger.info("model output: {}", mapper.writeValueAsString(fileApiResponse));
    }


    /**
     * 微调V4-查询上传文件列表
     */
    @Test
    public void testQueryUploadFileList() throws JsonProcessingException {
//        String filePath = "/Users/wujianguo/Downloads/transaction-data.jsonl";
//        String purpose = "fine-tune";
//        FileApiResponse fileApiResponse = client.invokeUploadFileApi(purpose,filePath);
//        logger.info("model output:"+mapper.writeValueAsString(fileApiResponse));
    }


    /**
     * 微调V4-创建微调任务
     */
    @Test
    public void testCreateFineTuningJob() throws JsonProcessingException {
        FineTuningJobRequest request = new FineTuningJobRequest();
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        request.setRequestId(requestId);
        request.setModel("chatglm3-6b");
        request.setTraining_file("file-20240118082608327-kp8qr");
        CreateFineTuningJobApiResponse createFineTuningJobApiResponse = client.createFineTuningJob(request);
        logger.info("model output: {}", mapper.writeValueAsString(createFineTuningJobApiResponse));
    }


    /**
     * 微调V4-查询微调任务
     */
    @Test
    public void testRetrieveFineTuningJobs() throws JsonProcessingException {
        QueryFineTuningJobRequest queryFineTuningJobRequest = new QueryFineTuningJobRequest();
        queryFineTuningJobRequest.setJobId("ftjob-20240119114544390-zkgjb");
//        queryFineTuningJobRequest.setLimit(1);
//        queryFineTuningJobRequest.setAfter(1);
        QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = client.retrieveFineTuningJobs(queryFineTuningJobRequest);
        logger.info("model output: {}", mapper.writeValueAsString(queryFineTuningJobApiResponse));
    }


    /**
     * testQueryPersonalFineTuningJobs V4-查询个人微调任务
     */
     @Test
     public void testQueryPersonalFineTuningJobs() throws JsonProcessingException {
         QueryPersonalFineTuningJobRequest queryPersonalFineTuningJobRequest = new QueryPersonalFineTuningJobRequest();
         queryPersonalFineTuningJobRequest.setLimit(1);
         QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobApiResponse = client.queryPersonalFineTuningJobs(queryPersonalFineTuningJobRequest);
         logger.info("model output: {}", mapper.writeValueAsString(queryPersonalFineTuningJobApiResponse));
     }


    private static String getAsyncTaskId() throws JsonProcessingException {
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
        logger.info("model output: {}", mapper.writeValueAsString(invokeModelApiResp));
        return invokeModelApiResp.getData().getId();
    }


    private static void testQueryResult(String taskId) throws JsonProcessingException {
        QueryModelResultRequest request = new QueryModelResultRequest();
        request.setTaskId(taskId);
        QueryModelResultResponse queryResultResp = client.queryModelResult(request);
        logger.info("model output {}", mapper.writeValueAsString(queryResultResp));
    }

    public static Flowable<ChatMessageAccumulator> mapStreamToAccumulator(Flowable<ModelData> flowable) {
        return flowable.map(chunk -> {
            return new ChatMessageAccumulator(chunk.getChoices().get(0).getDelta(), null, chunk.getChoices().get(0), chunk.getUsage(), chunk.getCreated(), chunk.getId());
        });
    }
}
