package com.cat.zproto.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingMysql;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;

/**
 * 业务组件注册
 * 
 * @author Jeremy
 */
@Configuration
public class SystemComponent {

	private static final Logger logger = LoggerFactory.getLogger(SystemComponent.class);

	/**
	 * 注册系统配置
	 * 
	 * @return
	 */
	@Bean
	public SettingConfig setting() {
		SettingConfig setting = null;
		try {
			ClassPathResource resource = new ClassPathResource(CommonConstant.SYSTEM_SETTING);
			//way1
//			File file = resource.getFile();
//			String content = FileUtils.readFileToString(file,StandardCharsets.UTF_8);
			//way2
//			InputStream inputStream= resource.getInputStream();
//			String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			//way3
//			String content = FileUtils.readFileToString(new File(CommonConstant.SYSTEM_SETTING),
//					StandardCharsets.UTF_8);
			//way4
			InputStream inputStream = resource.getInputStream();
			String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			setting = JSON.parseObject(content, SettingConfig.class, Feature.CustomMapDeserializer);
			setting.init();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("setting error");
		}
		logger.info("注册[SettingConfig]服务:{}", setting);
		return setting;
	}
	
	/**
	 * 注册数据库连接池, 固定一条连接
	 * 
	 * @return
	 */
	@Bean
	public DataSource database(SettingConfig setting) {
		SettingMysql mysqlInfo = setting.getDbInfo();
		DruidDataSource dataSource = mysqlInfo.newDruidDataSource();
		logger.info("注册[DataSource]服务, url:{}", dataSource.getUrl());
		return dataSource;
	}

	@Bean
	public SVNClientManager svnClientManager(SettingConfig setting) {
		DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        String username = setting.getSvn().getAccount();
        String password = setting.getSvn().getPassword();
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(null, username, password.toCharArray(), false);
        SVNClientManager svnClientManager = SVNClientManager.newInstance(options, authManager);
        logger.info("注册[svn]服务, username:{}, password:{}", username, password);
		return svnClientManager;
	}
	
//	/**
//	 * 注册字符串类型的文本模板
//	 * @return
//	 */
//	@Bean("stringConfiguration")
//	public freemarker.template.Configuration configuration(SettingConfig setting) {
//		freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_29);
//		StringTemplateLoader templateLoader = new StringTemplateLoader();
//		configuration.setTemplateLoader(templateLoader);
//		configuration.setDefaultEncoding("UTF-8");
//		return configuration;
//	}
	
	/**
	 * freemaker放在resource目录下不能编辑保存
	 * 所以放在项目根目录作为使用者用户配置存在.
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	@Primary
	@Bean
    public freemarker.template.Configuration freemarkerConfig() throws IOException, TemplateException {
		freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_29);
		//way1
		FileTemplateLoader loader1=new FileTemplateLoader(new File(CommonConstant.FTL_CODE_PATH));
		FileTemplateLoader loader2=new FileTemplateLoader(new File(CommonConstant.FTL_PROTO_PATH));
		TemplateLoader[] fileloadders={loader1,loader2};
		MultiTemplateLoader fmtl=new MultiTemplateLoader(fileloadders);
		configuration.setTemplateLoader(fmtl);
        //way2
//		configuration.setDirectoryForTemplateLoading(new File(CommonConstant.FTL_CODE_PATH));
		
		configuration.setClassicCompatible(true);
		configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return configuration;
    }


}
