package com.project.octwallet.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ParameterUtil {

    /**
     * 객체를 json string 으로 변환
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static <T> String makeJsonString(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            log.info("object {}", object);
            log.error("jsonString mapping error!!");
            throw new RuntimeException(e);
        }

        return jsonString;
    }
}
