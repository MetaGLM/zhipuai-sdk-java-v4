package com.zhipu.oapi.deserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.model.*;
import org.junit.Test;

import java.util.List;

public class DeserializeTest {

    private final ObjectMapper mapper = MessageDeserializeFactory.defaultObjectMapper();

    @Test
    public void testModelDataDeserializer() throws JsonProcessingException {
        String json = "{\n" +
                "    \"id\": \"8655897009087545746\",\n" +
                "    \"created\": 1715676949,\n" +
                "    \"model\": \"glm-4\",\n" +
                "    \"choices\": [\n" +
                "        {\n" +
                "            \"index\": 0,\n" +
                "            \"finish_reason\": \"length\",\n" +
                "            \"delta\": {\n" +
                "                \"role\": \"assistant\",\n" +
                "                \"content\": \"\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"usage\": {\n" +
                "        \"prompt_tokens\": 728,\n" +
                "        \"completion_tokens\": 50,\n" +
                "        \"total_tokens\": 778\n" +
                "    },\n" +
                "    \"web_search\": [\n" +
                "        {\n" +
                "            \"title\": \"清华大学的毕业生都去哪里了？本科生升学率79.6%！\",\n" +
                "            \"link\": \"https://toutiao.com/group/7204786103965188623/\",\n" +
                "            \"content\": \"有不少的朋友总是关心或者说好奇：清华的毕业生都去哪儿啊？我们一起来看一下，清华大学的《2022届毕业生就业质量报告》，是怎么说的。\\n清华大学2022届毕业生共8003人，与往年相比有所增加。\\n其中，本科生3197人（39.9%）、硕士 生2657人（33.2%）、博士生2149人（26.9%）；\\n男生5135人（64.2%）、女生2868人 （35.8%），男女比例为1.8:1\\n相比2021届，各类学生都有一个小幅增长。（清华大学2021届毕业生，共7741人。其中本科生3154人，硕士研究生2437人，博士生1847人。）\\n截至2022年10月31日，清华大学2022届毕业生毕业去向落实率为98.0%。\\n这个表格，告诉我们三件事：1、国内顶尖高校，本科生以升学为主，研究生以就业为主，已经成为普遍现象。\\n2、清华大学本科生升学率高达79.6%。；和2021届本科毕业生相比较，无论是国内升学，还是出国（境），升学比例再创新高！\\n2021届毕业生：国内升学占比63.7%，出国深造比例13%，二者相加，深造比例为76.6%\\n3、即使是强如清华大学，也不像很多人想象的那样，100%就业，而且单位很牛。看看清华的本科生灵活就业人数以及未就业人数，二者相加，超过了签三方就业的人数。\\n拜托，它毕竟是理工类高校，和艺术类院校不同的。\\n和相当一部分在京高校毕业生有所不同\",\n" +
                "            \"media\": \"今日头条\",\n" +
                "            \"icon\": \"https://sfile.chatglm.cn/searchImage/toutiao_com_icon.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"国科大、清华、上科大排前三！报志愿瞄准本科升学率，41所超50%（发布时间：2023-06-22 23:41:58）\",\n" +
                "            \"link\": \"https://zhuanlan.zhihu.com/p/638950178\",\n" +
                "            \"content\": \"从6月23日起，全国31个省份近1300万高考考生将陆续迎来查分时刻，接下来就是最关键的高考志愿填报。高考志愿需要综合考虑考分、个人兴趣、高校综合实力、专业排名、高校所在城市、就业等因素，越来越多的考生和家长更看重高校的升学率。近日，上海复旦附中举行的高校咨询会，吸引了复旦、上海交大、同济、华东师大以及清华、人大、南大等36所名校驻场摆摊”，从考生和家长的现场咨询的热门话题来看，升学率不仅是影响考生和家长报考的重要风向标”，也成了高校（院系）招生宣传的一大法宝。本科生升学率是本科生选择攻读硕士研究生人数所占的比例，一般来说，升学率越高代表着高校的综合实力越强。 从目前公布的114所双一流大学的本科升学率来看，升学率超过60%的有16所，超过50%的有41所，超过40%的有75所。2022届本科生升学率最高的中国科学院大学，超过90%，其中国内升学率为81.5%，境外升学率为8.7%。国科大从2014年开始招收本科生，招生人数是所有知名高校中最少的，2022年仅招收本科生408人，超高的升学率一方面与国科大由中科院主管有关，另一方面是招生人数较少。清华大学、上海科技大学、中国科学技术大学和南方科技大学的本科生升学率均超过70%。清华作为国内排名第一的高校，本科生升学率\",\n" +
                "            \"media\": \"知乎专栏\",\n" +
                "            \"icon\": \"https://sfile.chatglm.cn/searchImage/zhuanlan_zhihu_com_icon.jpg\"\n" +
                "        }\n" +
                "    ]\n" +
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
