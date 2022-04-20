package com.cat.server.game.module.artifact.attr;

import com.cat.server.game.helper.attribute.AbstractAttributeNode;
import com.cat.server.game.helper.attribute.AttributeDictionary;
import com.cat.server.game.module.artifact.domain.Artifact;

/**
 * 武将星格属性节点
 * 
 * @author Administrator
 *
 */
public class ArtifactAttrNode extends AbstractAttributeNode {
	
	private Artifact artifact;

	public ArtifactAttrNode(Artifact artifact) {
		this.artifact = artifact;
	}

	@Override
	public String getName() {
		return "Artifact";
	}

	/**
	 * 基础属性
	 */
	@Override
	protected AttributeDictionary calculateAttrDic() {
		AttributeDictionary attrDic = new AttributeDictionary();
		//不实现细节了, 这里计算出神兵的属性返回
		return attrDic;
	}

}
