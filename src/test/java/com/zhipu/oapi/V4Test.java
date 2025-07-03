package com.zhipu.oapi;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zhipu.oapi.core.response.HttpxBinaryResponseContent;
import com.zhipu.oapi.service.v4.audio.AudioCustomizationApiResponse;
import com.zhipu.oapi.service.v4.audio.AudioCustomizationRequest;
import com.zhipu.oapi.service.v4.audio.AudioSpeechApiResponse;
import com.zhipu.oapi.service.v4.audio.AudioSpeechRequest;
import com.zhipu.oapi.service.v4.batchs.BatchCreateParams;
import com.zhipu.oapi.service.v4.batchs.BatchResponse;
import com.zhipu.oapi.service.v4.batchs.QueryBatchResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingApiResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.file.*;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.mock.MockClientV4;
import com.zhipu.oapi.service.v4.image.CreateImageRequest;
import com.zhipu.oapi.service.v4.image.ImageApiResponse;
import com.zhipu.oapi.service.v4.model.*;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Testcontainers
public class V4Test {

    private final static Logger logger = LoggerFactory.getLogger(V4Test.class);
    private static final String API_SECRET_KEY = Constants.getApiKey() != null ? Constants.getApiKey() : "test-api-key.test-api-secret";

    private static final String API_BASE_URL = Constants.getBaseUrl();


    private static final ClientV4 client = new ClientV4.Builder(API_BASE_URL,API_SECRET_KEY)
            .enableTokenCache()
            .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
            .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
            .build();

    // Please customize your own business ID
    private static final String requestIdTemplate = "mycompany-%d";

