package com.cat.server.game.module.equip.attr;

import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.module.equip.domain.Equip;

/**
 * 装备属性根节点<br>
 * 装备总属性=基础属性+培养属性+镶嵌属性+隐藏属性+孔隐
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class EquipAttrRootNode extends AbstractEquipAttrNode{
	
	/** 装备基础属性*/
	private EquipAttrBaseNode baseNode;
	
	/** 卡牌属性*/
	private EquipAttrCardNode cardNode;
	
	/** 打孔隐藏属性*/
	private EquipAttrHoleHiddenNode holeNode;
	
	/** 升星属性*/
	private EquipAttrStarNode starNode;
	
	/** 加工(升星)隐藏属性*/
	private EquipAttrStarHiddenNode starHiddenNode;
	
	public EquipAttrRootNode(Equip equip) {
		super(equip);
		
		this.baseNode = new EquipAttrBaseNode(equip);
		this.addChild(baseNode);
		
		this.cardNode = new EquipAttrCardNode(equip);
		this.addChild(cardNode);
		
		this.holeNode = new EquipAttrHoleHiddenNode(equip);
		this.addChild(holeNode);
		
		this.starNode = new EquipAttrStarNode(equip);
		this.addChild(starNode);
		
		this.starHiddenNode = new EquipAttrStarHiddenNode(equip);
		this.addChild(starHiddenNode);
		
	}
	
	@Override
	public boolean isRoot() {
		return true;
	}
	
	@Override
    public boolean isLeaf() {
        return false;
    }
	
	 /**
     * 计算前缀增加的属性
     */
    @Override
    protected AttributeDictionary calculateAttrDic() {
        return this.getAttrDic();
    }

	public EquipAttrBaseNode getBaseNode() {
		return baseNode;
	}

	public void setBaseNode(EquipAttrBaseNode baseNode) {
		this.baseNode = baseNode;
	}

	public EquipAttrCardNode getCardNode() {
		return cardNode;
	}

	public void setCardNode(EquipAttrCardNode cardNode) {
		this.cardNode = cardNode;
	}

	public EquipAttrHoleHiddenNode getHoleNode() {
		return holeNode;
	}

	public void setHoleNode(EquipAttrHoleHiddenNode holeNode) {
		this.holeNode = holeNode;
	}

	public EquipAttrStarNode getStarNode() {
		return starNode;
	}

	public void setStarNode(EquipAttrStarNode starNode) {
		this.starNode = starNode;
	}

	public EquipAttrStarHiddenNode getStarHiddenNode() {
		return starHiddenNode;
	}

	public void setStarHiddenNode(EquipAttrStarHiddenNode starHiddenNode) {
		this.starHiddenNode = starHiddenNode;
	}
    
}
