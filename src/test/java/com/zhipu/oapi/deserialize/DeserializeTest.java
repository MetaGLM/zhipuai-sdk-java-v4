package com.zhipu.oapi.deserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.model.*;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
public class DeserializeTest {

    private final ObjectMapper mapper = MessageDeserializeFactory.defaultObjectMapper();

    @Test
    public void testModelDataDeserializer() throws JsonProcessingException {
        String json = "{" +
                "\"id\": \"8655897009087545746\"," +
                "\"created\": 1715676949," +
                "\"model\": \"glm-4\"," +
                "\"choices\": [" +
                "{" +
                "\"index\": 0," +
                "\"finish_reason\": \"length\"," +
                "\"delta\": {" +
                "\"role\": \"assistant\"," +
                "\"content\": \"\"" +
                "}" +
                "}" +
                "]," +
                "\"usage\": {" +
                "\"prompt_tokens\": 728," +
                "\"completion_tokens\": 50," +
                "\"total_tokens\": 778" +
                "}," +
                "\"web_search\": [" +
                "{" +
                "\"title\": \"Where do Tsinghua University graduates go? Undergraduate enrollment rate 79.6%!\"," +
                "\"link\": \"https://toutiao.com/group/7204786103965188623/\"," +
                "\"content\": \"Many friends are always concerned or curious: where do Tsinghua graduates go? Let's take a look at what Tsinghua University's '2022 Graduate Employment Quality Report' says.\\nTsinghua University had a total of 8,003 graduates in 2022, an increase compared to previous years.\\nAmong them, 3,197 undergraduates (39.9%), 2,657 master's students (33.2%), and 2,149 doctoral students (26.9%);\\n5,135 male students (64.2%) and 2,868 female students (35.8%), with a male-to-female ratio of 1.8:1\\nCompared to the 2021 cohort, all types of students showed a slight increase. (Tsinghua University's 2021 graduates totaled 7,741, including 3,154 undergraduates, 2,437 master's students, and 1,847 doctoral students.)\\nAs of October 31, 2022, the employment rate for Tsinghua University's 2022 graduates was 98.0%.\\nThis table tells us three things: 1. In top domestic universities, undergraduates mainly pursue further education while graduate students mainly seek employment, which has become a common phenomenon.\\n2. Tsinghua University's undergraduate enrollment rate is as high as 79.6%. Compared to 2021 undergraduate graduates, whether domestic enrollment or studying abroad, the enrollment rate has reached a new high!\\n2021 graduates: domestic enrollment accounted for 63.7%, overseas study rate was 13%, combined for a 76.6% further education rate\\n3. Even a strong university like Tsinghua is not as many people imagine - 100% employment with prestigious employers. Look at Tsinghua's undergraduate flexible employment numbers and unemployed numbers; combined, they exceed the number of those with signed three-party employment contracts.\\nPlease, it is after all a science and engineering university, different from art colleges.\\nDifferent from a considerable portion of Beijing university graduates\"," +
                "\"media\": \"Toutiao\"," +
                "\"icon\": \"https://sfile.chatglm.cn/searchImage/toutiao_com_icon.jpg\"" +
                "}," +
                "{" +
                "\"title\": \"UCAS, Tsinghua, ShanghaiTech rank top three! Target undergraduate enrollment rates for college applications, 41 schools exceed 50% (Published: 2023-06-22 23:41:58)\"," +
                "\"link\": \"https://zhuanlan.zhihu.com/p/638950178\"," +
                "\"content\": \"Starting from June 23, nearly 13 million college entrance examination candidates from 31 provinces nationwide will gradually enter the score checking phase, followed by the most critical college application process. College applications need to comprehensively consider test scores, personal interests, university comprehensive strength, major rankings, university location, employment and other factors. More and more candidates and parents value university enrollment rates. Recently, a university consultation meeting held at Shanghai Fudan High School attracted 36 prestigious schools including Fudan, Shanghai Jiao Tong University, Tongji, East China Normal University, as well as Tsinghua, Renmin University, Nanjing University, etc. From the hot topics of on-site consultations by candidates and parents, enrollment rate is not only an important indicator affecting candidates' and parents' application decisions, but has also become a major tool for university (department) enrollment promotion. Undergraduate enrollment rate is the proportion of undergraduates choosing to pursue master's degrees. Generally speaking, the higher the enrollment rate, the stronger the university's comprehensive strength. From the currently published undergraduate enrollment rates of 114 'Double First-Class' universities, 16 have enrollment rates exceeding 60%, 41 exceed 50%, and 75 exceed 40%. The University of Chinese Academy of Sciences has the highest 2022 undergraduate enrollment rate, exceeding 90%, with domestic enrollment rate at 81.5% and overseas enrollment rate at 8.7%. UCAS began enrolling undergraduates in 2014, with the smallest enrollment among all prestigious universities - only 408 undergraduates in 2022. The extremely high enrollment rate is partly related to UCAS being under the Chinese Academy of Sciences, and partly due to small enrollment numbers. Tsinghua University, ShanghaiTech University, University of Science and Technology of China, and Southern University of Science and Technology all have undergraduate enrollment rates exceeding 70%. As the top-ranked university in China, Tsinghua's undergraduate enrollment rate\"," +
                "\"media\": \"Zhihu Column\"," +
                "\"icon\": \"https://sfile.chatglm.cn/searchImage/zhuanlan_zhihu_com_icon.jpg\"" +
                "}" +
                "]" +
                "}";

        ModelData jsonNodes = mapper.readValue(json, ModelData.class);
        assert jsonNodes != null;
        assert jsonNodes.getChoices().size() == 1;
    }

