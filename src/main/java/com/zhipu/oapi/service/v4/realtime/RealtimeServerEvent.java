package com.zhipu.oapi.service.v4.realtime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.zhipu.oapi.service.v4.realtime.server.*;
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
        // 服务器 conversation.created 事件在会话创建后立即返回。每个会话都会创建一个对话。
        @JsonSubTypes.Type(value = ConversationCreated.class, name = "conversation.created"),
        // 服务器 conversation.item.created 事件在对话项创建时返回。
        @JsonSubTypes.Type(value = ConversationItemCreated.class, name = "conversation.item.created"),
        // 服务器 conversation.item.deleted 事件在客户端通过 conversation.item.delete 事件删除对话中的某个项目时返回。
        @JsonSubTypes.Type(value = ConversationItemDeleted.class, name = "conversation.item.deleted"),
        // 服务器 conversation.item.input_audio_transcription.completed 事件是音频缓冲区中语音转文字的结果。
        @JsonSubTypes.Type(value = ConversationItemInputAudioTranscriptionCompleted.class, name = "conversation.item.input_audio_transcription.completed"),
        // 服务器 conversation.item.input_audio_transcription.failed 事件在输入音频转录功能已启用但用户消息的转录请求失败时返回。
        @JsonSubTypes.Type(value = ConversationItemInputAudioTranscriptionFailed.class, name = "conversation.item.input_audio_transcription.failed"),
        // 服务器 conversation.item.truncated 事件在客户端通过 conversation.item.truncate 事件截断之前的助手音频消息项时返回。
        @JsonSubTypes.Type(value = ConversationItemTruncated.class, name = "conversation.item.truncated"),
        // 服务器 error 事件在发生错误时返回，可能是客户端问题或服务器问题。
        @JsonSubTypes.Type(value = RealtimeError.class, name = "error"),
        // 服务器 heartbeat 自定义事件。
        @JsonSubTypes.Type(value = RealtimeHeartBeat.class, name = "heartbeat"),
        // 服务器 input_audio_buffer.cleared 事件在客户端通过 input_audio_buffer.clear 事件清除输入音频缓冲区时返回。
        @JsonSubTypes.Type(value = InputAudioBufferCleared.class, name = "input_audio_buffer.cleared"),
        // 服务器 input_audio_buffer.committed 事件在输入音频缓冲区被提交时返回，提交可能由客户端发起或在服务器 VAD 模式下自动完成。
        @JsonSubTypes.Type(value = InputAudioBufferCommitted.class, name = "input_audio_buffer.committed"),
        // 服务器 input_audio_buffer.speech_started 事件在服务器 VAD 模式下检测到音频缓冲区中的语音时返回。
        @JsonSubTypes.Type(value = InputAudioBufferSpeechStarted.class, name = "input_audio_buffer.speech_started"),
        // 服务器 input_audio_buffer.speech_stopped 事件在服务器 VAD 模式下检测到音频缓冲区中语音结束时返回。
        @JsonSubTypes.Type(value = InputAudioBufferSpeechStopped.class, name = "input_audio_buffer.speech_stopped"),
        // 服务器 rate_limits.updated 事件在响应开始时发出，用于指示更新后的速率限制。
        @JsonSubTypes.Type(value = RateLimitsUpdated.class, name = "rate_limits.updated"),
        // 服务器 response.audio.delta 事件在模型生成的音频更新时返回。
        @JsonSubTypes.Type(value = ResponseAudioDelta.class, name = "response.audio.delta"),
        // 服务器 response.audio.done 事件在模型生成的音频完成时返回。
        @JsonSubTypes.Type(value = ResponseAudioDone.class, name = "response.audio.done"),
        // 服务器 response.audio_transcript.delta 事件在模型生成的音频输出转录内容更新时返回。
        @JsonSubTypes.Type(value = ResponseAudioTranscriptDelta.class, name = "response.audio_transcript.delta"),
        // 服务器 response.audio_transcript.done 事件在模型生成的音频输出转录流结束时返回。
        @JsonSubTypes.Type(value = ResponseAudioTranscriptDone.class, name = "response.audio_transcript.done"),
        // 服务器 response.content_part.added 事件在助手消息项中添加新的内容部分时返回。
        @JsonSubTypes.Type(value = ResponseContentPartAdded.class, name = "response.content_part.added"),
        // 服务器 response.content_part.done 事件在内容部分流结束时返回。
        @JsonSubTypes.Type(value = ResponseContentPartDone.class, name = "response.content_part.done"),
        // 服务器 response.created 事件在创建新响应时返回。这是响应创建的第一个事件，响应处于初始的 in_progress 状态。
        @JsonSubTypes.Type(value = ResponseCreated.class, name = "response.created"),
        // 服务器 response.done 事件在响应流结束时返回。
        @JsonSubTypes.Type(value = ResponseDone.class, name = "response.done"),
        // 服务器 response.function_call_arguments.delta 事件在模型生成的函数调用参数更新时返回。
        @JsonSubTypes.Type(value = ResponseFunctionCallArgumentsDelta.class, name = "response.function_call_arguments.delta"),
        // 服务器 response.function_call_arguments.done 事件在模型生成的函数调用参数流结束时返回。
        @JsonSubTypes.Type(value = ResponseFunctionCallArgumentsDone.class, name = "response.function_call_arguments.done"),
        // 服务器 response.output_item.added 事件在响应生成期间创建新项目时返回。
        @JsonSubTypes.Type(value = ResponseOutputItemAdded.class, name = "response.output_item.added"),
        // 服务器 response.output_item.done 事件在项目流结束时返回。
        @JsonSubTypes.Type(value = ResponseOutputItemDone.class, name = "response.output_item.done"),
        // 服务器 response.text.delta 事件在模型生成的文本更新时返回。
        @JsonSubTypes.Type(value = ResponseTextDelta.class, name = "response.text.delta"),
        // 服务器 response.text.done 事件在模型生成的文本流结束时返回。
        @JsonSubTypes.Type(value = ResponseTextDone.class, name = "response.text.done"),
        // 服务器 session.created 事件是在与实时 API 建立新连接时的第一个服务器事件。此事件创建并返回一个带有默认会话配置的新会话。
        @JsonSubTypes.Type(value = SessionCreated.class, name = "session.created"),
        // 服务器 session.updated 事件在客户端更新会话时返回。如果发生错误，服务器将返回错误事件。
        @JsonSubTypes.Type(value = SessionUpdated.class, name = "session.updated")
})
// @formatter:on
public class RealtimeServerEvent {
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("client_timestamp")
    private Long clientTimestamp;

    public RealtimeServerEvent() {
        this.eventId = "";
        this.type = "";
        // 私有字段
        this.clientTimestamp = 0L;
    }
}