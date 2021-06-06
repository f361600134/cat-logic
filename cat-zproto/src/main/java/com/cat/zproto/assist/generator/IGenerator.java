package com.cat.zproto.assist.generator;

import com.cat.zproto.domain.table.TableFreemarkerDto;

public interface IGenerator {
	
	/**
	 * 生成文件所在子目录
	 * @return  
	 * @return String  
	 * @date 2021年6月6日下午9:09:41
	 */
	String getChildDir();
	
	/**
	 * 文件名前部分补充
	 * 比如: ItemInfo是一个DTO对象,生成构建对象时为了区分加上PB关键字<br>
	 * 最后组成了PBItemInfo, 看需要配置
	 * @return  
	 * @return String  
	 * @date 2021年6月6日下午9:09:41
	 */
	String getFileNameFrontPart();
	
	/**
	 * 文件名后部分补充
	 * 比如: 要生成代码ItemService, 我们仅只有EntityName--Item, 所以需要通过<br>
	 * Item+Service拼接, 构成最终需要的ItemService.java文件<br>
	 * 最后组成了PBItemInfo, 看需要配置
	 * @return  
	 * @return String  
	 * @date 2021年6月6日下午9:09:41
	 */
	String getFileNameLatterPart();
	
	/**
	 * 获取文件后缀名
	 * @return  
	 * @return String  
	 * @date 2021年6月6日下午9:09:41
	 */
	String getFileNameSuffix();
	
	/**
	 * 获取proto名字,freemarker根据此名字获得模板
	 * @return  
	 * @return String  
	 * @date 2021年6月6日下午9:09:41
	 */
	String getProtoName();
	
	/**
	 * 生成
	 * @param dto  
	 * @return void  
	 * @date 2021年6月6日下午9:18:14
	 */
	void generate(TableFreemarkerDto dto);
	
}
