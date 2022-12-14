package com.cat.server.common;

import java.util.Collection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cat.api.core.task.TokenTaskQueueExecutor;
import com.cat.api.core.task.impl.CommonTaskExecutor;
import com.cat.api.core.task.impl.DefaultTokenTaskQueueExecutor;
import com.cat.orm.core.base.BasePo;
import com.cat.orm.core.db.process.DataProcessor;
import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;

/**
 * 业务组件注册
 * @author Administrator
 *
 */
@Configuration
public class LogicComponent {

	private static final Logger logger = LoggerFactory.getLogger(LogicComponent.class);

	@Autowired private ServerConfig serverConfig;
	
	/**
	 * 注册指定的数据处理器
	 * @return
	 */
	@Bean
	public IDataProcess processor(DataSource dataSource){
		Collection<Class<BasePo>> cols = ClassManager.instance().getClassByType(BasePo.class);
		logger.info("注册[DataProcessorAsyn]服务");
		return new DataProcessor(cols, new JdbcTemplate(dataSource));
	}

	/**
	 * 注册默认的公共线程池
	 * @return
	 */
	@Bean
	public TokenTaskQueueExecutor defaultExecutor() {
		logger.info("注册[DefaultTokenTaskQueueExecutor]服务");
		return new DefaultTokenTaskQueueExecutor("default-executor",1);
	}
	

	/**
	 * 注册默认的公共线程池
	 * @return
	 */
	@Bean
	public CommonTaskExecutor commonExecutor() {
		logger.info("注册[CommonTaskExecutor]服务");
		return new CommonTaskExecutor(2, "common-executor");
	}

	/**
	 * 注册UUID Generater
	 * @return
	 */
	@Bean
	public SnowflakeGenerator snowflakeGenerator(){
		logger.info("注册[SnowflakeGenerator]服务");
		return new SnowflakeGenerator(serverConfig.getServerId());
	}

	/**
	 * 注册json管理器
	 * @return
	 */
	@Bean
	public ConfigManager configManager(){
		logger.info("注册[ConfigManager]服务");
		return new ConfigManager();
	}

	/**
	 * 注册事件机制
	 * @return
	 */
	@Bean
	public GameEventBus GameEventBus() {
		logger.info("注册[GameEventBus]服务");
		return new GameEventBus();
	}
	
}
