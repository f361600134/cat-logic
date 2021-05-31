package com.cat.zson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fatiny.core.parse.ParamAnalysis;
import com.cat.zson.common.Config;
import com.cat.zson.reader.ExcelReader;
import com.cat.zson.structs.TableData;
import com.cat.zson.util.FileUtil;
import com.cat.zson.writer.javacode.JavaWriter;
import com.cat.zson.writer.json.ClientJsonWriter;
import com.cat.zson.writer.json.ServerJsonWriter;


public class App {
	
    private final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
    	ParamAnalysis.analysis("com.cat.zson");
    	
    	List<TableData> tableList = ExcelReader.parseAllExcels(Config.excelDir);
    	if (tableList.isEmpty()) {
            logger.error("sourcePath:{} .tableList is empty.", Config.excelDir);
            return;
        }
    	
    	exportJson(tableList);
    	exportJava(tableList, Config.javaDir);
    }
    
    private static void exportJson(List<TableData> tableList) throws IOException {
        // 服务端json
//        String serverJsonPath = jsonPath + File.separator + SERVER_JSON_DIR_NAME;
        File serverJsonDir = FileUtil.clearOrCreateDirWithoutSvn(Config.jsonDir);
        ServerJsonWriter.writeAll(tableList, serverJsonDir);

        // 客户端json
//        String clientJsonPath = jsonPath + File.separator + CLIENT_JSON_DIR_NAME;
        File clientJsonDir = FileUtil.clearOrCreateDirWithoutSvn(Config.clientJsonDir);
        ClientJsonWriter.writeAll(tableList, clientJsonDir);

//        String clientDecJsonPath = Settings.getClientDecJsonDir();
//        File clientDecJsonDir = FileUtil.clearOrCreateDirWithoutSvn(clientDecJsonPath);
//        ClientDecJsonWriter.writeAll(tableList, clientDecJsonDir);
    }

    private static void exportJava(List<TableData> tableList, String javaPath) throws IOException {
        File targetDir = new File(javaPath);
        JavaWriter.writeAll(tableList, targetDir);
    }
}
