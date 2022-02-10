package com.cat.server.game.module.resource.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.module.hero.domain.Hero;
import com.cat.server.game.module.mail.IMailService;
import com.cat.server.game.module.mail.assist.MailTemplate;
import com.cat.server.game.module.mail.assist.MailType;
import com.cat.server.game.module.resource.event.ResourceUpdateEvent;

/**
 * 武将资源处理代理类
 * @author Jeremy
 */
public class HeroResourceDomain extends AbstractResourceDomain<Long, Hero>{
	
	public static final int LIMIT = 999;

	HeroResourceDomain(long playerId) {
		super(playerId);
	}
	
	HeroResourceDomain(long playerId, Map<Long, Hero> heroMap) {
		super(playerId, heroMap);
	}
	
	public static HeroResourceDomain create(long playerId, Map<Long, Hero> beanMap) {
		HeroResourceDomain domain = new HeroResourceDomain(playerId, beanMap);
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
			getBeanMap().put(hero.getId(), hero);
			heros.add(hero);
			updateList.add(hero);
			//发送事件
			GameEventBus.getInstance().post(ResourceUpdateEvent.create(hero, 1));
		}
		return heros;
	}
	
	@Override
	public boolean deduct(Hero hero, int count) {
		hero.delete();
		getBeanMap().remove(hero.getId());
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
	
	/**
	 * 武将回收, 赠送给玩家有时效性的武将, 可以进行任意培养, 回收时消耗资源进行回收. 邮件通知给玩家.
	 */
	@Override
	public void beforeClearExpire(Hero hero) {
		Map<Integer, Integer> resource = hero.getUsedMaterials();
		//Send an email to the player notifying that the recycling was completed.
		long playerId = hero.getPlayerId();
		IMailService mailService = SpringContextHolder.getBean(IMailService.class);
		mailService.sendMail(MailType.PLAYER_MAIL.getMailType(), playerId, MailTemplate.HERO_RECYCLE.getMailConfigId(), resource, hero.getConfigId());
	}
}
