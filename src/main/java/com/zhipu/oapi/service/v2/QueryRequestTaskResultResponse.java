package com.zhipu.oapi.service.v2;

import com.zhipu.oapi.service.TaskStatus;

public class QueryRequestTaskResultResponse {

    private int code;
    private String msg;
    private boolean success;
    private QueryRequestTaskResultData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public QueryRequestTaskResultData getData() {
        return data;
    }

    public void setData(QueryRequestTaskResultData data) {
        this.data = data;
    }

    public static class QueryRequestTaskResultData {
        private String prompt;
        private String outputText;
        private int inputTokenNum;
        private int outputTokenNum;
        private int totalTokenNum;
        private String requestTaskNo;
        private String taskOrderNo;
        private TaskStatus taskStatus;

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getOutputText() {
            return outputText;
        }

        public void setOutputText(String outputText) {
            this.outputText = outputText;
        }

        public int getInputTokenNum() {
            return inputTokenNum;
        }

        public void setInputTokenNum(int inputTokenNum) {
            this.inputTokenNum = inputTokenNum;
        }

        public int getOutputTokenNum() {
            return outputTokenNum;
        }

        public void setOutputTokenNum(int outputTokenNum) {
            this.outputTokenNum = outputTokenNum;
        }

        public int getTotalTokenNum() {
            return totalTokenNum;
        }

        public void setTotalTokenNum(int totalTokenNum) {
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
