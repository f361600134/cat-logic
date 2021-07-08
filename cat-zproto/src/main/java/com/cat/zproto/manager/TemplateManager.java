package com.cat.zproto.manager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.cat.zproto.domain.template.TemplateDomain;

@Component
public class TemplateManager {
	
	/**
	 * key:类型 value: module domain
	 */
	private final Map<Integer, TemplateDomain> domainMap = new HashMap<>();

	/**
	 * 获取Moduledomain
	 * @param version
	 * @return
	 */
	public TemplateDomain getDomain(int type) {
		return domainMap.get(type);
	}

	/**
	 * 获取或创建domain
	 * @param version
	 * @return
	 */
	public TemplateDomain getOrCreateDomain(int type) {
		TemplateDomain domain = getDomain(type);
		if (domain == null) {
			domain = new TemplateDomain(type, new LinkedHashMap<>());
		}
		domainMap.put(type, domain);
		return domain;
	}

}
