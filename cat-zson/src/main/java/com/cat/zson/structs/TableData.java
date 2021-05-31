package com.cat.zson.structs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

import com.cat.zson.param.OutputTypes;
import com.cat.zson.param.ParamType;
import com.cat.zson.writer.javacode.JavaConfigConstant;

public class TableData {
    /**
     * 表名<br>
     * excel中的sheetName
     */
    private String name;
    /**
     * 文件名<br>
     * excel文件名
     */
    private String fileName;

    /**
     * 配置类型<br>
     * 是否前端/后端使用
     */
    private int type;
    /**
     * 参数列表<br>
     * 表头
     */
    private List<Param> paramList = new ArrayList<>();
    /**
     * 配置列表
     */
    private List<ConfigData> configList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Param> getParamList() {
        return paramList;
    }

    public void setParamList(List<Param> paramList) {
        this.paramList = paramList;
    }

    public List<ConfigData> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ConfigData> configList) {
        this.configList = configList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getJavaSuperSimpleClazz() {
        String fullName = getJavaSuperClazzFullName();
        String simpleName = fullName.substring(fullName.lastIndexOf(".") + 1);
        return simpleName;
    }

    public String getJavaClazzSimpleName() {
//        StringBuffer sb = new StringBuffer();
//        sb.append(name.substring(0, 1).toUpperCase());
//        if (name.length() > 1) {
//            sb.append(name.substring(1));
//        }        
        // sb.append("Config");

        String preName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
        return "Config".concat(preName);
    }

    public String getJavaSuperClazzFullName() {
        // TODO FIXME

        return JavaConfigConstant.DEFAULT_SUPER_CLASS_NAME;
    }

    public String getJavaPackageName() {
        return JavaConfigConstant.CONFIG_CLAZZ_PACKAGE_NAME;
    }

    public List<Param> getJavaParams() {
        List<Param> list = new ArrayList<>();
        for (Param param : paramList) {
            OutputTypes output = param.getOutput();
            if (!output.isServer()) {
                continue;
            }
            list.add(param);
        }
        return list;

    }

    /**
     * 引用的java类
     * 
     * @return
     */
    public Set<String> getImportJavaTypes() {
        HashSet<String> importClazzes = new HashSet<>();
        for (Param param : getJavaParams()) {
            ParamType  paramType = param.getType();
            String importClazz = paramType.getImportClazz();
            if (StringUtils.isEmpty(importClazz)) {
                continue;
            }
            importClazzes.add(importClazz);
        }
        importClazzes.add(JavaConfigConstant.CONFIG_PATH_CLASS_NAME);
        importClazzes.add(getJavaSuperClazzFullName());
        return importClazzes;
    }
}
