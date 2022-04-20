package com.cat.server.game.module.hero.attr;

import java.util.List;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.base.ConfigBufferBase;
import com.cat.server.game.data.config.local.base.ConfigHeroInfoBase;
import com.cat.server.game.data.config.local.base.ConfigHeroStarBase;
import com.cat.server.game.data.config.local.base.ConfigHeroTalentsBase;
import com.cat.server.game.helper.attribute.AttributeDictionary;
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
		ConfigHeroInfoBase configHero = configManager.getConfig(ConfigHeroInfoBase.class, hero.getConfigId());
		for (int i = 0; i < starLevel; i++) {// 已激活的天赋
			int talentId = configHero.getTalents().get(i);
			ConfigHeroTalentsBase configTalent = configManager.getConfig(ConfigHeroTalentsBase.class, talentId);
			attrDic.addAttr(configTalent.getAttValuesMap());
			// 天赋buffer属性
			configTalent.getSkill().forEach(bufferId ->{
				ConfigBufferBase configBuf = configManager.getConfig(ConfigBufferBase.class, bufferId);
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
			ConfigHeroStarBase configStar = configManager.getConfig(ConfigHeroStarBase.class, starId);
			attrDic.addAttr(configStar.getAttValuesMap());
		});
		return attrDic;
	}

}
