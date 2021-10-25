package com.cat.zson.structs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cat.zson.param.OutputTypes;
import com.cat.zson.param.ParamType;

/**
 * 单个参数的定义<br>
 * 对应表头
 *
 */
public class Param {
    /**
     * 在表中的下标
     */
    private int index;
    /**
     * 参数名
     */
    private String key;
    /**
     * 参数类型<br>
     * 
     */
    private ParamType type;
    /**
     * 名称<br>
     * 注释
     */
    private String name;
    /**
     * 描述
     */
    private String remark;
    /**
     * 输出类型<br>
     * 是否输出到前端/后端json中
     */
    private OutputTypes output;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ParamType getType() {
        return type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public OutputTypes getOutput() {
        return output;
    }

    public void setOutput(OutputTypes output) {
        this.output = output;
    }

    /**
     * 获取该参数的注释
     * 
     * @return
     */
    public List<String> getComments() {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(name)) {
            String[] strings = name.split("\n|\r");
            for (String str : strings) {
                if (StringUtils.isNotBlank(str)) {
                    list.add(str.trim());
                }
            }
        }
        if (StringUtils.isNotEmpty(remark)) {
            String[] strings = remark.split("\n|\r");
            for (String str : strings) {
                if (StringUtils.isNotBlank(str)) {
                    list.add(str.trim());
                }
            }
        }
        if (list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) {
                String comment = list.get(i);
                comment = comment + "<br>";
                list.set(i, comment);
            }
        }
        return list;
    }

}
