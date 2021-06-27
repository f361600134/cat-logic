package com.cat.zproto.domain.module;

import java.util.ArrayList;
import java.util.List;

import com.cat.zproto.util.StringUtils;

/**
 * module信息, <br>
 * 可以把module基本信息跟协议信息封装成一个对象.<br>
 * 新建模块时, 对象只有基本信息, 添加协议时, 区分开来
 * @author Jeremy
 */
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
     * 附加信息,1:任务,2:资源, 3:个人模块(通常伴有事件机制,消息返回)
     */
    private List<Integer> extendInfo =new ArrayList<>();

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
        this.name = StringUtils.firstCharUpper(name);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

	@Override
	public String toString() {
		return "ModuleEntity [id=" + id + ", name=" + name + ", comment=" + comment + ", one2one=" + one2one + ", extendInfo=" + extendInfo + "]";
	}
	
}
