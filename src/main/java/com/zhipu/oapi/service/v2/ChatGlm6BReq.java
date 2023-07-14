package com.zhipu.oapi.service.v2;

import java.util.List;

public class ChatGlm6BReq {
    private String prompt;
    private float temperature;
    private float top_p;
    private List<QA> history;
    private String requestTaskNo;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getTop_p() {
        return top_p;
    }

    public void setTop_p(float top_p) {
        this.top_p = top_p;
    }

    public List<QA> getHistory() {
        return history;
    }

    public void setHistory(List<QA> history) {
        this.history = history;
    }

    public String getRequestTaskNo() {
        return requestTaskNo;
    }

    public void setRequestTaskNo(String requestTaskNo) {
        this.requestTaskNo = requestTaskNo;
    }

    public static class QA {
        private String query;
        private String answer;

        public QA() {
        }

        public QA(String query, String answer) {
            this.query = query;
            this.answer = answer;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
