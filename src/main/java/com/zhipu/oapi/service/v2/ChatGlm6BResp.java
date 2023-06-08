package com.zhipu.oapi.service.v2;

import com.zhipu.oapi.service.TaskStatus;

public class ChatGlm6BResp {
    private int code;
    private String msg;
    private boolean success;

    private ChatGlm6BData data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        if (this.code == 200) {
            setSuccess(true);
        } else {
            setSuccess(false);
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ChatGlm6BData getData() {
        return data;
    }

    public void setData(ChatGlm6BData data) {
        this.data = data;
    }

    public static final class ChatGlm6BData {
        private String prompt;
        private Object outputText;
        private Integer inputTokenNum;
        private Integer outputTokenNum;
        private Integer totalTokenNum;
        private String requestTaskNo;
        private String taskOrderNo;
        private TaskStatus taskStatus;

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public Object getOutputText() {
            return outputText;
        }

        public void setOutputText(Object outputText) {
            this.outputText = outputText;
        }

        public Integer getInputTokenNum() {
            return inputTokenNum;
        }

        public void setInputTokenNum(Integer inputTokenNum) {
            this.inputTokenNum = inputTokenNum;
        }

        public Integer getOutputTokenNum() {
            return outputTokenNum;
        }

        public void setOutputTokenNum(Integer outputTokenNum) {
            this.outputTokenNum = outputTokenNum;
        }

        public Integer getTotalTokenNum() {
            return totalTokenNum;
        }

        public void setTotalTokenNum(Integer totalTokenNum) {
            this.totalTokenNum = totalTokenNum;
        }

        public String getRequestTaskNo() {
            return requestTaskNo;
        }

        public void setRequestTaskNo(String requestTaskNo) {
            this.requestTaskNo = requestTaskNo;
        }

        public String getTaskOrderNo() {
            return taskOrderNo;
        }

        public void setTaskOrderNo(String taskOrderNo) {
            this.taskOrderNo = taskOrderNo;
        }

        public TaskStatus getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(TaskStatus taskStatus) {
            this.taskStatus = taskStatus;
        }
    }
}