    private static final ObjectMapper mapper = new ObjectMapper();


    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapper;
    }

    @Test
    public void test() {

    }

    /**
     * SSE-V4: Function calling
     */
    @Test
    public void testFunctionSSE() throws JsonProcessingException {
        // Check if using test API key, skip real API call if so
        if (API_SECRET_KEY != null && API_SECRET_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            
            List<ChatMessage> messages = new ArrayList<>();
            ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "How long does it take from Chengdu to Beijing, and what's the weather like?");
            messages.add(chatMessage);
            String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
            
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    .model(Constants.ModelChatGLM4)
                    .stream(Boolean.TRUE)
                    .messages(messages)
                    .requestId(requestId)
                    .build();
            
            // Use mock data
            ModelApiResponse mockResponse = MockClientV4.mockModelApi(chatCompletionRequest);
            logger.info("Mock Function SSE response: {}", mockResponse);
            return;
        }
        
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "How long does it take from Chengdu to Beijing, and what's the weather like?");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        // Function call parameter construction
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();

        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String, Object> properties = new HashMap<>();
        properties.put("location", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "City, e.g.: Beijing");
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
                                logger.info("tool_calls: {}", jsonString);
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
            sseModelApiResp.setFlowable(null);// Clear flowable before printing
            sseModelApiResp.setData(data);
        }
        logger.info("model output: {}", mapper.writeValueAsString(sseModelApiResp));
    }


    /**
     * SSE-V4: Non-function calling
     */
    @Test
    public void testNonFunctionSSE() throws JsonProcessingException {
        // Check if using test API key, skip real API call if so
        if (API_SECRET_KEY != null && API_SECRET_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            List<ChatMessage> messages = new ArrayList<>();
            ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "Which is more powerful, ChatGLM or you?");
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
            
            // Use mock data
            ModelApiResponse sseModelApiResp = MockClientV4.mockModelApi(chatCompletionRequest);
            sseModelApiResp.setFlowable(null);// Clear flowable before printing
            logger.info("Mock response: {}", mapper.writeValueAsString(sseModelApiResp));
            return;
        }
        
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "Which is more powerful, ChatGLM or you?");
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
        // Stream processing method
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
            sseModelApiResp.setFlowable(null);// Clear flowable before printing
            sseModelApiResp.setData(data);
        }
        logger.info("model output: {}", mapper.writeValueAsString(sseModelApiResp));
    }


    /**
     * V4-Synchronous function calling
     */
    @Test
    public void testFunctionInvoke() {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "What can you do?");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        // Function call parameter construction
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String, Object> properties = new HashMap<>();
        properties.put("location", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "City, e.g.: Beijing");
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


        ChatTool chatTool1 = new ChatTool();
        chatTool1.setType(ChatToolType.WEB_SEARCH.value());
        WebSearch webSearch = new WebSearch();
        webSearch.setSearch_query("Tsinghua University enrollment rate");
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
            logger.info("model output: {}", mapper.writeValueAsString(invokeModelApiResp));
        } catch (JsonProcessingException e) {
            logger.error("model output error", e);
        }
    }


    /**
     * V4-Synchronous non-function calling
     */
    @Test
    public void testNonFunctionInvoke() throws JsonProcessingException {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "Which is more powerful, ChatGLM or you?");
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
     * V4-Synchronous non-function calling
     */
    @Test
    public void testCharGlmInvoke() throws JsonProcessingException {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "Which is more powerful, ChatGLM or you?");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());


        HashMap<String, Object> extraJson = new HashMap<>();
        extraJson.put("temperature", 0.5);

        ChatMeta meta = new ChatMeta();
        meta.setUser_info("I am Lu Xingchen, a male, a well-known director, and also Su Mengyuan's collaborative director. I am good at filming music-themed movies. Su Mengyuan's attitude towards me is respectful, and she regards me as a mentor and friend.");
        meta.setBot_info("Su Mengyuan, whose real name is Su Yuanxin, is a popular domestic female singer and actress. After participating in talent shows, she quickly became famous and entered the entertainment industry with her unique voice and outstanding stage charm. She is beautiful in appearance, but her real charm lies in her talent and diligence. Su Mengyuan is an excellent graduate of the music academy, good at creation, and has many popular original songs. In addition to her achievements in music, she is also enthusiastic about charity, actively participates in public welfare activities, and spreads positive energy through practical actions. At work, she is very dedicated to her work, always fully devoted to her roles when filming, winning praise from industry insiders and fans' love. Although in the entertainment industry, she always maintains a low-key and humble attitude, deeply respected by peers. In expression, Su Mengyuan likes to use 'we' and 'together', emphasizing team spirit.");
        meta.setBot_name("Su Mengyuan");
        meta.setUser_name("Lu Xingchen");

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelCharGLM3)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .requestId(requestId)
                .meta(meta)
                .extraJson(extraJson)
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        logger.info("model output: {}", mapper.writeValueAsString(invokeModelApiResp));
    }

    /**
     * V4 Asynchronous calling
     */
    @Test
    public void testAsyncInvoke() throws JsonProcessingException {
        String taskId = getAsyncTaskId();
        testQueryResult(taskId);
    }

    /**
     * Text-to-image
     */
    @Test
    public void testCreateImage() throws JsonProcessingException {
        CreateImageRequest createImageRequest = new CreateImageRequest();
        createImageRequest.setModel(Constants.ModelCogView);
        createImageRequest.setPrompt("Futuristic cloud data center, showcasing advanced technologgy and a high-tech atmosp\n" +
                "here. The image should depict a spacious, well-lit interior with rows of server racks, glo\n" +
                "wing lights, and digital displays. Include abstract representattions of data streams and\n" +
                "onnectivity, symbolizing the essence of cloud computing. Thee style should be modern a\n" +
                "nd sleek, with a focus on creating a sense of innovaticon and cutting-edge technology\n" +
                "The overall ambiance should convey the power and effciency of cloud services in a visu\n" +
                "ally engaging way.");
        createImageRequest.setRequestId("test11111111111111");
        ImageApiResponse imageApiResponse = client.createImage(createImageRequest);
        logger.info("imageApiResponse: {}", mapper.writeValueAsString(imageApiResponse));
    }

    /**
     * glm-4-voice测试
     */
    @Test
    public void testVoice() throws JsonProcessingException {
        List<ChatMessage> messages = new ArrayList<>();
        List<Object> contentList = new ArrayList<>();
        Map<String, Object> content = new HashMap<>();
        content.put("type", "text");
        content.put("text", "给我讲个冷笑话");
        contentList.add(content);
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), contentList);

        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        HashMap<String, Object> extraJson = new HashMap<>();
        extraJson.put("temperature", 0.5);
        extraJson.put("max_tokens", 1024);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4Voice)
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
     * Vector model V4
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
     * V4 Fine-tuning upload dataset
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
     * Fine-tuning V4 - Query uploaded file list
     */
    @Test
    public void testQueryUploadFileList() throws JsonProcessingException {
        QueryFilesRequest queryFilesRequest = new QueryFilesRequest();
        QueryFileApiResponse queryFileApiResponse = client.queryFilesApi(queryFilesRequest);
        logger.info("model output: {}", mapper.writeValueAsString(queryFileApiResponse));
    }

    @Test
    public void testFileContent() throws IOException {
        try {

            HttpxBinaryResponseContent httpxBinaryResponseContent = client.fileContent("20240514_ea19d21b-d256-4586-b0df-e80a45e3c286");
            String filePath = "demo_output.jsonl";
            String resourcePath = V4Test.class.getClassLoader().getResource("").getPath();

            httpxBinaryResponseContent.streamToFile(resourcePath + "1" + filePath, 1000);

        } catch (IOException e) {
            logger.error("file content error", e);
        }
    }

    /**
     * Fine-tuning V4 - Create fine-tuning task
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
     * Fine-tuning V4 - Query fine-tuning task
     */
    @Test
    public void testRetrieveFineTuningJobs() throws JsonProcessingException {
        QueryFineTuningJobRequest queryFineTuningJobRequest = new QueryFineTuningJobRequest();
        queryFineTuningJobRequest.setJobId("ftjob-20240429172916475-fb7r9");
        QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = client.retrieveFineTuningJobs(queryFineTuningJobRequest);
        logger.info("model output: {}", mapper.writeValueAsString(queryFineTuningJobApiResponse));
    }


    /**
     * Fine-tuning V4 - Query fine-tuning task
     */
    @Test
    public void testFueryFineTuningJobsEvents() throws JsonProcessingException {
        QueryFineTuningJobRequest queryFineTuningJobRequest = new QueryFineTuningJobRequest();
        queryFineTuningJobRequest.setJobId("ftjob-20240429172916475-fb7r9");

        QueryFineTuningEventApiResponse queryFineTuningEventApiResponse = client.queryFineTuningJobsEvents(queryFineTuningJobRequest);
        logger.info("model output: {}", mapper.writeValueAsString(queryFineTuningEventApiResponse));
    }


    /**
     * testQueryPersonalFineTuningJobs V4 - Query personal fine-tuning tasks
     */
    @Test
    public void testQueryPersonalFineTuningJobs() throws JsonProcessingException {
        QueryPersonalFineTuningJobRequest queryPersonalFineTuningJobRequest = new QueryPersonalFineTuningJobRequest();
        queryPersonalFineTuningJobRequest.setLimit(1);
        QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobApiResponse = client.queryPersonalFineTuningJobs(queryPersonalFineTuningJobRequest);
        logger.info("model output: {}", mapper.writeValueAsString(queryPersonalFineTuningJobApiResponse));
    }


    @Test
    public void testBatchesCreate() {
        BatchCreateParams batchCreateParams = new BatchCreateParams(
                "24h",
                "/v4/chat/completions",
                "20240514_ea19d21b-d256-4586-b0df-e80a45e3c286",
                new HashMap<String, String>() {{
                    put("key1", "value1");
                    put("key2", "value2");
                }}
        );

        BatchResponse batchResponse = client.batchesCreate(batchCreateParams);
        logger.info("output: {}", batchResponse);
    }

    @Test
    public void testDeleteFineTuningJob() {
        FineTuningJobIdRequest request = FineTuningJobIdRequest.builder().jobId("test").build();
        QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = client.deleteFineTuningJob(request);
        logger.info("output: {}", queryFineTuningJobApiResponse);

    }

    @Test
    public void testCancelFineTuningJob() {
        FineTuningJobIdRequest request = FineTuningJobIdRequest.builder().jobId("test").build();
        QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = client.cancelFineTuningJob(request);
        logger.info("output: {}", queryFineTuningJobApiResponse);

    }

    @Test
    public void testBatchesRetrieve() {
        BatchResponse batchResponse = client.batchesRetrieve("batch_1791021399316246528");
        logger.info("output: {}", batchResponse);

    }

    @Test
    public void testDeleteFineTuningModel() {
        FineTuningJobModelRequest request = FineTuningJobModelRequest.builder().fineTunedModel("test").build();

        FineTunedModelsStatusResponse fineTunedModelsStatusResponse = client.deleteFineTuningModel(request);
        logger.info("output: {}", fineTunedModelsStatusResponse);

    }

    @Test
    public void testBatchesList() {
        QueryBatchRequest queryBatchRequest = new QueryBatchRequest();
        queryBatchRequest.setLimit(10);
        QueryBatchResponse queryBatchResponse = client.batchesList(queryBatchRequest);
        logger.info("output: {}", queryBatchResponse);

    }

    @Test
    public void testBatchesCancel() {
        BatchResponse batchResponse = client.batchesCancel("batch_1791021399316246528");
        logger.info("output: {}", batchResponse);
    }

    @Test
    public void testAudioSpeech() throws IOException {
        AudioSpeechRequest audioSpeechRequest = AudioSpeechRequest.builder()
                .model(Constants.ModelTTS)
                .input("智谱，你好呀")
                .voice("child")
                .responseFormat("wav")
                .build();
        AudioSpeechApiResponse audioSpeechApiResponse = client.speech(audioSpeechRequest);
        File file = audioSpeechApiResponse.getData();
        file.createNewFile();

        logger.info("testAudioSpeech file generation,fileName:{},filePath:{}",audioSpeechApiResponse.getData().getName(),audioSpeechApiResponse.getData().getAbsolutePath());

    }

    @Test
    public void testAudioCustomization() throws IOException {
        AudioCustomizationRequest audioCustomizationRequest = AudioCustomizationRequest.builder()
                .model(Constants.ModelTTS)
                .input("智谱，你好呀")
                .voiceText("这是一条测试用例")
                .voiceData(new File("/Users/jhy/Desktop/tts/test_case_8s.wav"))
                .responseFormat("wav")
                .build();
        AudioCustomizationApiResponse audioCustomizationApiResponse = client.customization(audioCustomizationRequest);
        File file = audioCustomizationApiResponse.getData();
        file.createNewFile();
        logger.info("testAudioCustomization file generation,fileName:{},filePath:{}",audioCustomizationApiResponse.getData().getName(),audioCustomizationApiResponse.getData().getAbsolutePath());
    }

    private static String getAsyncTaskId() throws JsonProcessingException {
        // Check if using test API key, skip real API call if so
        if (API_SECRET_KEY != null && API_SECRET_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            List<ChatMessage> messages = new ArrayList<>();
            ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "Which is more powerful, ChatGLM or you?");
            messages.add(chatMessage);
            String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
            // Function call parameter construction part
            List<ChatTool> chatToolList = new ArrayList<>();
            ChatTool chatTool = new ChatTool();
            chatTool.setType(ChatToolType.FUNCTION.value());
            ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
            chatFunctionParameters.setType("object");
            Map<String, Object> properties = new HashMap<>();
            properties.put("location", new HashMap<String, Object>() {{
                put("type", "string");
                put("description", "City, e.g.: Beijing");
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
            
            // Use mock data
            ModelApiResponse invokeModelApiResp = MockClientV4.mockModelApi(chatCompletionRequest);
            logger.info("Mock response: {}", mapper.writeValueAsString(invokeModelApiResp));
            return invokeModelApiResp.getData().getId();
        }
        
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "Which is more powerful, ChatGLM or you?");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        // Function call parameter construction part
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String, Object> properties = new HashMap<>();
        properties.put("location", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "City, e.g.: Beijing");
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
