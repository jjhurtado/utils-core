package com.jhw.utils.jackson;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jhw.utils.jackson.serializer_deserializer.date.DateJsonDeserializer;
import com.jhw.utils.jackson.serializer_deserializer.date.DateJsonSerializer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class JACKSON {

    private static ObjectMapper om;

    public static String toString(Object value) throws JsonProcessingException {
        if (om == null) {
            initObjectMapper();
        }
        return om.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    public static void write(File resultFile, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        if (om == null) {
            initObjectMapper();
        }
        om.writerWithDefaultPrettyPrinter().writeValue(resultFile, value);
    }

    public static <T extends Object> T read(File src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        if (om == null) {
            initObjectMapper();
        }
        return om.readValue(src, valueType);
    }

    public static <T extends Object> T read(File src, TypeReference<T> valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        if (om == null) {
            initObjectMapper();
        }
        return om.readValue(src, valueTypeRef);
    }

    public static <T extends Object> T read(File src, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        if (om == null) {
            initObjectMapper();
        }
        return om.readValue(src, javaType);
    }

    public static <T extends Object> T read(String src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        if (om == null) {
            initObjectMapper();
        }
        return om.readValue(src, valueType);
    }

    public static <T extends Object> T read(String src, TypeReference<T> valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        if (om == null) {
            initObjectMapper();
        }
        return om.readValue(src, valueTypeRef);
    }

    public static <T extends Object> T read(String src, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        if (om == null) {
            initObjectMapper();
        }
        return om.readValue(src, javaType);
    }

    private static void initObjectMapper() {
        om = new ObjectMapper();

        SimpleModule dateModule = new SimpleModule("Date Module");
        dateModule.addSerializer(Date.class, new DateJsonSerializer());
        dateModule.addDeserializer(Date.class, new DateJsonDeserializer());
        om.registerModule(dateModule);
    }

    public static void registerModule(Module module) {
        if (om == null) {
            initObjectMapper();
        }
        om.registerModule(module);
    }

    public static TypeFactory getTypeFactory() {
        if (om == null) {
            initObjectMapper();
        }

        return om.getTypeFactory();
    }

    public static <T> T convert(Object objectToConvert, Class<? extends T> convertToClass) throws Exception {
        String from = toString(objectToConvert);
        return read(from, convertToClass);
    }

    public static <T> List<T> convert(List list, Class<? extends T> convertToClass) throws Exception {
        //convert entities to domain
        List<T> answ = new ArrayList<>(list.size());
        for (Object obj : list) {
            answ.add(JACKSON.convert(obj, convertToClass));
        }
        return answ;
    }
}
