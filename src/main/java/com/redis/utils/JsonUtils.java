package com.redis.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

/**
 * @Author 杨阳
 * @Date 2016/11/29 - 17:00
 */
@Data
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static  ObjectMapper getMapper() {
        return mapper;
    }

    public static String writeToString(Object value){
        try {
            return getMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T readValue(String str, Class<T> type) {
        try {
            return getMapper().readValue(str, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
