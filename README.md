# 智谱大模型开放接口SDK

智谱[开放平台](http://open.bigmodel.cn/howuse/platformintroduced)大模型接口Java SDK（Big Model API SDK in
Java），让开发者更便捷的调用智谱开放API

## 简介

- 对所有接口进行了类型封装，无需查阅API文档即可完成接入
- 初始化client并调用成员函数，无需关注http调用过程的各种细节，所见即所得
- 默认缓存token
- 支持主流http client实现，已支持apache httpclient、okhttpclient

## 安装

- 运行环境：JDK1.8+
- maven坐标

```
TODO
```

## 使用

- 调用流程：
    1. 使用APIKey和PubKey创建Client
    2. 调用Client对应的成员方法
- com.zhipu.oapi.demo有完整的demo示例，请替换自己的ApiKey和ApiSecret进行测试

### 创建Client

```
    ClientV3 client = new ClientV3.Builder("{Your ApiKey}", "Your ApiSecret")
                .httpTransport(new ApacheHttpClientTransport())// 传输层默认使用okhttpclient，如果需要修改位其他http client（如apache），可以在这里指定。注意apache不支持sse调用
                .build();
```        

### 异步调用

```
    # 1. 调用模型接口（同步响应只返回taskOrderNo）
    Client client = builder.build();    
    ModelApiRequest modelApiRequest = new ModelApiRequest();
    // 指定模型
    modelApiRequest.setModelId(Constants.ModelTest);
    // 指定模型访问方式
    modelApiRequest.setInvokeMethod(Constants.invokeMethodAsync);
    // 指定输入
    modelApiRequest.setPrompt("ChatGPT和你哪个更强大");
    // 设置会话历史
    ModelApiRequest.QA history1 = new ModelApiRequest.QA("你好", "我是人工智能助手");
    ModelApiRequest.QA history2 = new ModelApiRequest.QA("你叫什么名字", "我叫chatGLM");
    List<ModelApiRequest.QA> history = new ArrayList<>();
    history.add(history1);
    history.add(history2);
    modelApiRequest.setHistory(history);
    ModelApiResponse modelApiResp = client.invokeModelApi(modelApiRequest);
    
    # 2. 查询指定taskOrderNo的结果，根据模型和输入内容的不同，通常需要等待10-300s模型才能完成处理
    QueryModelResultRequest queryModelResultRequest = new QueryModelResultRequest();
    queryModelResultRequest.setTaskOrderNo(taskOrderNo);
    QueryModelResultResponse queryResultResp = client.queryModelResult(request);
``` 

### sse调用
```
    ModelApiRequest modelApiRequest = new ModelApiRequest();
    modelApiRequest.setModelId(Constants.ModelTest);
    modelApiRequest.setInvokeMethod(Constants.invokeMethodSse);
    // 可自定义sse listener
    modelApiRequest.setSseListener(new StandardEventSourceListener());
    modelApiRequest.setPrompt("ChatGPT和你哪个更强大")
    ModelApiRequest.QA history1 = new ModelApiRequest.QA("你好", "我是人工智能助手");
    ModelApiRequest.QA history2 = new ModelApiRequest.QA("你叫什么名字", "我叫chatGLM");
    List<ModelApiRequest.QA> history = new ArrayList<>();
    history.add(history1);
    history.add(history2);
    modelApiRequest.setHistory(history);
```