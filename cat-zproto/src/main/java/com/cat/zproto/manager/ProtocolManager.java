package com.cat.zproto.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cat.zproto.domain.module.ModuleDomain;
import com.cat.zproto.domain.proto.ProtocolDomain;

@Component
public class ProtocolManager {

	/**
	 * key: 版本号 value: module domain
	 */
	private final Map<String, ProtocolDomain> domainMap = new HashMap<>();

	/**
	 * 获取Moduledomain
	 * @param version
	 * @return
	 */
	public ProtocolDomain getDomain(String version) {
		return domainMap.get(version);
	}

	/**
	 * 获取或创建domain
	 * @param version
	 * @return
	 */
	public ProtocolDomain getOrCreateDomain(String version) {
		ProtocolDomain domain = getDomain(version);
		if (domain == null) {
			domain = new ProtocolDomain(version);
		}
		domainMap.put(version, domain);
		return domain;
	}

}
