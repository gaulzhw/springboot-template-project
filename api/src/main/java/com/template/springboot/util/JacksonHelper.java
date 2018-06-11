package com.template.springboot.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;

@Slf4j
public class JacksonHelper {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 将对象转化为json
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error(String.format("obj=[%s]", obj.toString()), e);
        }
        return null;
    }

    /**
     * 将json转化为对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error(String.format("json=[%s]", json), e);
        }
        return null;
    }

    /**
     * 将json对象转化为集合类型
     */
    @SuppressWarnings("rawtypes")
    public static <T> Collection<T> fromJson(String json, Class<? extends Collection> collectionClazz, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            Collection<T> collection = mapper.readValue(json, getCollectionType(collectionClazz, clazz));
            return collection;
        } catch (IOException e) {
            log.error(String.format("json=[%s]", json), e);
        }
        return null;
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
