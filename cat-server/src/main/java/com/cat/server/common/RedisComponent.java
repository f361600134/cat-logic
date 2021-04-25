package com.cat.server.common;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.cat.orm.core.base.BasePo;
import com.cat.orm.core.redis.RedisProcessor;

//默认不开启redis
//@Configuration
public class RedisComponent {

	private static final Logger log = LoggerFactory.getLogger(RedisComponent.class);

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${spring.redis.password}")
	private String redisPassword;
	@Value("${spring.redis.timeout}")
	private long timeout;
	@Value("${spring.redis.database}")
	private int database;
	@Value("${spring.redis.lettuce.pool.max-active}") // 最大连接数
	private int maxActive;
	@Value("${spring.redis.lettuce.pool.max-idle}") // 最大空闲 数
	private int maxIdle;
	@Value("${spring.redis.lettuce.pool.min-idle}") // 最小空闲 数
	private int minIdle;
	@Value("${spring.redis.lettuce.pool.max-wait}") // 连接池等待时间
	private long maxWait;
	@Value("${spring.redis.lettuce.pool.shutdown.timeout}") // 关闭超时
	private long shutdownTimeout;

	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory() {
		// redis配置
		RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
		redisConfiguration.setDatabase(database);
		redisConfiguration.setPassword(redisPassword);

		// 连接池配置
		GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<Object>();
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMinIdle(minIdle);
		genericObjectPoolConfig.setMaxTotal(maxActive);
		genericObjectPoolConfig.setMaxWaitMillis(maxWait);

		// redis客户端配置
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration
				.builder();
		builder.commandTimeout(Duration.ofMillis(timeout));
		builder.shutdownTimeout(Duration.ofMillis(shutdownTimeout));
		builder.poolConfig(genericObjectPoolConfig);
		LettuceClientConfiguration lettuceClientConfiguration = builder.build();

		// 根据配置和客户端配置创建连接
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration,
				lettuceClientConfiguration);
		lettuceConnectionFactory.afterPropertiesSet();
		return lettuceConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());

		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericFastJsonRedisSerializer());
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}

	@Bean
	public RedisProcessor redisProcessor(RedisTemplate<String, Serializable> redisTemplate) {
		log.info("注册[RedisProcessor]服务");
		Collection<Class<BasePo>> cols = ClassManager.instance().getClassByType(BasePo.class);
		return new RedisProcessor(cols, redisTemplate);
	}

}
