# 智谱大模型开放接口SDK

智谱[开放平台](http://open.bigmodel.cn/howuse/platformintroduced)大模型接口Java SDK（Big Model API SDK in
Java），让开发者更便捷的调用智谱开放API

## 简介
- <font color="red">**java sdk仍在开发测试阶段，有bug请留言联系**</font>
- 对所有接口进行了类型封装，无需查阅API文档即可完成接入

## 安装

- 运行环境：JDK1.8+
- maven坐标
```
        <dependency>
            <groupId>cn.bigmodel.openapi</groupId>
            <artifactId>oapi-java-sdk</artifactId>
            <version>release-V4-2.0.0</version>
        </dependency>
```

## 使用
- 调用流程：
    1. 使用APISecretKey创建Client
    2. 调用Client对应的成员方法
- com.zhipu.oapi.demo有完整的demo示例，请替换自己的ApiKey和ApiSecret进行测试

### 创建Client

```
    //ClientV4 client = new ClientV4.Builder("{Your ApiKey}", "Your ApiSecret")
    //            .httpTransport(new ApacheHttpClientTransport())// 传输层默认使用okhttpclient，如果需要修改位其他http client（如apache），可以在这里指定。注意apache不支持sse调用
    //            .build();
    ClientV4 client = new ClientV4.Builder("{Your ApiSecretKey}")
                .httpTransport(new ApacheHttpClientTransport())// 传输层默认使用okhttpclient，如果需要修改位其他http client（如apache），可以在这里指定。注意apache不支持sse调用
                .build();       
```

### sse调用
```
        // 建议直接查看demo包代码，这里更新可能不及时
         List<ChatMessage> messages = new ArrayList<>();
         ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "ChatGLM和你哪个更强大");
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
         System.out.println("model output:"+ JSON.toJSONString(sseModelApiResp))
```
