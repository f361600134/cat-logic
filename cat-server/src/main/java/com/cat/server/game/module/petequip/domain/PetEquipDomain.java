package com.cat.server.game.module.petequip.domain;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleMultiDomain;
import com.cat.server.game.module.resource.IEquipResourceDomain;
import com.cat.server.game.module.resource.domain.PetEquipResourceDomain;

/**
* PetEquipDomain
* @author Jeremy
*/
public class PetEquipDomain extends AbstractModuleMultiDomain<Long, Long, PetEquip> implements IEquipResourceDomain<Long, PetEquip>{

	private static final Logger log = LoggerFactory.getLogger(PetEquipDomain.class);
	
	/**
	 * 资源代理对象
	 */
	private IEquipResourceDomain<Long, PetEquip> resourceDomainProxy;
	
	////////////业务代码////////////////////
	
	@Override
	public void afterInit() {
		this.resourceDomainProxy = PetEquipResourceDomain.create(getId(), getBeanMap());
	}
	
	@Override
	public PetEquip getBeanByConfigId(int configId) {
		return resourceDomainProxy.getBeanByConfigId(configId);
	}

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

	@Override
	public PetEquip wear(long holderId, int slot, PetEquip newEquip) {
		return resourceDomainProxy.wear(holderId, slot, newEquip);
	}

	@Override
	public void takeOut(long holderId, int slot, PetEquip petEquip) {
		resourceDomainProxy.takeOut(holderId, slot, petEquip);
	}
	
	@Override
	public Map<Integer, Long> getUsedEquips(long holderId) {
		return resourceDomainProxy.getUsedEquips(holderId);
	}
	
//	public static void main(String[] args) {
////Map<Integer, int[]> usedEquips = new HashMap<>();
////int[] ret = new int[6];
////usedEquips.put(1, ret);
////ret[0] = 1;
////System.out.println(Arrays.toString(usedEquips.get(1)));
//
//HashBasedTable<Integer, Integer, Integer> table = HashBasedTable.create();
//table.put(1, 0, 10001);
//table.put(1, 1, 10003);
//table.put(1, 2, 10004);
//table.put(1, 3, 10005);
//table.put(3, 4, 10006);
//table.put(2, 5, 10007);
//System.out.println(table);
//System.out.println(table.containsRow(1));
//System.out.println(table.containsColumn(2));
//System.out.println(table.get(1, 1));
//System.out.println(table.row(5));
//System.out.println(table.values());
//}
}
