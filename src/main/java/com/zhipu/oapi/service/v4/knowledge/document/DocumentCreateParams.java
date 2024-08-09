package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import com.zhipu.oapi.service.v4.file.UploadFileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * This class represents the parameters required for file creation and upload.
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentCreateParams extends CommonRequest implements ClientRequest<DocumentCreateParams> {

    /**
     * local file
     */
    private String filePath;

    /**
     * File and upload_detail are mutually exclusive and one is required.
     */
    @JsonProperty("upload_detail")
    private List<UploadDetail> uploadDetail;

    /**
     * The purpose of uploading the file.
     * Supported values: "fine-tune", "retrieval", "batch".
     * <ul>
     * <li>For "retrieval", the supported file types are Doc, Docx, PDF, Xlsx, and URL, and the maximum file size is 5MB.</li>
     * <li>For "fine-tune", the supported file type is .jsonl, and the maximum file size is 100MB.</li>
     * </ul>
     */
    @JsonProperty("purpose")
    private String purpose;

    /**
     * Custom separator list.
     * When the purpose is "retrieval" and the file type is pdf, url, or docx, upload with the default slicing rule as `\n`.
     */
    @JsonProperty("custom_separator")
    private List<String> customSeparator;

    /**
     * Knowledge Base ID.
     * Required when the file upload purpose is "retrieval".
     */
    @JsonProperty("knowledge_id")
    private String knowledgeId;

    /**
     * Sentence size.
     * Required when the file upload purpose is "retrieval".
     */
    @JsonProperty("sentence_size")
    private Integer sentenceSize;

    @Override
    public DocumentCreateParams getOptions() {
        return this;
    }
}
