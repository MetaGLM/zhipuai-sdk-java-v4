package com.zhipu.oapi.service.v4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatCompletionRequest {

    /**
     * 所要调用的模型编码
     */
    private String model;


    /**
     * 调用语言模型时，将当前对话信息列表作为提示输入给模型， 按照 {"role": "user", "content": "你好"} 的json 数组形式进行传参； 可能的消息类型包括 System message、User message、Assistant message 和 Tool message。见下方 message 消息字段说明
     */
     private List<ChatMessage> messages;



    /**
     * 由用户端传参，需保证唯一性；用于区分每次请求的唯一标识，用户端不传时平台会默认生成
     */
    private String requestId;


    /**
     * do_sample 为 true 时启用采样策略，do_sample 为 false 时采样策略 temperature、top_p 将不生效
     */
    private Boolean doSample;


    /**
     * 同步调用：false，sse调用：true
     */
    private Boolean stream;


    /**
     * 采样温度，控制输出的随机性，必须为正数
     * 取值范围是：(0.0,1.0]，不能等于 0，默认值为 0.95
     * 值越大，会使输出更随机，更具创造性；值越小，输出会更加稳定或确定
     * 建议您根据应用场景调整 top_p 或 temperature 参数，但不要同时调整两个参数
     */
    private Float temperature;


    /**
     * 用温度取样的另一种方法，称为核取样
     * 取值范围是：(0.0, 1.0) 开区间，不能等于 0 或 1，默认值为 0.7
     * 模型考虑具有 top_p 概率质量 tokens 的结果
     * 例如：0.1 意味着模型解码器只考虑从前 10% 的概率的候选集中取 tokens
     * 建议您根据应用场景调整 top_p 或 temperature 参数，但不要同时调整两个参数
     */
    private Float topP;


    /**
     * 模型最大输出tokens
     */
    private Integer maxTokens;

    /**
     * 模型在遇到stop所制定的字符时将停止生成，目前仅支持单个停止词格式为
     */
    private List<String> stop;


    /**
     * 敏感词检测控制
     */
    private SensitiveWordCheckRequest sensitiveWordCheck;


    /**
     * 可供模型调用的工具列表
     * tools字段会计算 tokens ，同样受到tokens长度的限制：
     * 1. message中最后一条message的长度、system message的长度和functions字段长度之和不能超过4000 tokens。否则将无法完成对话。
     * 2. 当messages的总长度超过了tokens上限，系统会依次遗忘最早的历史会话不超过4000 tokens
     */
    private List<ChatTool> tools;


    /**
     * 指定调用一个特定函数
     */
    private  Object toolChoice;




    private String invokeMethod;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatCompletionRequestFunctionCall {
        String name;

        public static ChatCompletionRequestFunctionCall of(String name) {
            return new ChatCompletionRequestFunctionCall(name);
        }

    }


}
