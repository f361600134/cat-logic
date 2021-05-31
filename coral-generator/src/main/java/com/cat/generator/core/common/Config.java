package com.cat.generator.core.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.cat.generator.core.param.annotation.ParamMapping;


@ParamMapping(prefix = "config")
public class Config {
	
	public static String suffix;
	public static String dbName;
	public static String outputPath;
	public static String ftlPath;
	public static String protoPath;
	
	public static DruidDataSource dbSource;
	
	/**
	 * 生成表列表
	 */
	public static Map<String, Info> genTbMap;
	
	/**
	 * 默认生成列表
	 */
	public static List<String> defaultProto;
	
	
	/**
	 * 表信息
	 * @author Jereme
	 *
	 */
	public static class Info{
		
		private String tbName;//实体名
		private boolean o2o;//true表示一对一,false表示一对多
		private boolean mission;//true表示任务类
		private boolean resource;//true表示资源类
		private boolean common;//true表示公共模块
		private String relation; //关联关键字,匹配就表示相同模块
		
		public String getTbName() {
			return tbName;
		}
		public void setTbName(String tbName) {
			this.tbName = tbName;
		}
		public boolean isO2o() {
			return o2o;
		}
		public void setO2o(boolean o2o) {
			this.o2o = o2o;
		}
		public boolean isMission() {
			return mission;
		}
		public void setMission(boolean mission) {
			this.mission = mission;
		}
		public boolean isResource() {
			return resource;
		}
		public void setResource(boolean resource) {
			this.resource = resource;
		}
		public boolean isCommon() {
			return common;
		}
		public void setCommon(boolean common) {
			this.common = common;
		}
		public String getRelation() {
			return StringUtils.isBlank(relation) ? tbName : relation;
		}
		public void setRelation(String relation) {
			this.relation = relation;
		}
	}
	
}
