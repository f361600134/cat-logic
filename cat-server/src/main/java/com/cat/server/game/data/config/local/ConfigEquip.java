package com.cat.server.game.data.config.local;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.game.data.config.local.base.ConfigEquipBase;
import com.cat.server.game.data.config.local.ext.ListMapParse;
import com.cat.server.game.data.config.local.interfaces.IConfigResource;


/**
 * zb.装备.xlsx<br>
 * equip.json<br>
 * @author auto gen
 */
@ConfigPath("equip.json")
public class ConfigEquip extends ConfigEquipBase implements IConfigResource{
	
	/**
	 * 隐藏属性列表
	 */
	@JSONField(name="starHiddenAttrs", deserializeUsing = ListMapParse.class)
	private List<Map<Integer, Integer>> starHiddenAttrList;
	
	/**
	 * 隐藏属性列表
	 */
	@JSONField(name="holeHiddenAttrs", deserializeUsing = ListMapParse.class)
	private List<Map<Integer, Integer>> holeHiddenAttrList;
	
	/**
	 * 通过下标获取加工隐藏属性
	 */
	public Map<Integer, Integer> getStarHiddenAttr(int index) {
		return starHiddenAttrList.get(index);
	}
	
	/**
	 * 通过下标获取打孔隐藏属性
	 */
	public Map<Integer, Integer> getHoleHiddenAttr(int index) {
		return holeHiddenAttrList.get(index);
	}

	@Override
	public String getName() {
		return null;
	}

}
