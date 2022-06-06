package com.cat.server.game.module.petequip.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.data.config.local.ConfigPetEquip;
import com.cat.server.game.module.resource.IEquipResourceDomain;
import com.cat.server.game.module.resource.IResourceDomain;
import com.cat.server.game.module.resource.domain.PetEquipResourceDomain;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
* PetEquipDomain
* @author Jeremy
*/
public class PetEquipDomain extends AbstractModuleMultiDomain<Long, Long, PetEquip> implements IResourceDomain<Long, PetEquip>{

	private static final Logger log = LoggerFactory.getLogger(PetEquipDomain.class);
	
	/**
	 * 资源代理对象
	 */
	private IEquipResourceDomain<Long, PetEquip> resourceDomainProxy;
	
	/**
	 * 这种处理方式, 初始化时,需要根据装备的配置信息获取到槽位
	 * @param row PlayerId
	 * @param column index 槽位
	 * @param value equipId
	 */
	private final transient Table<Long, Integer, Long> usedEquips = HashBasedTable.create();
	
	public PetEquipDomain(){
		
	}

//	/**
//	 * 获取已使用的装备Ids
//	 * @return Collection<Long>  已使用的装备列表id
//	 * @date 2022年6月3日下午10:31:32
//	 */
//	public Collection<Long> getUsedEquipIds(long holderId){
//		if (!usedEquips.containsRow(holderId)) {
//			return Collections.emptyList();
//		}
//		return usedEquips.row(holderId).values();
//	}
//	
//	/**
//	 * 获取已使用的装备列表
//	 * @return Collection<Long>  已使用的装备列表id
//	 * @date 2022年6月3日下午10:31:32
//	 */
//	public List<PetEquip> getUsedEquips(long holderId){
//		Collection<Long> equipIds = this.getUsedEquipIds(holderId);
//		List<PetEquip> ret = new ArrayList<>();
//		for (Long equipId : equipIds) {
//			ret.add(this.getBean(equipId));
//		}
//		return ret;
//	}
	
	/**
	 * @param holderId 持有者id
	 * @return 返回装备列表
	 * @return Map<Long,PetEquip>  装备集
	 * @date 2022年6月5日下午10:38:23
	 */
	public Map<Integer, PetEquip> getUsedEquipMap(long holderId){
		if (!usedEquips.containsRow(holderId)) {
			return Collections.emptyMap();
		}
		Map<Integer, PetEquip> ret = new HashMap<>();
		Map<Integer, Long> equipIdMap = usedEquips.row(holderId);
		for (Integer slot : equipIdMap.keySet()) {
			long equipId = equipIdMap.get(slot);
			ret.put(slot, this.getBean(equipId));
		}
		return ret;
	}
	
	/**
	 * 替换新装备
	 * @return void  
	 * @date 2022年6月5日下午10:50:03
	 */
	public void replaceNewEquip(int slot, long holderId, PetEquip newPetEquip) {
		if (this.usedEquips.contains(holderId, slot)) {
			long oldPetEquipId = this.usedEquips.get(holderId, slot);
			PetEquip oldPetEquip = this.getBean(oldPetEquipId);
			if (oldPetEquip != null) {
				//之前有穿过宠物装备,替换下来,直接设置持有者为0
				oldPetEquip.setHolder(0L);
			}
		}
		//新装备设置新的持有者id
		newPetEquip.setHolder(holderId);
		this.usedEquips.put(holderId, slot, newPetEquip.getId());
	}
	
	////////////业务代码////////////////////
	
	
	@Override
	public void afterInit() {
		this.resourceDomainProxy = PetEquipResourceDomain.create(getId(), getBeanMap());
		for (PetEquip equip : getBeanMap().values()) {
			if (equip.getHolder() != 0L) {
				continue;
			}
			ConfigPetEquip configPetEquip = ConfigManager.getInstance().getConfig(ConfigPetEquip.class, equip.getConfigId());
			if (configPetEquip == null) {
				continue;
			}
			usedEquips.put(equip.getHolder(), configPetEquip.getSlot(), equip.getId());
		}
	}
	
	@Override
	public PetEquip getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

	//@Override
	//public Collection<PetEquip> getBeansByConfigId(int configId) {
	//	return resourceDomainProxy.getBeansByConfigId(configId);
	//}

	@Override
	public boolean checkAdd(int configId, int count) {
		return resourceDomainProxy.checkAdd(configId, count);
	}

	@Override
	public List<PetEquip> add(int configId, int count) {
		return resourceDomainProxy.add(configId, count);
	}

	@Override
	public boolean checkEnough(int configId, int count) {
		return resourceDomainProxy.checkEnough(configId, count);
	}

	@Override
	public boolean costByConfigId(int configId, int count) {
		return resourceDomainProxy.costByConfigId(configId, count);
	}

	@Override
	public boolean costById(Long id, int count) {
		return resourceDomainProxy.costById(id, count);
	}

	@Override
	public List<PetEquip> getAndClearUpdateList() {
		return resourceDomainProxy.getAndClearUpdateList();
	}


	@Override
	public int getCount(int configId) {
		return resourceDomainProxy.getCount(configId);
	}


	@Override
	public List<Long> getAndClearDeleteList() {
		return resourceDomainProxy.getAndClearDeleteList();
	}


	@Override
	public void clearExpire(int configId) {
		resourceDomainProxy.clearExpire(configId);
	}
	
//	public static void main(String[] args) {
//		Map<Integer, int[]> usedEquips = new HashMap<>();
//		int[] ret = new int[6];
//		usedEquips.put(1, ret);
//		ret[0] = 1;
//		System.out.println(Arrays.toString(usedEquips.get(1)));
//		
//		HashBasedTable<Integer, Integer, Integer> table = HashBasedTable.create();
//		table.put(1, 0, 10001);
//		table.put(1, 1, 10003);
//		table.put(1, 2, 10004);
//		table.put(1, 3, 10005);
//		table.put(3, 4, 10006);
//		table.put(2, 5, 10007);
//		System.out.println(table);
//		System.out.println(table.containsRow(1));
//		System.out.println(table.containsColumn(2));
//		System.out.println(table.get(1, 1));
//		System.out.println(table.row(1).values());
//		System.out.println(table.values());
//	}
}
