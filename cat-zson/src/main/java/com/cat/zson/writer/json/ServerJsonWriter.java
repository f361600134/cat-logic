package com.cat.zson.writer.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
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
 * 服务端配置json生成工具
 * 
 * 
 *
 */
public class ServerJsonWriter {
    private final static Logger logger = LoggerFactory.getLogger(ServerJsonWriter.class);

    public static int writeAll(List<TableData> tableList, File targetDir) throws IOException {
        int size = 0;
        for (TableData tableData : tableList) {
            int tableType = tableData.getType();
            if (tableType != ConfigConstant.TABLE_TYPE_SERVER && tableType != ConfigConstant.TABLE_TYPE_SERVER_AND_CLIENT) {
                continue;
            }
            writeTable(tableData, targetDir);
            size++;
        }
        logger.info("write all serverJson.size={}", size);
        return size;
    }

//    public static void writeTable(TableData tableData, File targetDir) throws IOException {
//        String outputFileName = tableData.getName();
//        List<Param> paramList = tableData.getParamList();
//        List<ConfigData> configList = tableData.getConfigList();
//        // map结构 id:config
//        ObjectMapper objectmapper = JsonUtil.getObjectmapper();
//        JsonNodeFactory nodeFactory = objectmapper.getNodeFactory();
//        ObjectNode rootNode = nodeFactory.objectNode();
//        for (ConfigData configData : configList) {
//            if (!configData.getOutput().isServer()){
//                continue;
//            }
//            int id = configData.getId();
//            ObjectNode configJson = nodeFactory.objectNode();
//            List<Object> paramValueList = configData.getParamList();
//            for (int index = 0; index < paramList.size(); index++) {
//                Param param = paramList.get(index);
//                OutputTypes output = param.getOutput();
//                if (!output.isServer()) {
//                    // 该参数不输出到服务端json
//                    continue;
//                }
//                String key = param.getKey();
//                Object paramValue = null;
//                if (index < paramValueList.size()) {
//                    paramValue = paramValueList.get(index);
//                }
//                if (paramValue == null) {
//                    paramValue = param.getType().defaultValue();
//                }
//                configJson.putPOJO(key, paramValue);
//            }
//            rootNode.set(Integer.toString(id), configJson);
//        }
//
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.readTree(rootNode.toString());
//        }catch (JsonMappingException e){
//            logger.error("write serverJson:[{}] error", outputFileName);
//            throw new IOException(e);
//        }
//        File file = JsonUtil.createJsonFile(rootNode, targetDir, outputFileName);
//        logger.info("write serverJson:[{}]", file.getName());
//    }
    public static void writeTable(TableData tableData, File targetDir) throws IOException {
        String outputFileName = tableData.getName();
        List<Param> paramList = tableData.getParamList();
        List<ConfigData> configList = tableData.getConfigList();
        // map结构 id:config
        ObjectMapper objectmapper = JsonUtil.getObjectmapper();
        JsonNodeFactory nodeFactory = objectmapper.getNodeFactory();
//        ObjectNode rootNode = nodeFactory.objectNode();
        ArrayNode rootNode = nodeFactory.arrayNode();
        for (ConfigData configData : configList) {
            if (!configData.getOutput().isServer()){
                continue;
            }
            int id = configData.getId();
            ObjectNode configJson = nodeFactory.objectNode();
            List<Object> paramValueList = configData.getParamList();
            for (int index = 0; index < paramList.size(); index++) {
                Param param = paramList.get(index);
                OutputTypes output = param.getOutput();
                if (!output.isServer()) {
                    // 该参数不输出到服务端json
                    continue;
                }
                String key = param.getKey();
                Object paramValue = null;
                if (index < paramValueList.size()) {
                    paramValue = paramValueList.get(index);
                }
                if (paramValue == null) {
                    paramValue = param.getType().defaultValue();
                }
                configJson.putPOJO(key, paramValue);
            }
            //rootNode.set(Integer.toString(id), configJson);
            rootNode.add(configJson);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(rootNode.toString());
        }catch (JsonMappingException e){
            logger.error("write serverJson:[{}] error", outputFileName);
            throw new IOException(e);
        }
        File file = JsonUtil.createJsonFile(rootNode, targetDir, outputFileName);
        logger.info("write serverJson:[{}]", file.getName());
    }

}
