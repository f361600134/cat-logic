package com.cat.zproto.enums;

import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.dto.TemplateTreeDto;

public enum TemplateEnum {

	CODE(1, "代码"){
		@Override
		public TemplateTreeDto newTreeDto() {
			TemplateTreeDto dto = super.newTreeDto();
			dto.setSpread(true);
			return dto;
		}
		
		@Override
		public String getPath() {
			return CommonConstant.FTL_CODE_PATH;
		}
		
		@Override
		public String getStructPath() {
			return CommonConstant.FTL_STRUCT_CODE_PATH;
		}

	},
	PROTO(2, "协议"){
		
		@Override
		public String getPath() {
			return CommonConstant.FTL_PROTO_PATH;
		}
		
		@Override
		public String getStructPath() {
			return CommonConstant.FTL_STRUCT_PROTO_PATH;
		}
		
	},
	;
	
	private final int type;
	private final String name;
	
	private TemplateEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}

	public TemplateTreeDto newTreeDto() {
		TemplateTreeDto dto = TemplateTreeDto.newTree(getType(), getName());
//		dto.setDisabled(true);
//		List<String> names = getAllFileName();
//		for (int i = 0; i < names.size(); i++) {
//			int childrenId = getType() * 1000 + i;
//			dto.addChildren(TemplateTreeDto.newTree(childrenId, names.get(i)));
//		}
		return dto;
	}
	
	/**
	 * 根据类型返回生成的文件的路径
	 * @return
	 */
	public abstract String getPath();

	/**
	 * 根据类型返回对应的存储结构路径
	 * @return
	 */
	public abstract String getStructPath();
	
	public static TemplateEnum getEnum(int type) {
		for (TemplateEnum tenum : values()) {
			if (tenum.getType() == type) {
				return tenum;
			}
		}
		return null;
	} 
}
