package com.zhipu.oapi.service.v4.deserialize.assistant.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.zhipu.oapi.service.v4.assistant.message.MessageContent;
import com.zhipu.oapi.service.v4.assistant.message.TextContentBlock;
import com.zhipu.oapi.service.v4.assistant.message.ToolsDeltaBlock;
import com.zhipu.oapi.service.v4.assistant.message.tools.ToolsType;
import com.zhipu.oapi.service.v4.deserialize.JsonTypeField;
import com.zhipu.oapi.service.v4.deserialize.JsonTypeMapping;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

public class MessageContentDeserializer extends JsonDeserializer<MessageContent> {


    @Override
    public MessageContent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        // 获取 MessageContent 类的 JsonTypeMapping 注解
        JsonTypeMapping mapping = MessageContent.class.getAnnotation(JsonTypeMapping.class);
        if (mapping == null) {
            throw new IllegalStateException("Missing JsonTypeMapping annotation on MessageContent class");
        }

        // 遍历注解中定义的类，根据注解或静态方法值来判断合适的类
        for (Class<?> clazz : mapping.value()) {
            JsonTypeField typeField = clazz.getAnnotation(JsonTypeField.class);

            if (typeField != null && node.has(typeField.value())) {
                try {
                    // 创建类的实例
                    Object obj = clazz.getDeclaredConstructor().newInstance();

                    // 使用反射手动设置字段的值
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        //field.getName() 获取上面的JsonProperty注解的值
                        JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                        String name = null;
                        if (annotation ==null ){
                            name = field.getName();
                        }else {
                            name = annotation.value();
                        }
                        if (node.has(name)) {
                            // 根据字段的类型设置值，这里假设字段为基本类型或字符串
                            if (field.getType().equals(String.class)) {
                                field.set(obj, node.get(name).asText());
                            } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                                field.set(obj, node.get(name).asInt());
                            } else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                                field.set(obj, node.get(name).asBoolean());
                            } else if (List.class.isAssignableFrom(field.getType())) {
                                // 检查泛型类型是否为 ToolsType
                                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                                Class<?> listGenericType = (Class<?>) listType.getActualTypeArguments()[0];

                                if (listGenericType.equals(ToolsType.class)) {
                                    // 反序列化为 List<ToolsType>
                                    List<ToolsType> list = new ObjectMapper().readerForListOf(ToolsType.class)
                                            .readValue(node.get(name));
                                    field.set(obj, list);
                                } else {
                                    throw new IllegalArgumentException("Unsupported generic list type: " + listGenericType);
                                }
                            } else {
                                throw new IllegalArgumentException("Unsupported field type: " + field.getType());
                            }

                        }
                    }

                    return (MessageContent) obj;
                } catch (Exception e) {
                    throw new RuntimeException("Error while creating instance of " + clazz.getName(), e);
                }
            }
        }

        // 创建类的实例
        try {
            MessageContent obj = TextContentBlock.class.getDeclaredConstructor().newInstance();

            // 使用反射手动设置字段的值
            for (Field field : TextContentBlock.class.getDeclaredFields()) {
                field.setAccessible(true);
                //field.getName() 获取上面的JsonProperty注解的值
                JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                String name = null;
                if (annotation ==null ){
                    name = field.getName();
                }else {
                    name = annotation.value();
                }
                if (node.has(name)) {
                    // 根据字段的类型设置值，这里假设字段为基本类型或字符串
                    if (field.getType().equals(String.class)) {
                        field.set(obj, node.get(name).asText());
                    } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                        field.set(obj, node.get(name).asInt());
                    } else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                        field.set(obj, node.get(name).asBoolean());


                    } else {
                        // 对于其他类型，直接使用 ObjectMapper 进行转换
                        Object o = new ObjectMapper().treeToValue(node.get(name), field.getType());
                        field.set(obj, o);
                    }
                }
            }

            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating instance of " + TextContentBlock.class.getName(), e);
        }

    }

}
