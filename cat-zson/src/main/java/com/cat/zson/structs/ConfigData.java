package com.cat.zson.structs;

import java.util.ArrayList;
import java.util.List;

import com.cat.zson.param.OutputTypes;

/**
 * 单条配置数据<br>
 * 对应1条配置<br>
 * 
 * 
 * 
 *
 */
public class ConfigData implements Comparable<ConfigData> {

    private OutputTypes output;
    
    private int id;
    
    /**
     * 参数列表<br>
     * 含id<br>
     *不需要输出的行 也塞个空字符串
     * 
     */
    private List<Object> paramList = new ArrayList<>();

    public List<Object> getParamList() {
        return paramList;
    }

    public void setParamList(List<Object> paramList) {
        this.paramList = paramList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(ConfigData o) {
        return Integer.compare(id, o.id);
    }

    public OutputTypes getOutput() {
        return output;
    }

    public void setOutput(OutputTypes output) {
        this.output = output;
    }

}
