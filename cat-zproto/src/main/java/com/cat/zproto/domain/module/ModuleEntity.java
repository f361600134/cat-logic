package com.cat.zproto.domain.module;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class ModuleEntity {
    /**
     * 模块id<br>
     * 也对应功能开启id
     */
    private int id;
    /**
     * 模块名
     */
    private String name;
    /**
     * 该模块的注释
     */
    private String comment;
    
    /**
     * 是否一对一
     */
    private boolean one2one;
    
    /**
     * 附加信息,1:任务,2:资源,3:一对多
     */
    private List<Integer> extendInfo =new ArrayList<>();

    /**
     * 所属终端
     */
    private List<Integer> endPoints=new ArrayList<>();
    
    /**
     * proto中的包名<br>
     * 也是前端使用的包名
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String protoPackage;
    /**
     * java中的包名
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String javaPackage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Integer> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(List<Integer> endPoints) {
        this.endPoints = endPoints;
    }

    public String getProtoPackage() {
        return protoPackage;
    }

    public void setProtoPackage(String protoPackage) {
        this.protoPackage = protoPackage;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

	public List<Integer> getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(List<Integer> extendInfo) {
		this.extendInfo = extendInfo;
	}

	public boolean isOne2one() {
		return one2one;
	}

	public void setOne2one(boolean one2one) {
		this.one2one = one2one;
	}
}
