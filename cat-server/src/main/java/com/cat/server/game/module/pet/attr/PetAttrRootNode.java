package com.cat.server.game.module.pet.attr;

import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.module.pet.domain.Pet;

/**
 * 宠物属性资质节点
 * @auth Jeremy
 * @date 2022年4月9日上午9:04:03
 */
public class PetAttrRootNode extends AbstractPetAttrNode{
	
	/*** 基础属性节点*/
	private PetAttrBaseNode petBaseNode;
	/*** 属性资质节点*/
	private PetAttrAptitudeNode aptitudeNode;
	/*** 宠物属性点节点*/
	private PetAttrPointNode attrPointNode;
	/*** 前缀属性增益节点*/
	private PetAttrPrefixNode prefixNode;
	/*** 前缀属性点节点*/
	private PetAttrPrefixPointNode prefixAttrPointNode;

	public PetAttrRootNode(Pet pet) {
		super(pet);
		this.petBaseNode = new PetAttrBaseNode(pet);
		this.addChild(petBaseNode);
		
		this.aptitudeNode = new PetAttrAptitudeNode(pet);
		this.addChild(aptitudeNode);
		
		this.attrPointNode = new PetAttrPointNode(pet);
		this.addChild(attrPointNode);
		
		this.prefixNode = new PetAttrPrefixNode(pet);
		this.addChild(prefixNode);
		
		this.prefixAttrPointNode = new PetAttrPrefixPointNode(pet);
		this.addChild(prefixAttrPointNode);
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

	public PetAttrBaseNode getPetBaseNode() {
		return petBaseNode;
	}

	public PetAttrAptitudeNode getAptitudeNode() {
		return aptitudeNode;
	}

	public PetAttrPointNode getAttrPointNode() {
		return attrPointNode;
	}

	public PetAttrPrefixNode getPrefixNode() {
		return prefixNode;
	}

	public PetAttrPrefixPointNode getPrefixAttrPointNode() {
		return prefixAttrPointNode;
	}
	
}
