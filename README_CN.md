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

### Maven 依赖
在您的 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>cn.bigmodel.openapi</groupId>
    <artifactId>oapi-java-sdk</artifactId>
    <version>release-V4-2.3.4</version>
</dependency>
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
private static final ClientV4 client = new ClientV4.Builder(API_SECRET_KEY) 
        .enableTokenCache()
        .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
        .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
        .build();
```

## 💡 使用示例

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
