package com.zhipu.oapi.service.v4.embedding;

import lombok.Data;

import java.util.List;

/**
 * Represents an embedding returned by the embedding api
 */
@Data
public class Embedding {

    /**
     * The type of object returned, should be "embedding"
     */
    String object;

    /**
     * The embedding vector
     */
    List<Double> embedding;

    /**
     * The position of this embedding in the list
     */
    Integer index;
}
