package com.zhipu.oapi.service.v4.model;

import java.nio.file.LinkOption;
import java.util.Map;

/**
 * Class that accumulates chat messages and provides utility methods for
 * handling message chunks and function calls within a chat stream. This
 * class is immutable.
 *
 * @author [Your Name]
 */
public class ChatMessageAccumulator {

    private final ChatMessage accumulatedMessage;

    private final Delta delta;

    private final Choice choice;

    private final Usage usage;

    private final Long created;

    private final String id;



    public ChatMessageAccumulator(Delta delta, ChatMessage accumulatedMessage, Choice choice, Usage usage, Long created,String id) {
        this.delta = delta;
        this.accumulatedMessage = accumulatedMessage;
        this.choice = choice;
        this.usage = usage;
        this.created = created;
        this.id = id;
    }


    public Delta getDelta() {
        return delta;
    }


    public ChatMessage getAccumulatedMessage() {
        return accumulatedMessage;
    }


    public Choice getChoice() {
        return choice;
    }

    public Usage getUsage() {
        return usage;
    }

    public String getId() { return id; }

    public Long getCreated(){return created;}


    public ChatFunctionCall getAccumulatedChatFunctionCall() {
        return getAccumulatedMessage().getTool_calls().get(0).getFunction();
    }


}
