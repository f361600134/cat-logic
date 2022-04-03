package com.cat.zson.writer.javacode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.zson.defines.ConfigConstant;
import com.cat.zson.structs.TableData;
import com.cat.zson.util.ResourceUtil;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

/**
 * java配置类生成器<br>
 * TODO 改为使用freemarker
 * 
 * 
 *
 */
public class JavaWriter {

    private final static Logger logger = LoggerFactory.getLogger(JavaWriter.class);

    private static Configuration configuration = buildConfiguration();

    private static Configuration buildConfiguration() {
        Version version = new Version(2, 3, 29);
        Configuration configuration = new Configuration(version);
        configuration.setDefaultEncoding("utf-8");
        // 设置模板文件对应的目录
        File templateDir = ResourceUtil.getFile(JavaConfigConstant.TEMPLATE_DIR);
        try {
            configuration.setDirectoryForTemplateLoading(templateDir);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("init freemarker config error.", e);
        }
        return configuration;
    }

    public static void writeAll(List<TableData> tableList, File targetDir) throws IOException {
        // 过滤出服务器使用的表
        List<TableData> serverTableList = new ArrayList<>();
        for (TableData tableData : tableList) {
            int type = tableData.getType();
            if (type != ConfigConstant.TABLE_TYPE_SERVER && type != ConfigConstant.TABLE_TYPE_SERVER_AND_CLIENT) {
                continue;
            }
            serverTableList.add(tableData);
        }

        for (TableData tableData : serverTableList) {
            // config
            writeConfigFile(tableData, targetDir);
        }
//        writeRegister(serverTableList, targetDir);
        logger.info("write json success.size={}", serverTableList.size());
    }

    private static void writeRegister(List<TableData> serverTableList, File targetDir) {
        File file = getJavaFile(targetDir, JavaConfigConstant.CONFIG_REGISTER_CLAZZ_NAME);
        try {
            Map<String,Object> dataModel=new HashMap<>();
            dataModel.put("tableList", serverTableList);
            writeFile(dataModel, JavaConfigConstant.REGISTER_TEMPLATE_NAME, file);
        } catch (Exception e) {
            logger.error("build java config register file error.", e);
        }
    }

    private static void writeConfigFile(TableData tableData, File targetDir) throws IOException {
        String configClassName = tableData.getJavaClazzSimpleName();
//        String configFullName = JavaConfigConstant.CONFIG_CLAZZ_PACKAGE_NAME + "." + configClassName;
        String configFullName = configClassName;
        //生成Base
        File baseFile = getJavaBaseFile(targetDir, configFullName);
        try {
			writeFile(tableData, JavaConfigConstant.CONFIG_BASE_TEMPLATE_NAME, baseFile);
		} catch (Exception e) {
			logger.error("config[" + tableData.getName() + "(" + tableData.getFileName() + ")] build java config file error.", e);
		       
		}
        //生成config
        File configFile = getJavaFile(targetDir, configFullName);
        try {
            writeFile(tableData, JavaConfigConstant.CONFIG_TEMPLATE_NAME, configFile);
        } catch (Exception e) {
            logger.error("config[" + tableData.getName() + "(" + tableData.getFileName() + ")] build java config file error.", e);
        }
    }

    private static File getJavaFile(File projectDir, String clazzFullName) {
        //String clazzPath = projectDir.getPath() + File.separator + "src/main/java" + File.separator;
    	String clazzPath = projectDir.getPath() + File.separator;
        clazzPath += clazzFullName.replace('.', File.separatorChar);
        clazzPath = clazzPath + ".java";
        File clazzFile = new File(clazzPath);
        return clazzFile;
    }
    
    private static File getJavaBaseFile(File projectDir, String clazzFullName) {
        //String clazzPath = projectDir.getPath() + File.separator + "src/main/java" + File.separator;
    	String clazzPath = projectDir.getPath() + File.separator + "base" + File.separator;
        clazzPath += clazzFullName.replace('.', File.separatorChar);
        clazzPath = clazzPath + "Base.java";
        File clazzFile = new File(clazzPath);
        return clazzFile;
    }

    private static void writeFile(Object dataModel, String templateName, File file) throws Exception {
        if (file.exists()) {
            file.delete();
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file)) {
            Template template = getTemplate(templateName);
            template.process(dataModel, fileWriter);
            fileWriter.flush();
            logger.info("write [{}] success.path:{}", file.getName(), file.getPath());
        } catch (IOException | TemplateException e) {
            logger.error("write [" + file.getName() + "] error.", e);
            throw e;
        }
    }

    private static Template getTemplate(String templateName) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        return configuration.getTemplate(templateName);
    }

}
