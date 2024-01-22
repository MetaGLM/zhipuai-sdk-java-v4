package com.zhipu.oapi.service.v4.embedding;

import com.zhipu.oapi.service.v4.model.Usage;
import lombok.Data;

import java.util.List;

/**
 * An object containing a response from the answer api
 */
@Data
public class EmbeddingResult {

    /**
     * The GPTmodel used for generating embeddings
     */
    String model;

    /**
     * The type of object returned, should be "list"
     */
    String object;

    /**
     * A list of the calculated embeddings
     */
    List<Embedding> data;

    /**
     * The API usage for this request
     */
    Usage usage;
}
