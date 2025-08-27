
Z.AI 和 智谱AI 的 [全新 Java SDK](https://github.com/THUDM/z-ai-sdk-java) 已经发布：[z-ai-sdk-java](https://github.com/THUDM/z-ai-sdk-java)！推荐使用此 SDK，以获得更好、更快的长期支持。

---

# 智谱AI开放平台 Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/cn.bigmodel.openapi/oapi-java-sdk.svg)](https://search.maven.org/artifact/cn.bigmodel.openapi/oapi-java-sdk)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/java-1.8%2B-orange.svg)](https://www.oracle.com/java/)

[English Readme](README.md)

[智谱AI开放平台](http://open.bigmodel.cn/howuse/platformintroduced)官方 Java SDK，帮助开发者快速集成智谱AI强大的人工智能能力到Java应用中。

## ✨ 特性

- 🚀 **类型安全**: 所有接口完全类型封装，无需查阅API文档即可完成接入
- 🔧 **简单易用**: 简洁直观的API设计，快速上手
- ⚡ **高性能**: 基于现代Java库构建，性能优异
- 🛡️ **安全可靠**: 内置身份验证和令牌管理
- 📦 **轻量级**: 最小化依赖，易于项目集成

## 📦 安装

### 环境要求
- Java 1.8 或更高版本
- Maven 或 Gradle
- 尚不支持在 Android 平台运行

### Maven 依赖
在您的 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>cn.bigmodel.openapi</groupId>
    <artifactId>oapi-java-sdk</artifactId>
    <version>release-V4-2.3.4</version>
</dependency>
```

### Gradle 依赖
在您的 `build.gradle` 中添加以下依赖（适用于 Groovy DSL）：

```groovy
dependencies {
    implementation 'cn.bigmodel.openapi:oapi-java-sdk:release-V4-2.3.4'
}
```

或 `build.gradle.kts`（适用于 Kotlin DSL）：

```kotlin
dependencies {
    implementation("cn.bigmodel.openapi:oapi-java-sdk:release-V4-2.3.4")
}
```

### 📋 核心依赖

本SDK使用以下核心依赖库：

| 依赖库 | 版本 |
|--------|------|
| OkHttp | 3.14.9 |
| Java JWT | 4.2.2 |
| Jackson | 2.11.3 |
| Retrofit2 | 2.9.0 |

## 🚀 快速开始

### 基本用法

1. **使用API密钥创建客户端**
2. **调用相应的API方法**

完整示例请参考 [V4Test.java](src/test/java/com/zhipu/oapi/V4Test.java)，记得替换为您自己的API密钥。

### 客户端配置

SDK提供了灵活的 `ClientV4` 构建器来自定义您的客户端：

**配置选项：**
- `enableTokenCache()`: 启用令牌缓存，减少令牌请求次数
- `networkConfig()`: 配置连接、读取、写入超时时间和ping间隔
- `connectionPool()`: 设置连接池

```java
String API_SECRET_KEY = "your_api_key_here";
ClientV4 client = new ClientV4.Builder(API_SECRET_KEY)
        .enableTokenCache()
        .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
        .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
        .build();
```

## 💡 使用示例

### 对话模型调用

#### 流式调用（SSE）

- **基础对话**

```java
List<ChatMessage> messages = new ArrayList<>();
ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "智谱AI和ChatGLM是什么关系？");
messages.add(chatMessage);
String requestId = String.format("your-request-id-%d", System.currentTimeMillis());
ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
        .model(Constants.ModelChatGLM4)
        .stream(Boolean.TRUE)
        .messages(messages)
        .requestId(requestId)
        .build();
ModelApiResponse sseModelApiResp = client.invokeModelApi(chatCompletionRequest);
if (sseModelApiResp.isSuccess()) {
    AtomicBoolean isFirst = new AtomicBoolean(true);
    ChatMessageAccumulator chatMessageAccumulator = mapStreamToAccumulator(sseModelApiResp.getFlowable())
            .doOnNext(accumulator -> {
                // 处理流式返回结果
                System.out.println("accumulator: " + accumulator);
            })
            .doOnComplete(System.out::println)
            .lastElement()
            .blockingGet();
}
```

- **Function-Calling**

```java
List<ChatMessage> messages = new ArrayList<>();
ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "从成都到北京的机票多少钱？");
messages.add(chatMessage);
String requestId = String.format("your-request-id-%d", System.currentTimeMillis());
// 函数定义
List<ChatTool> chatToolList = new ArrayList<>();
ChatTool chatTool = new ChatTool();
chatTool.setType(ChatToolType.FUNCTION.value());
ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
chatFunctionParameters.setType("object");
Map<String, Object> properties = new HashMap<>();
properties.put("departure", new HashMap<String, Object>() {{
    put("type", "string");
    put("description", "出发地");
}});
properties.put("destination", new HashMap<String, Object>() {{
    put("type", "string");
    put("description", "目的地");
}});
chatFunctionParameters.setProperties(properties);
ChatFunction chatFunction = ChatFunction.builder()
        .name("query_flight_prices")
        .description("查询航班价格")
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
// 处理返回结果
```

#### 同步调用

- **基础对话**

```java
List<ChatMessage> messages = new ArrayList<>();
ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "智谱AI和ChatGLM是什么关系？");
messages.add(chatMessage);
String requestId = String.format("your-request-id-%d", System.currentTimeMillis());
ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
        .model(Constants.ModelChatGLM4)
        .stream(Boolean.FALSE)
        .invokeMethod(Constants.invokeMethod)
        .messages(messages)
        .requestId(requestId)
        .build();
ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
System.out.println("model output:" + new ObjectMapper().writeValueAsString(invokeModelApiResp));
```

- **Function-Calling**

```java
List<ChatMessage> messages = new ArrayList<>();
ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "你能做什么？");
messages.add(chatMessage);
String requestId = String.format("your-request-id-%d", System.currentTimeMillis());
// 函数定义... (参考流式Function-Calling)
List<ChatTool> chatToolList = new ArrayList<>();
// ... 添加Function和WebSearch工具
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
```

#### 异步调用

```java
// 1. 发起异步任务
List<ChatMessage> messages = new ArrayList<>();
ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "智谱AI和ChatGLM是什么关系？");
messages.add(chatMessage);
ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
        .model(Constants.ModelChatGLM4)
        .stream(Boolean.FALSE)
        .invokeMethod(Constants.invokeMethodAsync)
        .messages(messages)
        .build();
ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
String taskId = invokeModelApiResp.getData().getTaskId();

