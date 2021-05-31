package com.cat.zson.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;


/**
 * json工具类
 * 
 * 
 *
 */
public class JsonUtil {
	
	   public static final String JSON_EXTENSION = ".json";

    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private final static ObjectMapper objectMapper = initObjectMapper();

    private static ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 是否可以解析带有/* //格式的注释的json
        objectMapper.configure(Feature.ALLOW_COMMENTS, true);
        // 是否可以解析带有/* //格式的注释的json
        objectMapper.configure(Feature.ALLOW_YAML_COMMENTS, true);
        // 是否可以解析带有单引号的字符串
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        // // 是否可以解析结束语控制字符
        // objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // // 是否可以解析以"0"为开头的数字(如: 000001),解析时则忽略0
        // objectMapper.configure(Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        // 若json的属性比对应的java类多 是否抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 不使用get set方法 直接使用变量名进行序列化/反序列化
        objectMapper.setVisibility(PropertyAccessor.SETTER, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        // 注册各种常用的类的解析器
        SimpleModule module = new SimpleModule();
        // 扫描自定义解析类
//        registerCustomJsonDeserializers(module);
        objectMapper.registerModule(module);
        return objectMapper;
    }

//    /**
//     * 注册自定义解析类
//     * 
//     * @param module
//     */
//    @SuppressWarnings("unchecked")
//    private static void registerCustomJsonDeserializers(SimpleModule module) {
//        List<Class<?>> clazzList = ClazzUtil.scanClassList((clazz) -> {
//            if (!clazz.isAnnotationPresent(AutoRegister.class)) {
//                return false;
//            }
//            if (!JsonDeserializer.class.isAssignableFrom(clazz)) {
//                return false;
//            }
//            return true;
//        });
//
//        Map<Class<?>, Class<?>> deserializerModifier = new HashMap<>();
//
//        for (Class<?> clazz : clazzList) {
//            try {
//                Class<?>[] types = ClazzUtil.getParameterizedTypeClazzes(clazz, JsonDeserializer.class);
//                if (types == null) {
//                    types = ClazzUtil.getParameterizedTypeClazzes(clazz, AbstractCustomJsonParsers.class);
//                }
//                Class<Object> type = (Class<Object>) types[0];
//                if (AbstractCustomJsonParsers.class.isAssignableFrom(clazz)) {
//                    deserializerModifier.put(type, clazz);
//                    continue;
//                }
//                JsonDeserializer<Object> deser = (JsonDeserializer<Object>) clazz.getConstructor().newInstance();
//
//                module.addDeserializer(type, deser);
//
//            } catch (Exception e) {
//                logger.error("JsonUtil register deserializers error", e);
//                e.printStackTrace();
//            }
//        }
//
//        if (!deserializerModifier.isEmpty()) {
//            module.setDeserializerModifier(new BeanDeserializerModifier() {
//                @Override
//                public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
//
//                    if (deserializerModifier.containsKey(beanDesc.getBeanClass())) {
//                        Class<?> clazz = deserializerModifier.get(beanDesc.getBeanClass());
//                        try {
//                            JsonDeserializer<Object> deser = (JsonDeserializer<Object>) clazz.getConstructor().newInstance();
//                            Field field = deser.getClass().getSuperclass().getDeclaredField("defaultDeserializer");
//                            field.setAccessible(true);
//                            field.set(deser, deserializer);
//                            return deser;
//                        } catch (Exception e) {
//                            logger.error("JsonUtil register deserializers error", e);
//                            e.printStackTrace();
//                        }
//                    }
//                    return deserializer;
//                }
//            });
//        }
//
//    }

    /**
     * 对象转为json
     * 
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            String jsonStr = objectMapper.writeValueAsString(obj);
            return jsonStr;
        } catch (JsonProcessingException e) {
            logger.error("objToJsonString error.", e);
        }
        return ReflectionToStringBuilder.toString(obj, ToStringStyle.JSON_STYLE);
    }

    /**
     * 对象转为json格式的字节流
     * 
     * @param obj
     * @return
     */
    public static byte[] toJsonBytes(Object obj) {
        try {
            byte[] jsonBytes = objectMapper.writeValueAsBytes(obj);
            return jsonBytes;
        } catch (JsonProcessingException e) {
            logger.error("objToJsonBytes error.", e);
        }
        return ReflectionToStringBuilder.toString(obj, ToStringStyle.JSON_STYLE).getBytes(Charsets.UTF_8);
    }

