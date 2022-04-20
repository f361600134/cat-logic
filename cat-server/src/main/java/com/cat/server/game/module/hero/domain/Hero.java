package com.cat.server.game.module.hero.domain;

import java.util.List;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.helper.attribute.IAttributeEntity;
import com.cat.server.game.helper.attribute.IAttributeNode;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.hero.attr.HeroRootNode;
import com.cat.server.game.module.resource.IResource;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.google.common.collect.Lists;

/**	 武将实体类
 * @author Jeremy
 */
@PO(name = "hero")
public class Hero extends HeroPo implements IAttributeEntity, IPersistence, IResource {

	private static final long serialVersionUID = -6477234322349578720L;

	/**
	 * 	已激活星格id列表(不包含未激活)
	 */
	@Column(PROP_STARIDSTR)
	private List<Integer> starIds;
	
	/**
	 * 	培养的消耗:{configId:num,configId:num}
	 */
	@Column(PROP_USEDMATERIALSTR)
	private ResourceGroup usedMaterials;

	/**
	 * 	总星级 天赋index,从0开始
	 */
	private transient int starLevel;
	
	/**
	 * 武将属性根节点
	 */
	private transient HeroRootNode heroAttrNode;

	public Hero() {
		this.starIds = Lists.newArrayList();
		this.usedMaterials = new ResourceGroup();
		this.heroAttrNode = new HeroRootNode(this);
	}
	
	public Hero(long playerId, long id, int configId) {
		this.id = id;
		this.playerId = playerId;
		this.configId = configId;
		this.starIds = Lists.newArrayList();
		this.usedMaterials = new ResourceGroup();
		this.heroAttrNode = new HeroRootNode(this);
	}

	public List<Integer> getStarIds() {
		return starIds;
	}

	public int getStarLevel() {
		return starLevel;
	}
	
	public ResourceGroup getUsedMaterials() {
		return usedMaterials;
	}

	@Override
	public IAttributeNode getAttributeNode() {
		return heroAttrNode;
	}
	
	/**
	 * 创建一个武将对象
	 * @note 通过spring获取到uuid生成器,创建一个武将对象,通过spring获取数据存储器,进行保存
	 * @param playerId 玩家id
	 * @param configId 武将配置
	 * @return  
	 * @return Hero  
	 * @date 2021年1月17日上午12:22:09
	 */
	public static Hero create(long playerId, int configId) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		long id = generator.nextId();
		Hero hero = new Hero(playerId, id, configId);
		hero.save();
		return hero;
	}

	@Override
	public long getUniqueId() {
		return getId();
	}

	@Override
	public int getCount() {
		return 1;
	}

}
