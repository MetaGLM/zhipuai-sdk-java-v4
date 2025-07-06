# ZhipuAI Open Platform Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/cn.bigmodel.openapi/oapi-java-sdk.svg)](https://search.maven.org/artifact/cn.bigmodel.openapi/oapi-java-sdk)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/java-1.8%2B-orange.svg)](https://www.oracle.com/java/)

[中文文档](README_CN.md)

The official Java SDK for [ZhipuAI Open Platform](http://open.bigmodel.cn/howuse/platformintroduced) Big Model API, enabling developers to easily integrate ZhipuAI's powerful AI capabilities into their Java applications.

## ✨ Features

- 🚀 **Type-safe API**: All interfaces are fully type-encapsulated, no need to consult API documentation
- 🔧 **Easy Integration**: Simple and intuitive API design for quick integration
- ⚡ **High Performance**: Built with modern Java libraries for optimal performance
- 🛡️ **Secure**: Built-in authentication and token management
- 📦 **Lightweight**: Minimal dependencies for easy project integration

## 📦 Installation

### Requirements
- Java 1.8 or higher
- Maven or Gradle
- Not supported on Android platform

### Maven
Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>cn.bigmodel.openapi</groupId>
    <artifactId>oapi-java-sdk</artifactId>
    <version>release-V4-2.3.4</version>
</dependency>
```

### Gradle
Add the following dependency to your `build.gradle`:

```groovy
dependencies {
    implementation 'cn.bigmodel.openapi:oapi-java-sdk:release-V4-2.3.4'
}
```

Or `build.gradle.kts`:

``kotlin
dependencies {
    implementation("cn.bigmodel.openapi:oapi-java-sdk:release-V4-2.3.4")
}
```

### 📋 Dependencies

This SDK uses the following core dependencies:

| Library | Version |
|---------|----------|
| OkHttp | 3.14.9 |
| Java JWT | 4.2.2 |
| Jackson | 2.11.3 |
| Retrofit2 | 2.9.0 |

## 🚀 Quick Start

### Basic Usage

1. **Create a Client** with your API key
2. **Call the desired API methods**

For complete examples, see [V4Test.java](src/test/java/com/zhipu/oapi/V4Test.java). Remember to replace the API key with your own.

### Client Configuration

The SDK provides a flexible `ClientV4` builder for customizing your client:

**Configuration Options:**
- `enableTokenCache()`: Enable token caching to reduce token requests
- `networkConfig()`: Configure connection, read, write timeouts, and ping intervals
- `connectionPool()`: Set up connection pooling

```java
String API_SECRET_KEY = "your_api_key_here";
private static final ClientV4 client = new ClientV4.Builder(API_SECRET_KEY) 
        .enableTokenCache()
        .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
        .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
        .build();
```

## 💡 Examples

### Spring Boot Integration

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

## 📈 Release Notes

For detailed release notes and version history, please see [Release-Note.md](Release-Note.md).

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

We welcome contributions! Please feel free to submit a Pull Request.

## 📞 Support

For questions and support, please visit the [ZhipuAI Open Platform](http://open.bigmodel.cn/) or check our documentation.