    /**
     * json转为对象
     * 
     * @param <T>
     * @param jsonStr
     * @param typeReference
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T toObject(String jsonStr, TypeReference<T> typeReference) throws JsonProcessingException {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            T obj = objectMapper.readValue(jsonStr, typeReference);
            return obj;
        } catch (JsonProcessingException e) {
            logger.error("jsonToObject error", e);
            throw e;
        }
    }

    /**
     * json转为对象
     * 
     * @param <T>
     * @param json
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> T toObject(JsonNode json, Class<T> clazz) throws IOException {
        if (json == null || json.isNull()) {
            return null;
        }
        try {
            ObjectReader reader = objectMapper.readerFor(clazz);
            T obj = reader.readValue(json);
            return obj;
        } catch (IOException e) {
            logger.error("jsonToObject error", e);
            throw e;
        }
    }

    /**
     * json转为对象
     * 
     * @param <T>
     * @param jsonStr
     * @param clazz
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T toObject(String jsonStr, Class<T> clazz) throws JsonProcessingException {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            T obj = objectMapper.readValue(jsonStr, clazz);
            return obj;
        } catch (JsonProcessingException e) {
            logger.error("jsonToObject error", e);
            throw e;
        }
    }

    /**
     * json转为对象
     * 
     * @param <T>
     * @param jsonStr
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> T toObject(byte[] jsonBytes, Class<T> clazz) throws IOException {
        if (jsonBytes == null || jsonBytes.length <= 0) {
            return null;
        }
        try {
            T obj = objectMapper.readValue(jsonBytes, clazz);
            return obj;
        } catch (JsonProcessingException e) {
            logger.error("jsonBytesToObject error", e);
            throw e;
        }
    }

    /**
     * json转为list
     * 
     * @param <T>
     * @param jsonStr
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> List<T> toList(String jsonStr, Class<T> clazz) throws IOException {
        List<T> list = new ArrayList<>();
        if (StringUtils.isEmpty(jsonStr)) {
            return list;
        }
        try {
            ObjectReader reader = objectMapper.readerFor(clazz);
            JsonNode rootNode = reader.readTree(jsonStr);
            if (rootNode.isArray()) {
                ArrayNode rootArrayNode = (ArrayNode) rootNode;
                Iterator<JsonNode> iterator = rootArrayNode.elements();
                while (iterator.hasNext()) {
                    JsonNode node = iterator.next();
                    T obj = reader.readValue(node);
                    list.add(obj);
                }
            } else if (rootNode.isObject()) {
                Iterator<JsonNode> iterator = rootNode.elements();
                while (iterator.hasNext()) {
                    JsonNode node = iterator.next();
                    T obj = reader.readValue(node);
                    list.add(obj);
                }
            } else if (rootNode.isPojo()) {
                T obj = reader.readValue(rootNode);
                list.add(obj);
            }
            return list;
        } catch (IOException e) {
            logger.error("jsonToObject error", e);
            throw e;
        }
    }

    public static ObjectMapper getObjectmapper() {
        return objectMapper;
    }
    
    public static File createJsonFile(JsonNode json, File targetDir, String fileName) throws IOException {
        String jsonFileName = fileName + JSON_EXTENSION;
        File file = new File(targetDir, jsonFileName);
        return createJsonFile(json, file);
    }

    public static File createJsonFile(JsonNode json, File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        // 2020/07/24 修改代码,支持转移字符 @lst
//        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        String jsonStr = objectMapper.writeValueAsString(json);
        jsonStr = StringEscapeUtils.unescapeJava(jsonStr);

        PrintWriter printWriter = new PrintWriter(file,"UTF-8");

        printWriter.write(jsonStr);

        printWriter.flush();

        printWriter.close();

//        try (JsonGenerator generator = objectMapper.getFactory().createGenerator(file, JsonEncoding.UTF8)) {
//            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
//            writer.writeValue(generator, json);
//
//            file.
//
//            return file;
//        } finally {
//        }

        return file;

    }


}
