package com.cat.zproto.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cat.zproto.domain.module.ModuleDomain;

@Component
public class ModuleManager {

	/**
	 * key: 版本号 value: module domain
	 */
	private final Map<String, ModuleDomain> moduleDomainMap = new HashMap<>();

	/**
	 * 获取Moduledomain
	 * @param version
	 * @return
	 */
	public ModuleDomain getDomain(String version) {
		return moduleDomainMap.get(version);
	}

	/**
	 * 获取或创建domain
	 * @param version
	 * @return
	 */
	public ModuleDomain getOrCreateDomain(String version) {
		ModuleDomain domain = getDomain(version);
		if (domain == null) {
			domain = new ModuleDomain(version);
		}
		moduleDomainMap.put(version, domain);
		return domain;
	}

}
