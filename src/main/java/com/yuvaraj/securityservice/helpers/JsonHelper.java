package com.yuvaraj.securityservice.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.securityservice.models.DefaultToken;

public class JsonHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static Object toJson(Object object) {
        try{
            return objectMapper.valueToTree(object);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
