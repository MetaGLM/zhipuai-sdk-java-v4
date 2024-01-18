package com.zhipu.oapi.service.v4.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delta {

    private String role;

    private String content;
}
