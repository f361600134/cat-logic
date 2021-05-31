package com.cat.zson.writer.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.cat.zson.defines.ConfigConstant;
import com.cat.zson.param.OutputTypes;
import com.cat.zson.structs.ConfigData;
import com.cat.zson.structs.Param;
import com.cat.zson.structs.TableData;
import com.cat.zson.util.JsonUtil;

/**
 * 客户端配置类生成器
 *
 * 
 *
 */
public class ClientJsonWriter {
    private final static Logger logger = LoggerFactory.getLogger(ClientJsonWriter.class);

    public static int writeAll(List<TableData> tableList, File targetDir) throws IOException {
        int size = 0;
        for (TableData tableData : tableList) {
            String tableName = tableData.getName();
            int tableType = tableData.getType();
            if (tableName.equals(ConfigConstant.CUT_TABLE_NAME)){
                continue;
            }
            if (tableType != ConfigConstant.TABLE_TYPE_CLIENT &&
                    tableType != ConfigConstant.TABLE_TYPE_SERVER_AND_CLIENT) {
                // 不是客户端json
                continue;
            }
            writeTable(tableData, targetDir);
            size++;
        }
        logger.info("write all clientJson.size={}", size);
        return size;
    }

    public static void writeTable(TableData tableData, File targetDir) throws IOException {
        List<Param> paramList = tableData.getParamList();
        List<ConfigData> configList = tableData.getConfigList();
        ObjectMapper objectmapper = JsonUtil.getObjectmapper();
        JsonNodeFactory nodeFactory = objectmapper.getNodeFactory();
        ObjectNode rootNode = nodeFactory.objectNode();
        // colMap=[paramKey,...]
        ArrayNode paramsJson = nodeFactory.arrayNode();
        for (Param param : paramList) {
            OutputTypes output = param.getOutput();
            if (!output.isClient()) {
                // 该参数不输出到客户端json
                continue;
            }
            String key = param.getKey();
            if (ConfigConstant.ID_KEY.equals(key)) {
                continue;
            }
            paramsJson.add(key);
        }
        ObjectNode configsJson = nodeFactory.objectNode();
        for (ConfigData configData : configList) {
            if (!configData.getOutput().isClient()){
                continue;
            }
            int id = configData.getId();
            ArrayNode configJson = nodeFactory.arrayNode();
            List<Object> paramValueList = configData.getParamList();
            for (Param param : paramList) {
                OutputTypes output = param.getOutput();
                if (!output.isClient()) {
                    // 该参数不输出到客户端json
                    continue;
                }
                String key = param.getKey();
                if (ConfigConstant.ID_KEY.equals(key)) {
                    continue;
                }
                int index = param.getIndex();
                Object paramValue = null;
                if (index >= 0 && index <= paramValueList.size()) {
                    //list 从零开始取值 客户端输出结果 id作为key 少一项
                    paramValue = paramValueList.get(index - 1);
                }
                if (paramValue == null) {
                    paramValue = param.getType().defaultValue();
                }
                if (paramValue instanceof JsonNode){
                    JsonNode jsonNode = (JsonNode) paramValue;
                    configJson.add(jsonNode);
                }else {
                    configJson.addPOJO(paramValue);
                }
            }
            configsJson.putPOJO(Integer.toString(id), configJson);
        }
        rootNode.putPOJO("colMap", paramsJson);
        rootNode.putPOJO("list", configsJson);
        // 输出
        String outputFileName = buildFileName(tableData.getName());
        File file = JsonUtil.createJsonFile(rootNode, targetDir, outputFileName);
        logger.info("write clientJson:[{}]", file.getName());

    }

    private static String buildFileName(String tableName) {
        // 转成大驼峰式
        String arr[] = tableName.split("_");
        StringBuffer sb = new StringBuffer();
        for (String anArr : arr) {
            sb.append(toUpperCaseFirstOne(anArr));
        }
        //sb.append("Config");
        return sb.toString();
    }

    private static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return String.valueOf(Character.toUpperCase(s.charAt(0))) + s.substring(1);
        }
    }

}
