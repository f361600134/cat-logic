package com.cat.zproto.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 模板service
 * @author Administrator
 *
 */
@Service
public class TemplateService {
	
	private static Logger logger = LoggerFactory.getLogger(TemplateService.class);
	
	@Autowired private Configuration configuration;

	/**
	 * 输出文件
	 */
	public void printer(Object module, String fileName, String protoName) {
		try {
			File file = new File(fileName);
			Template template = configuration.getTemplate(protoName);
			FileOutputStream fos = new FileOutputStream(file);
	        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
			template.process(module, out);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("printer error, e:", e);
		}
		
	}
	
}
