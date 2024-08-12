package com.zhipu.oapi.service.v4.assistant.message.tools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.zhipu.oapi.service.v4.assistant.message.TextContentBlock;
import com.zhipu.oapi.service.v4.assistant.message.ToolsDeltaBlock;
import com.zhipu.oapi.service.v4.assistant.message.tools.code_interpreter.CodeInterpreterToolBlock;
import com.zhipu.oapi.service.v4.assistant.message.tools.drawing_tool.DrawingToolBlock;
import com.zhipu.oapi.service.v4.assistant.message.tools.function.FunctionToolBlock;
import com.zhipu.oapi.service.v4.assistant.message.tools.retrieval.RetrievalToolBlock;
import com.zhipu.oapi.service.v4.assistant.message.tools.retrieval.RetrievalToolOutput;
import com.zhipu.oapi.service.v4.assistant.message.tools.web_browser.WebBrowserToolBlock;
import com.zhipu.oapi.service.v4.deserialize.JsonTypeMapping;
import com.zhipu.oapi.service.v4.deserialize.assistant.message.MessageContentDeserializer;
import com.zhipu.oapi.service.v4.deserialize.assistant.message.tools.ToolsTypeDeserializer;

@JsonTypeMapping({
        WebBrowserToolBlock.class,
        RetrievalToolBlock.class,
        FunctionToolBlock.class,
        DrawingToolBlock.class,
        CodeInterpreterToolBlock.class,
})
@JsonDeserialize(using = ToolsTypeDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ToolsType {
}
