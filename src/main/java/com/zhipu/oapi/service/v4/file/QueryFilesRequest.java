package com.zhipu.oapi.service.v4.file;


import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 查询文件列表
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryFilesRequest extends CommonRequest {


    private String purpose;

    private Integer limit;

    private String after;

    private String order;


}
