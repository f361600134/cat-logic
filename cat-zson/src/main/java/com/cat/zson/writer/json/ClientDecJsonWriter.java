package com.cat.zson.writer.json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
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
public class ClientDecJsonWriter {

    private final static Logger logger = LoggerFactory.getLogger(ClientDecJsonWriter.class);

    public static int writeAll(List<TableData> tableList, File targetDir) throws IOException {
        Map<Integer, String> groupFiles = new HashMap<>();
        for (TableData tableData : tableList) {
            String tableName = tableData.getName();
            if (!tableName.equals(ConfigConstant.CUT_TABLE_NAME)) {
                // 不是剧情表
                continue;
            }
            String fileName = tableData.getFileName();
            List<ConfigData> configList = tableData.getConfigList();
            Map<Integer, List<ConfigData>> groupMap = new HashMap<>();
            for (ConfigData config : configList) {
                int id = config.getId();
                int groupId = getGroupId(id);
                if (groupFiles.containsKey(groupId)) {
                    String otherFileName = groupFiles.get(groupId);
                    logger.error("write client dec doc error.same groupId[{}] in [{}] and [{}]", groupId, fileName, otherFileName);
                    throw new RuntimeException("write client dec doc error.same groupId[" + groupId + "] in [" + fileName + "] and [" + otherFileName + "]");
                }
                List<ConfigData> list = groupMap.computeIfAbsent(groupId, k -> new ArrayList<>());
                list.add(config);
            }
            writeJsons(tableData, groupMap, targetDir);
            for (int groupId : groupMap.keySet()) {
                groupFiles.put(groupId, fileName);
            }
        }
        logger.info("write all clientDecJson.size={}", groupFiles.size());
        return groupFiles.size();
    }

    private static void writeJsons(TableData tableData, Map<Integer, List<ConfigData>> groupMap, File targetDir) throws IOException {
        List<Param> paramList = tableData.getParamList();
        for (Entry<Integer, List<ConfigData>> entry : groupMap.entrySet()) {
            int groupId = entry.getKey();
            List<ConfigData> configList = entry.getValue();
            Collections.sort(configList);
            String fileName = Integer.toString(groupId);
            JsonNode json = createJson(paramList, configList);
            // 输出
            File file = JsonUtil.createJsonFile(json, targetDir, fileName);
            logger.info("write clientDecJson:[{}]", file.getName());
        }
        logger.info("write clientDec[{}] size:{}", tableData.getFileName(), groupMap.size());
    }

    private static JsonNode createJson(List<Param> paramList, List<ConfigData> configList) {
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
            int id = configData.getId();
            ArrayNode configJson = nodeFactory.arrayNode();
            List<Object> paramValueList = configData.getParamList();
            for (Param param : paramList) {
                OutputTypes output = param.getOutput();
                if (!output.isClient()) {
                    continue;
                }
                String key = param.getKey();
                if (ConfigConstant.ID_KEY.equals(key)) {
                    continue;
                }
                int index = param.getIndex();
                Object paramValue = null;
                if (index >= 0 && index <= paramValueList.size()) {
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
        return rootNode;
    }

    private static int getGroupId(int id) {
        return id / 10000;
    }

}
