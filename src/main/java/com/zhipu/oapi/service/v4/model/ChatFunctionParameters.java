package com.zhipu.oapi.service.v4.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatFunctionParameters {


    private String type;

    private Object properties;

    private List<String> required;

}