    @Test
    public void testChoiceDeserializer() throws JsonProcessingException {
        String json = " " +
                "        {\n" +
                "            \"index\": 0,\n" +
                "            \"finish_reason\": \"length\",\n" +
                "            \"delta\": {\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"content\": \"123\"\n" +
                "            }\n" +
                "        }\n" +
                "    ";
        Choice choice = mapper.readValue(json, Choice.class);
        assert choice != null;
        assert choice instanceof Choice;
        assert choice.getFinishReason().equals("length");

    }

    @Test
    public void testChatMessageDeserializer() throws JsonProcessingException {
        String json = "{\n" +
                "    \"role\": \"assistant\",\n" +
                "    \"content\": \"123\",\n" +
                "    \"name\": \"1222\"\n" +
                "}";
        ChatMessage chatMessage = mapper.readValue(json, ChatMessage.class);
        assert chatMessage != null;
        assert chatMessage instanceof ChatMessage;
        assert chatMessage.getRole().equals("assistant");
        assert chatMessage.getContent().equals("123");
        assert chatMessage.getName().equals("1222");
    }

    @Test
    public void testDeltaDeserializer() throws JsonProcessingException {
        String json = "{\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"content\": \"123\"\n" +
                "            }";
        Delta delta = mapper.readValue(json, Delta.class);

        assert delta != null;
        assert delta instanceof Delta;
        assert delta.getRole().equals("assistant");
        assert delta.getContent().equals("123");

    }

    @Test
    public void testToolCallsDeserializer() throws JsonProcessingException {
        String json = "{\n" +
                "                        \"id\": \"call_8655897249605738470\",\n" +
                "                        \"index\": 0,\n" +
                "                        \"type\": \"function\",\n" +
                "                        \"function\": {\n" +
                "                            \"name\": \"get_current_weather\",\n" +
                "                            \"arguments\": \"{\\\"location\\\":\\\"Beijing\\\",\\\"unit\\\":\\\"celsius\\\"}\"\n" +
                "                        }\n" +
                "                    }";

        ToolCalls toolCalls = mapper.readValue(json, ToolCalls.class);

        assert toolCalls != null;
        assert toolCalls instanceof ToolCalls;
        assert toolCalls.getId().equals("call_8655897249605738470");
        assert toolCalls.getType().equals("function");
        assert toolCalls.getFunction().getName().equals("get_current_weather");
        assert toolCalls.getFunction().getArguments() instanceof TextNode;
        assert toolCalls.getFunction().getArguments().asText().equals("{\"location\":\"Beijing\",\"unit\":\"celsius\"}");

    }

    @Test
    public void testChatFunctionCallDeserializer() throws JsonProcessingException {
        String json = "{\n" +
                "                            \"name\": \"get_current_weather\",\n" +
                "                            \"arguments\": \"{\\\"location\\\":\\\"Beijing\\\",\\\"unit\\\":\\\"celsius\\\"}\"\n" +
                "                        }";
        ChatFunctionCall chatFunctionCall = mapper.readValue(json, ChatFunctionCall.class);
        assert chatFunctionCall != null;
        assert chatFunctionCall instanceof ChatFunctionCall;
        assert chatFunctionCall.getName().equals("get_current_weather");
        assert chatFunctionCall.getArguments() instanceof TextNode;
        assert chatFunctionCall.getArguments().asText().equals("{\"location\":\"Beijing\",\"unit\":\"celsius\"}");
    }

}
