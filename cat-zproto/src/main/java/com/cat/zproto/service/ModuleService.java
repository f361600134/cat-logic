package com.cat.zproto.service;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.cat.zproto.domain.ModuleEntity;

@Service
public class ModuleService {

	
	 /**
     * 模块id,模块
     */
    private static final Map<Integer, ModuleEntity> moduleEntityMap = new TreeMap<>();
    
    static {
    	ModuleEntity enetity = new ModuleEntity();
    	enetity.setId(100);
    	enetity.setName("login");
    	enetity.setComment("登录");
    	enetity.getEndPoints().add(1);
    	enetity.getEndPoints().add(2);
    	moduleEntityMap.put(enetity.getId(), enetity);
    }

	public Map<Integer, ModuleEntity> getModuleentitymap() {
		return moduleEntityMap;
	}
    
}
