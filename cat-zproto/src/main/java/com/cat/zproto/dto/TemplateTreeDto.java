package com.cat.zproto.dto;

import java.util.List;

/**
 *  模板文件树
 * @author Administrator
 */
public class TemplateTreeDto {
	
	private int id;
	private String title;
	private boolean disabled;
	private boolean spread;//节点是否初始展开，默认 false
	private List<TemplateTreeDto> children;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isSpread() {
		return spread;
	}
	public void setSpread(boolean spread) {
		this.spread = spread;
	}
	public List<TemplateTreeDto> getChildren() {
		return children;
	}
	public void setChildren(List<TemplateTreeDto> children) {
		this.children = children;
	}
	
	public void addChildren(TemplateTreeDto dto) {
		this.children.add(dto);
	}
	
	public TemplateTreeDto() {
	}
	
	
	public static TemplateTreeDto newTree(int id, String title) {
		TemplateTreeDto dto = new TemplateTreeDto();
		dto.setId(id);
		dto.setTitle(title);
		return dto;
	}
	
}

