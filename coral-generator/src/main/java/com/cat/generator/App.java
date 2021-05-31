package com.cat.generator;

import java.util.List;

import com.cat.generator.core.common.Config;
import com.cat.generator.core.db.TableEntity;
import com.cat.generator.core.param.ParamAnalysis;
import com.cat.generator.core.proto.ProtocolParser;
import com.cat.generator.util.DbUtils;
import com.cat.generator.util.FreeMarkerTemplateUtils;
import com.cat.generator.util.Printer;

/**
 * Hello world!
 *
 */
public class App {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	try {
    		long startTime = System.currentTimeMillis();
    		//配置解析
			ParamAnalysis.analysis();
			
			//数据库内容从初始化
			List<TableEntity> list = DbUtils.readDb(Config.dbSource);
			
			//模板初始化
			FreeMarkerTemplateUtils.setConfig(Config.ftlPath);
			
			//解析协议
			ProtocolParser.parse(Config.protoPath);
			
			//生成代码
			Printer.genDefault();
			Printer.genPojo(list);
			
			log.info("generator working succeessfully, Cost time:{}ms", (System.currentTimeMillis() - startTime));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
