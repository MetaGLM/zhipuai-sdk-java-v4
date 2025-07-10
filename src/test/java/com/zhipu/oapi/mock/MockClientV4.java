package com.zhipu.oapi.mock;

import com.zhipu.oapi.service.v4.embedding.Embedding;
import com.zhipu.oapi.service.v4.audio.AudioTranscriptionsRequest;
import com.zhipu.oapi.service.v4.embedding.EmbeddingApiResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.tools.*;
import com.zhipu.oapi.service.v4.web_search.*;
import io.reactivex.Flowable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Mock utility class for simulating API calls in tests
 */
public class MockClientV4 {
    
    /**
     * Mock WebSearchProStreamingInvoke method
     */
    public static WebSearchApiResponse mockWebSearchProStreamingInvoke(WebSearchParamsRequest request) {
        WebSearchApiResponse response = new WebSearchApiResponse();
        response.setSuccess(true);
        response.setCode(200);
        response.setMsg("success");
        
        // Create mock streaming data
        List<WebSearchPro> mockData = createMockWebSearchProData();
        Flowable<WebSearchPro> flowable = Flowable.fromIterable(mockData)
            .delay(100, TimeUnit.MILLISECONDS);
        
        response.setFlowable(flowable);
        return response;
    }
    
    /**
     * Mock WebSearchProInvoke method
     */
    public static WebSearchApiResponse mockWebSearchProInvoke(WebSearchParamsRequest request) {
        WebSearchApiResponse response = new WebSearchApiResponse();
        response.setSuccess(true);
        response.setCode(200);
        response.setMsg("success");
        
        // Create mock WebSearchPro data
        WebSearchPro data = createMockWebSearchProData().get(0);
        response.setData(data);
        
        return response;
    }
    
    /**
     * Mock WebSearch method
     */
    public static WebSearchResponse mockWebSearch(WebSearchRequest request) {
        WebSearchResponse response = new WebSearchResponse();
        response.setSuccess(true);
        response.setCode(200);
        response.setMsg("success");
        
        // Create mock WebSearchDTO data
        WebSearchDTO data = createMockWebSearchDTO();
        response.setData(data);
        
        return response;
    }
    
    /**
     * Mock ModelApi method
     */
    public static ModelApiResponse mockModelApi(ChatCompletionRequest request) {
        ModelApiResponse response = new ModelApiResponse();
        response.setSuccess(true);
        response.setCode(200);
        response.setMsg("success");
        
        if (request.getStream() != null && request.getStream()) {
            // Streaming response
            List<ModelData> mockData = createMockModelData();
            Flowable<ModelData> flowable = Flowable.fromIterable(mockData)
                .delay(100, TimeUnit.MILLISECONDS);
            response.setFlowable(flowable);
        } else {
            // Non-streaming response
            ModelData data = createMockModelData().get(0);
            response.setData(data);
        }
        
        return response;
    }
    
    /**
     * Mock EmbeddingApi method
     */
    public static EmbeddingApiResponse mockEmbeddingApi(EmbeddingRequest request) {
        EmbeddingApiResponse response = new EmbeddingApiResponse();
        response.setSuccess(true);
        response.setCode(200);
        response.setMsg("success");
        
        // Create mock EmbeddingResult data
        EmbeddingResult data = createMockEmbeddingResult(request);
        response.setData(data);
        
        return response;
    }
    
    /**
     * Create mock WebSearchPro data list
     */
    private static List<WebSearchPro> createMockWebSearchProData() {
        List<WebSearchPro> dataList = new ArrayList<>();
        
        WebSearchPro data = new WebSearchPro();
        data.put("created", System.currentTimeMillis() / 1000);
        data.put("id", "mock-websearch-" + System.currentTimeMillis());
        data.put("request_id", "mock-request-" + System.currentTimeMillis());
        
        // Create mock choices
        List<WebSearchChoice> choices = new ArrayList<>();
        WebSearchChoice choice = new WebSearchChoice();
        choice.put("index", 0);
        choice.put("finish_reason", "stop");
        
        // Create mock message
        WebSearchMessage message = new WebSearchMessage();
        message.put("role", "assistant");
        message.put("content", "This is a mock search result. Based on the search, I found relevant information.");
        
        choice.set("message", message);
        choices.add(choice);
        
        data.putPOJO("choices", choices);
        dataList.add(data);
        
        return dataList;
    }
    
