package com.zhipu.oapi.service.v4.file;

import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Create file upload request
 *
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UploadFileRequest extends CommonRequest implements ClientRequest<UploadFileRequest> {
    /**
     * The purpose of the file
     */
    private String purpose;
    /**
     * local file
     */
    private String filePath;

    @Override
    public UploadFileRequest getOptions() {
        return this;
    }
}
