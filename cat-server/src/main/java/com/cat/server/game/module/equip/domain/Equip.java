package com.cat.server.game.module.equip.domain;

import java.util.ArrayList;
import java.util.List;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBEquip.PBEquipDto;
import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.equip.attr.EquipAttrRootNode;
import com.cat.server.game.module.equip.proto.PBEquipDtoBuilder;
import com.cat.server.game.module.resource.IEquip;

/**
 * 装备对象
* @author Jeremy
*/
@PO(name = "equip")
public class Equip extends EquipPo implements IPersistence, IEquip{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3159723799785160474L;

	/*** 卡牌列表 */
	@Column(value = PROP_CARDSTR)
	private List<Integer> cards = new ArrayList<>();
	
	/*** 卡牌属性列表 */
	@Column(value = PROP_CARDATTRSTR)
	private AttributeDictionary catdAttrDic = new AttributeDictionary();

	/*** 附魂属性 */
	@Column(value = PROP_ENCHANTMENTATTRSTR)
	private AttributeDictionary enchantmentAttrDic = new AttributeDictionary();
	
	/**
	 * 宠物技能根节点
	 */
	private transient final EquipAttrRootNode attrRootNode = new EquipAttrRootNode(this);
	
	public Equip() {

	}
	
	public Equip(long playerId, long uniqueId, int configId) {
		this.playerId = playerId;
		this.id = uniqueId;
		this.configId = configId;
	}

	@Override
	public long getUniqueId() {
		return this.getId();
	}

	@Override
	public int getCount() {
		return 1;
	}

	public List<Integer> getCards() {
		return cards;
	}

	public void setCards(List<Integer> cards) {
		this.cards = cards;
	}

	public AttributeDictionary getCatdAttrDic() {
		return catdAttrDic;
	}

	public void setCatdAttrDic(AttributeDictionary catdAttrDic) {
		this.catdAttrDic = catdAttrDic;
	}

	public AttributeDictionary getEnchantmentAttrDic() {
		return enchantmentAttrDic;
	}

	public void setEnchantmentAttrDic(AttributeDictionary enchantmentAttrDic) {
		this.enchantmentAttrDic = enchantmentAttrDic;
	}

//	public AttributeDictionary getHolehiddenAttrDic() {
//		return holehiddenAttrDic;
//	}
//
//	public void setHolehiddenAttrDic(AttributeDictionary holehiddenAttrDic) {
//		this.holehiddenAttrDic = holehiddenAttrDic;
//	}

//	public AttributeDictionary getStarhiddenAttrDic() {
//		return starhiddenAttrDic;
//	}
//
//	public void setStarhiddenAttrDic(AttributeDictionary starhiddenAttrDic) {
//		this.starhiddenAttrDic = starhiddenAttrDic;
//	}
	
	public EquipAttrRootNode getAttrRootNode() {
		return attrRootNode;
	}

	/**
	 * 装备转协议对象
	 * @return  
	 * @return PBEquipDto  
	 * @date 2022年6月3日下午9:56:40
	 */
	public PBEquipDto toProto() {
		PBEquipDtoBuilder dto = PBEquipDtoBuilder.newInstance();
		dto.setUniqueId(this.getId());
		dto.setConfigId(this.getConfigId());
		dto.setHolderId(this.getHolder());
		return dto.build();
	}
	

	/**
	 * 创建一个带有持有者的武器对象
	 * @param playerId 玩家id
	 * @param configId 武将配置
	 * @return Equip
	 * @date 2021年1月17日上午12:22:09
	 */
	public static Equip create(long playerId, int configId) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		long id = generator.nextId();
		Equip equip = new Equip(playerId, id, configId);
		equip.save();
		return equip;
	}
	
}