// 2. 根据taskId查询结果
QueryModelResultRequest request = new QueryModelResultRequest();
request.setTaskId(taskId);
QueryModelResultResponse queryResultResp = client.queryModelResult(request);
```

### 角色扮演

```java
List<ChatMessage> messages = new ArrayList<>();
ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "你最近过得怎么样？");
messages.add(chatMessage);

ChatMeta meta = new ChatMeta();
meta.setUser_info("我是一名电影导演，擅长拍摄音乐主题的电影。");
meta.setBot_info("你是一位国内当红的女歌手、演员，拥有出色的音乐才华。");
meta.setBot_name("苏梦远");
meta.setUser_name("陆星辰");

ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
        .model(Constants.ModelCharGLM3)
        .stream(Boolean.FALSE)
        .invokeMethod(Constants.invokeMethod)
        .messages(messages)
        .meta(meta)
        .build();
ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
```

### 图像生成

```java
CreateImageRequest createImageRequest = new CreateImageRequest();
createImageRequest.setModel(Constants.ModelCogView);
createImageRequest.setPrompt("一个充满未来感的云数据中心");
ImageApiResponse imageApiResponse = client.createImage(createImageRequest);
```

### 向量模型

```java
EmbeddingRequest embeddingRequest = new EmbeddingRequest();
embeddingRequest.setInput("hello world");
embeddingRequest.setModel(Constants.ModelEmbedding2);
EmbeddingApiResponse apiResponse = client.invokeEmbeddingsApi(embeddingRequest);
```

### 微调

#### 创建微调任务

```java
FineTuningJobRequest request = new FineTuningJobRequest();
request.setModel("chatglm3-6b");
request.setTraining_file("your-file-id");
CreateFineTuningJobApiResponse createFineTuningJobApiResponse = client.createFineTuningJob(request);
```

#### 查询微调任务

```java
QueryFineTuningJobRequest queryFineTuningJobRequest = new QueryFineTuningJobRequest();
queryFineTuningJobRequest.setJobId("your-job-id");
QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = client.retrieveFineTuningJobs(queryFineTuningJobRequest);
```

#### 查询个人微调任务

```java
QueryPersonalFineTuningJobRequest queryPersonalFineTuningJobRequest = new QueryPersonalFineTuningJobRequest();
queryPersonalFineTuningJobRequest.setLimit(10);
QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobApiResponse = client.queryPersonalFineTuningJobs(queryPersonalFineTuningJobRequest);
```

#### 查询微调任务事件

```java
QueryFineTuningJobRequest queryFineTuningJobRequest = new QueryFineTuningJobRequest();
queryFineTuningJobRequest.setJobId("your-job-id");
QueryFineTuningEventApiResponse queryFineTuningEventApiResponse = client.queryFineTuningJobsEvents(queryFineTuningJobRequest);
```

#### 取消微调任务

```java
FineTuningJobIdRequest request = FineTuningJobIdRequest.builder().jobId("your-job-id").build();
QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = client.cancelFineTuningJob(request);
```

#### 删除微调模型

```java
FineTuningJobModelRequest request = FineTuningJobModelRequest.builder().fineTunedModel("your-fine-tuned-model").build();
FineTunedModelsStatusResponse fineTunedModelsStatusResponse = client.deleteFineTuningModel(request);
```

### 批处理

#### 创建批处理任务

```java
BatchCreateParams batchCreateParams = new BatchCreateParams(
        "24h",
        "/v4/chat/completions",
        "your-file-id",
        new HashMap<String, String>() {{
            put("model", "glm-4");
        }}
);
BatchResponse batchResponse = client.batchesCreate(batchCreateParams);
```

#### 查询批处理任务

```java
BatchResponse batchResponse = client.batchesRetrieve("your-batch-id");
```

#### 查询批处理任务列表

```java
QueryBatchRequest queryBatchRequest = new QueryBatchRequest();
queryBatchRequest.setLimit(10);
QueryBatchResponse queryBatchResponse = client.batchesList(queryBatchRequest);
```

#### 取消批处理任务

```java
BatchResponse batchResponse = client.batchesCancel("your-batch-id");
```

### Spring Boot 集成

```java
package com.zhipu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wd.common.core.domain.R;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import com.zhipu.oapi.service.v4.model.ModelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

  private final static Logger logger = LoggerFactory.getLogger(TestController.class);
  private static final String API_SECRET_KEY = Constants.getApiKey();

  private static final ClientV4 client = new ClientV4.Builder(API_SECRET_KEY)
          .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
          .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
          .build();
  private static final ObjectMapper mapper = MessageDeserializeFactory.defaultObjectMapper();


  @RequestMapping("/test")
  public R<ModelData> test(@RequestBody ChatCompletionRequest chatCompletionRequest) {
    ModelApiResponse sseModelApiResp = client.invokeModelApi(chatCompletionRequest);

    return R.ok(sseModelApiResp.getData());
  }
}

```

## 📈 版本更新

详细的版本更新记录和历史信息，请查看 [Release-Note.md](Release-Note.md)。

## 📄 许可证

本项目基于 MIT 许可证开源 - 详情请查看 [LICENSE](LICENSE) 文件。

## 🤝 贡献

欢迎贡献代码！请随时提交 Pull Request。

## 📞 支持

如有问题和技术支持，请访问 [智谱AI开放平台](http://open.bigmodel.cn/) 或查看我们的文档。
