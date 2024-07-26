package com.zhipu.oapi.service.v4.deserialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zhipu.oapi.service.v4.deserialize.embedding.EmbeddingDeserializer;
import com.zhipu.oapi.service.v4.deserialize.embedding.EmbeddingResultDeserializer;
import com.zhipu.oapi.service.v4.deserialize.image.ImageDeserializer;
import com.zhipu.oapi.service.v4.deserialize.image.ImageResultDeserializer;
import com.zhipu.oapi.service.v4.deserialize.knowledge.KnowledgeInfoDeserializer;
import com.zhipu.oapi.service.v4.deserialize.knowledge.KnowledgePageDeserializer;
import com.zhipu.oapi.service.v4.deserialize.knowledge.KnowledgeStatisticsDeserializer;
import com.zhipu.oapi.service.v4.deserialize.knowledge.KnowledgeUsedDeserializer;
import com.zhipu.oapi.service.v4.deserialize.knowledge.document.*;
import com.zhipu.oapi.service.v4.deserialize.tools.*;
import com.zhipu.oapi.service.v4.deserialize.videos.VideoObjectDeserializer;
import com.zhipu.oapi.service.v4.deserialize.videos.VideoResultDeserializer;
import com.zhipu.oapi.service.v4.embedding.Embedding;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.image.Image;
import com.zhipu.oapi.service.v4.image.ImageResult;
import com.zhipu.oapi.service.v4.knowledge.KnowledgeInfo;
import com.zhipu.oapi.service.v4.knowledge.KnowledgePage;
import com.zhipu.oapi.service.v4.knowledge.KnowledgeStatistics;
import com.zhipu.oapi.service.v4.knowledge.KnowledgeUsed;
import com.zhipu.oapi.service.v4.knowledge.document.*;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.model.params.CodeGeexContext;
import com.zhipu.oapi.service.v4.model.params.CodeGeexExtra;
import com.zhipu.oapi.service.v4.model.params.CodeGeexTarget;
import com.zhipu.oapi.service.v4.tools.*;
import com.zhipu.oapi.service.v4.videos.VideoObject;
import com.zhipu.oapi.service.v4.videos.VideoResult;

public class MessageDeserializeFactory {

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        SimpleModule module = new SimpleModule();

        module.addDeserializer(ModelData.class, new ModelDataDeserializer());
        module.addDeserializer(Choice.class, new ChoiceDeserializer());
        module.addDeserializer(ChatMessage.class, new ChatMessageDeserializer());
        module.addDeserializer(Delta.class, new DeltaDeserializer());
        module.addDeserializer(ToolCalls.class, new ToolCallsDeserializer());
        module.addDeserializer(ChatFunctionCall.class, new ChatFunctionCallDeserializer());
        module.addDeserializer(CodeGeexContext.class, new CodeGeexContextDeserializer());
        module.addDeserializer(ChoiceDelta.class, new ChoiceDeltaDeserializer());
        module.addDeserializer(ChoiceDeltaToolCall.class, new ChoiceDeltaToolCallDeserializer());
        module.addDeserializer(SearchChatMessage.class, new SearchChatMessageDeserializer());
        module.addDeserializer(SearchIntent.class, new SearchIntentDeserializer());
        module.addDeserializer(SearchRecommend.class, new SearchRecommendDeserializer());
        module.addDeserializer(SearchResult.class, new SearchResultDeserializer());
        module.addDeserializer(WebSearchChoice.class, new WebSearchChoiceDeserializer());
        module.addDeserializer(WebSearchMessage.class, new WebSearchMessageDeserializer());
        module.addDeserializer(WebSearchMessageToolCall.class, new WebSearchMessageToolCallDeserializer());
        module.addDeserializer(WebSearchPro.class, new WebSearchProDeserializer());
        module.addDeserializer(VideoResult.class, new VideoResultDeserializer());
        module.addDeserializer(VideoObject.class, new VideoObjectDeserializer());
        module.addDeserializer(Image.class, new ImageDeserializer());
        module.addDeserializer(ImageResult.class, new ImageResultDeserializer());
        module.addDeserializer(EmbeddingResult.class, new EmbeddingResultDeserializer());
        module.addDeserializer(Embedding.class, new EmbeddingDeserializer());
        module.addDeserializer(KnowledgeInfo.class, new KnowledgeInfoDeserializer());
        module.addDeserializer(KnowledgeUsed.class, new KnowledgeUsedDeserializer());
        module.addDeserializer(KnowledgeStatistics.class, new KnowledgeStatisticsDeserializer());
        module.addDeserializer(KnowledgePage.class, new KnowledgePageDeserializer());
        module.addDeserializer(DocumentFailedInfo.class, new DocumentFailedInfoDeserializer());
        module.addDeserializer(DocumentObject.class, new DocumentObjectDeserializer());
        module.addDeserializer(DocumentSuccessInfo.class, new DocumentSuccessInfoDeserializer());
        module.addDeserializer(DocumentData.class, new DocumentDataDeserializer());
        module.addDeserializer(DocumentDataFailInfo.class, new DocumentDataFailInfoDeserializer());
        module.addDeserializer(DocumentPage.class, new DocumentPageDeserializer());
        module.addDeserializer(EmbeddingResult.class, new EmbeddingResultDeserializer());
        module.addDeserializer(Embedding.class, new EmbeddingDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

}
