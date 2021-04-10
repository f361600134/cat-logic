package com.cat.server.game.module.hero.attr;

import java.util.List;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigBuffer;
import com.cat.server.game.data.config.local.ConfigHeroInfo;
import com.cat.server.game.data.config.local.ConfigHeroStar;
import com.cat.server.game.data.config.local.ConfigHeroTalents;
import com.cat.server.game.module.attribute.domain.AttributeDictionary;
import com.cat.server.game.module.hero.domain.Hero;

import com.google.common.collect.Lists;

/**
 * 武将星格属性节点
 * 
 * @author Administrator
 *
 */
public class HeroStarNode extends AbstractHeroAttrNode {

	public HeroStarNode(Hero hero) {
		super(hero);
	}

	@Override
	public String getName() {
		return "Star";
	}

	/**
	 * 基础属性
	 */
	@Override
	protected AttributeDictionary calculateAttrDic() {
		AttributeDictionary attrDic = new AttributeDictionary();
		int starLevel = hero.getStarLevel();
		// 天赋属性
		List<Integer> starList = Lists.newArrayList();
		ConfigManager configManager = ConfigManager.getInstance();
		ConfigHeroInfo configHero = configManager.getConfig(ConfigHeroInfo.class, hero.getConfigId());
		for (int i = 0; i < starLevel; i++) {// 已激活的天赋
			int talentId = configHero.getTalents().get(i);
			ConfigHeroTalents configTalent = configManager.getConfig(ConfigHeroTalents.class, talentId);
			attrDic.addAttr(configTalent.getAttValuesMap());
			// 天赋buffer属性
			configTalent.getSkill().forEach(bufferId ->{
				ConfigBuffer configBuf = configManager.getConfig(ConfigBuffer.class, bufferId);
				if (configBuf == null || configBuf.getSeverClient() != 1) {
					return;
				}
				attrDic.addAttr(configBuf.getAttValuesMap());
			});
			starList.addAll(configTalent.getActiveStars());
		}
		// 星格属性
		starList.addAll(hero.getStarIds());
		starList.forEach(starId -> {
			ConfigHeroStar configStar = configManager.getConfig(ConfigHeroStar.class, starId);
			attrDic.addAttr(configStar.getAttValuesMap());
		});
		return attrDic;
	}

}
