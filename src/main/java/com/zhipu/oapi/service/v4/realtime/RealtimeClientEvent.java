package com.zhipu.oapi.service.v4.realtime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zhipu.oapi.service.v4.realtime.client.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
// @formatter:off
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true,
        defaultImpl = RealtimeServerEvent.class
)
@JsonSubTypes({
        // 用于处理客户端向对话上下文中添加新项目（消息、函数调用及其响应）的事件
        @JsonSubTypes.Type(value = ConversationItemCreate.class, name = "conversation.item.create"),
        // 用于处理从对话历史记录中删除某个项目的事件
        @JsonSubTypes.Type(value = ConversationItemDelete.class, name = "conversation.item.delete"),
        // 用于处理截断之前助手消息中的音频部分的事件
        @JsonSubTypes.Type(value = ConversationItemTruncate.class, name = "conversation.item.truncate"),
        // 用于将音频字节追加到输入音频缓冲区中的事件
        @JsonSubTypes.Type(value = InputAudioBufferAppend.class, name = "input_audio_buffer.append"),
        // 用于清除缓冲区中的音频字节的事件
        @JsonSubTypes.Type(value = InputAudioBufferClear.class, name = "input_audio_buffer.clear"),
        // 用于提交用户的输入音频缓冲区以便处理的事件
        @JsonSubTypes.Type(value = InputAudioBufferCommit.class, name = "input_audio_buffer.commit"),
        // 自定义事件
        @JsonSubTypes.Type(value = InputAudioBufferPreCommit.class, name = "input_audio_buffer.pre_commit"),
        // 自定义事件
        @JsonSubTypes.Type(value = InputVideoBufferAppend.class, name = "input_audio_buffer.append_video_frame"),
        // 用于取消正在进行中的响应处理的事件
        @JsonSubTypes.Type(value = ResponseCancel.class, name = "response.cancel"),
        // 指示服务器通过模型推理来创建响应的事件
        @JsonSubTypes.Type(value = ResponseCreate.class, name = "response.create"),
        // 用于更新会话默认配置的事件
        @JsonSubTypes.Type(value = SessionUpdate.class, name = "session.update")
})
// @formatter:on
public class RealtimeClientEvent {
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("client_timestamp")
    private Long clientTimestamp;

    public RealtimeClientEvent() {
        this.eventId = "";
        this.type = "";
        this.clientTimestamp = System.currentTimeMillis();
    }
}
