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
            <version>release-V4-2.0.3</version>
        </dependency>
```
### 依赖信息

```text
okhttp_3.14.9
java-jwt_4.2.2
jackson_2.11.3
retrofit2_2.9.0 
```
## 使用
- 调用流程：
    1. 使用APIKey创建Client
    2. 调用Client对应的成员方法
- [V4Test.java](src/test/java/com/zhipu/oapi/V4Test.java)有完整的demo示例，请替换自己的ApiKey进行测试


> SDK提供了ClientV4的构造器，此方法可以在创建Client时进行配置，可配置项如下：


- devMode：是否开启开发模式，开发模式下会打印请求和响应的详细信息
- enableTokenCache：是否开启token缓存，开启后会缓存token，减少token请求次数
- networkConfig：设置连接超时、读取超时、写入超时、ping间隔、ping超时时间
- connectionPool：设置连接池

```
boolean devMode = true;
String API_SECRET_KEY = "your api"
private static final ClientV4 client = new ClientV4.Builder(API_SECRET_KEY)
        .devMode(devMode)
        .enableTokenCache()
        .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
        .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
        .build();
 
```




## 升级内容


#### release-V4-2.0.3
- 增加拓展报文序列化工具类
- 增加测试样例
- 修改为使用api key鉴权
- 统一通信客户端
- 删除部分序列化框架依赖
- 增加批处理API

#### release-V4-2.0.2
- readTimeOut时间设置为300s
- 修改测试demo中apiKey命名


#### release-V4-2.0.1
- 统一client4构造apikey入参
- 延长token过期时间