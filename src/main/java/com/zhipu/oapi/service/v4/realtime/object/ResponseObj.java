package com.zhipu.oapi.service.v4.realtime.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.List.of;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ResponseObj {
    @JsonProperty("modalities")
    private List<String> modalities;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("voice")
    private String voice;

    @JsonProperty("output_audio_format")
    private String outputAudioFormat;

    @JsonProperty("tools")
    private List<ToolObj> tools;

    @JsonProperty("tool_choice")
    private ToolChoiceObj toolChoice;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("max_output_tokens")
    private IntOrInfObj maxOutputTokens;

    @JsonProperty("conversation")
    private String conversation;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    @JsonProperty("input")
    private List<Object> input;

    public ResponseObj() {
        this.modalities = new ArrayList<>();
        this.modalities.add("text");
        this.modalities.add("audio");
        this.instructions = "";
        this.voice = "alloy";
        this.outputAudioFormat = "pcm16";
        this.tools = new ArrayList<>();
        this.toolChoice = ToolChoiceObj.of("auto");
        this.temperature = 0.7;
        this.maxOutputTokens = IntOrInfObj.inf();
        this.conversation = "";
        this.metadata = Map.of();
        this.input = of();
    }
}
