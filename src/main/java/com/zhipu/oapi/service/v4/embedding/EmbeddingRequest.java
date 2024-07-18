package com.zhipu.oapi.service.v4.embedding;

import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import com.zhipu.oapi.service.v4.file.UploadFileRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates an embedding vector representing the input text.
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmbeddingRequest extends CommonRequest  implements ClientRequest<Map<String, Object>> {

    /**
     * The name of the model to use.
     * Required if using the new v1/embeddings endpoint.
     */
    private String model;

    /**
     * Input text to get embeddings for, encoded as a string or array of tokens.
     * To get embeddings for multiple inputs in a single request, pass an array of strings or array of token arrays.
     * Each input must not exceed 2048 tokens in length.
     * <p>
     * Unless you are embedding code, we suggest replacing newlines (\n) in your input with a single space,
     * as we have observed inferior results when newlines are present.
     */

    private String input;

    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request_id", this.getRequestId());
        paramsMap.put("user_id", this.getUserId());
        paramsMap.put("input", this.getInput());
        paramsMap.put("model", this.getModel());
        if(this.getExtraJson() !=null){
            paramsMap.putAll(this.getExtraJson());
        }
        return paramsMap;
    }
}
