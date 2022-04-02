package com.cat.server.game.module.resource.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

@Deprecated
public final class ImmutableResourceGroup extends ResourceGroup {
	
	public ImmutableResourceGroup() {
    	this.dictionary = Collections.unmodifiableMap(new HashMap<>());
    }
	
	public ImmutableResourceGroup(ResourceGroup group) {
    	this.dictionary = Collections.unmodifiableMap(group.getDictionary());
    }
	
    public ImmutableResourceGroup(Map<Integer, Integer> dictionary) {
    	this.dictionary = Collections.unmodifiableMap(dictionary);
    }
    
    public static void main(String[] args) {
    	Map<Integer, Integer> map = Maps.newHashMap();
    	map.put(1, 1);
    	ResourceGroup resource = new ImmutableResourceGroup(map);
    	System.out.println(resource.getDictionary());
    	resource.addCount(1, 1);
	}
}
