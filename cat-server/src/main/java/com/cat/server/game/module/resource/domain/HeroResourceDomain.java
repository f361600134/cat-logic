package com.cat.server.game.module.resource.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cat.server.game.module.hero.domain.Hero;

/**
 * 武将资源处理代理类
 * @author Jeremy
 */
public class HeroResourceDomain extends AbstractResourceDomain<Long, Hero>{
	
	public static final int LIMIT = 999;

	HeroResourceDomain(long playerId) {
		super(playerId);
	}
	
	
	public static HeroResourceDomain create(long playerId, Map<Long, Hero> beanMap) {
		HeroResourceDomain domain = new HeroResourceDomain(playerId);
		domain.setBeanMap(beanMap);
		return domain;
	}
	
	/**
	 * 武将消耗
	 */
	@Override
	public boolean costByConfigId(int configId, int count) {
		throw new UnsupportedOperationException("武将删除操作, 必须由玩家指定id, 才支持删除");
	}

	@Override
	public List<Hero> add(int configId, int count) {
		List<Hero> heros = new ArrayList<>();
		Hero hero = null;
		//	生成一个武将,需要唯一id
		for (int i = 0; i < count; i++) {
			hero = Hero.create(playerId, configId);
			beanMap.put(hero.getId(), hero);
			heros.add(hero);
			updateList.add(hero);
		}
		return heros;
	}
	
	@Override
	public boolean deduct(Hero hero, int count) {
		hero.delete();
		beanMap.remove(hero.getId());
		return true;
	}

	@Override
	public int getTotalLimit() {
		return LIMIT;
	}

	@Override
	public int getLimit(int configId) {
		//如需要, 通过配置获取到此类物品的最大限制
		return LIMIT;
	}

}
