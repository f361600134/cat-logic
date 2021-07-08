package com.cat.zproto.domain.template;

import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.dto.TemplateTreeDto;

/**
 * 模板结构对象
 * @author Jeremy
 */
public class TemplateStruct {
	/**
	 * 模板id<br>
	 * 此id用于给页面区分唯一性的, 同一个文件每次重启生成的id不一致<br>
	 * 所以不能用于后台表示同文件的唯一id
	 */
	private int id;
	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 模板类型
	 */
	private int type;
	/**
	 * 模板内容
	 */
	private String content;
	
	/**
	 * 持久化成json文件的名字
	 */
	private transient String jsonName;
	
	/**
	 * 持久化成ftl文件的名字
	 */
	private transient String ftlName;
	
	public TemplateStruct() {
		super();
	}
	
	public TemplateStruct(int id, String name, int type, String content) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.content = content;
		this.ftlName = this.name.concat(CommonConstant.TEMPLATE_SUBFIX);
		this.jsonName = this.name.concat(CommonConstant.JSON_SUBFIX);
	}
	
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
		this.ftlName = this.name.concat(CommonConstant.TEMPLATE_SUBFIX);
		this.jsonName = this.name.concat(CommonConstant.JSON_SUBFIX);
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public String getJsonName() {
		return jsonName;
	}
	public String getFtlName() {
		return ftlName;
	}

	/**
	 * 创建一个新的模板结构
	 * @param id
	 * @param name
	 * @param type
	 * @param content
	 * @return
	 */
	public static TemplateStruct create(int id, String name, int type, String content) {
		return new TemplateStruct(id, name, type, content);
	}
	
	public TemplateTreeDto toTemplateDto() {
		return TemplateTreeDto.newTree(this.getId(), this.getFtlName());
	}
	
}
