package com.cat.battle;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableDubbo(scanBasePackages = "com.cat.battle.service")
@PropertySource("classpath:/dubbo.properties")
public class ProviderConfiguration {
	
}