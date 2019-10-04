package com.wechatapp.eticket.core.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;

import lombok.SneakyThrows;

/**
 * Json转换工具
 *
 * @author virgo.zx
 */
public class JsonUtils {

    private JsonUtils() {
    }

    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    @SneakyThrows
    public static <T> T readValue(String content, Class<T> type) {
    	return objectMapper.readValue(content, type);
    }

    @SneakyThrows
    public static String writeValueAsString(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