    /**
     * Create mock WebSearchDTO data
     */
    private static WebSearchDTO createMockWebSearchDTO() {
        WebSearchDTO dto = new WebSearchDTO();
        dto.setCreated((int) (System.currentTimeMillis() / 1000));
        dto.setId("mock-websearch-" + System.currentTimeMillis());
        dto.setRequestId("mock-request-" + System.currentTimeMillis());
        
        // Create mock search results
        List<WebSearchResp> searchResults = new ArrayList<>();
        WebSearchResp result = new WebSearchResp();
        result.setTitle("Mock Search Result Title");
        result.setContent("This is mock search result content for testing purposes.");
        result.setLink("https://example.com/mock-result");
        result.setRefer("example.com");
        searchResults.add(result);
        dto.setWebSearchResp(searchResults);
        
        // Create mock search intent
        List<SearchIntentResp> searchIntents = new ArrayList<>();
        SearchIntentResp intent = new SearchIntentResp();
        intent.setQuery("Mock search query");
        intent.setIntent("search");
        intent.setKeywords("mock,test,keywords");
        searchIntents.add(intent);
        dto.setSearchIntentResp(searchIntents);
        
        return dto;
    }
    
    /**
     * Create mock ModelData data list
     */
    private static List<ModelData> createMockModelData() {
        List<ModelData> dataList = new ArrayList<>();
        
        ModelData data = new ModelData();
        data.setCreated(System.currentTimeMillis());
        data.setId("mock-chat-" + System.currentTimeMillis());
        data.setRequestId("mock-request-" + System.currentTimeMillis());
        
        // Create mock choices
        List<Choice> choices = new ArrayList<>();
        Choice choice = new Choice();
        choice.setIndex(0L);
        choice.setFinishReason("stop");
        
        // Create mock message
        ChatMessage message = new ChatMessage();
        message.setRole(ChatMessageRole.ASSISTANT.value());
        message.setContent("This is a mock AI assistant reply for testing purposes.");
        
        choice.setMessage(message);
        choices.add(choice);
        
        data.setChoices(choices);
        dataList.add(data);
        
        return dataList;
    }
    
    /**
     * Create mock EmbeddingResult data
     */
    private static EmbeddingResult createMockEmbeddingResult(EmbeddingRequest request) {
        EmbeddingResult result = new EmbeddingResult();
        result.setModel(request.getModel() != null ? request.getModel() : "embedding-3");
        result.setObject("list");
        
        // Create mock embedding data
        List<Embedding> embeddings = new ArrayList<>();
        
        // Create corresponding number of embeddings based on input type
        int count = 1;
        if (request.getInput() instanceof List) {
            count = ((List<?>) request.getInput()).size();
        }
        
        for (int i = 0; i < count; i++) {
            Embedding embedding = new Embedding();
            embedding.setObject("embedding");
            embedding.setIndex(i);
            
            // Create mock vector data
            int dimensions = request.getDimensions() != null ? request.getDimensions() : 512;
            List<Double> vector = new ArrayList<>();
            Random random = new Random();
            for (int j = 0; j < dimensions; j++) {
                vector.add(random.nextGaussian());
            }
            embedding.setEmbedding(vector);
            
            embeddings.add(embedding);
        }
        
        result.setData(embeddings);
        
        // Create mock usage information
        Usage usage = new Usage();
        usage.setPromptTokens(10);
        usage.setTotalTokens(10);
        result.setUsage(usage);
        
        return result;
    }
    
    /**
     * Mock Transcriptions API call
     */
    public static ModelApiResponse mockTranscriptionsApi(AudioTranscriptionsRequest request) {
        ModelApiResponse response = new ModelApiResponse();
        response.setCode(200);
        response.setMsg("success");
        response.setSuccess(true);
        
        // Create mock transcription result
        List<ModelData> dataList = new ArrayList<>();
        ModelData data = new ModelData();
        data.setCreated(System.currentTimeMillis());
        data.setId("mock-transcription-" + System.currentTimeMillis());
        data.setRequestId("mock-request-" + System.currentTimeMillis());
        
        // Create mock choices
        List<Choice> choices = new ArrayList<>();
        Choice choice = new Choice();
        choice.setIndex(0L);
        choice.setFinishReason("stop");
        
        // Create mock transcription text
        ChatMessage message = new ChatMessage();
        message.setRole(ChatMessageRole.ASSISTANT.value());
        message.setContent("This is mock voice transcription text content.");
        choice.setMessage(message);
        
        choices.add(choice);
        data.setChoices(choices);
        
        // Create mock usage information
        Usage usage = new Usage();
        usage.setPromptTokens(0);
        usage.setCompletionTokens(10);
        usage.setTotalTokens(10);
        data.setUsage(usage);
        
        response.setData(data);
        
        return response;
    }
}