package com.cat.server.game.module.gm.type.define;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.gm.annotation.Command;
import com.cat.server.game.module.gm.type.AbstractResourceCommand;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.google.common.collect.Lists;

/**
 * 资源GMService, 正数表示增加, 负数表示减少
 * @resource id1,num1,id2,num2
 * @resource 1001,10,1002,-10
 * @author Administrator
 */
@Command("@resource")
public class CommandResource extends AbstractResourceCommand{
	
	@Autowired
	private IResourceGroupService resourceGroupService;

	@Override
	public boolean doProcess(long playerId, String params) {
		List<Integer> paramList = this.formatIntegerList(params);
		ResourceGroup addGoods = new ResourceGroup();
		ResourceGroup deductGoods = new ResourceGroup();
		for(int i=1; i<paramList.size(); i=i+2)
		{
			int configId = paramList.get(i-1);
			int count = paramList.get(i);
			if (count > 0) {
				addGoods.addCount(configId, count);
			}else {
				deductGoods.addCount(configId, Math.abs(count));
			}
		} 
		resourceGroupService.reward(playerId, addGoods, NatureEnum.GM);
		resourceGroupService.cost(playerId, deductGoods, NatureEnum.GM);
		return true;
	}
	
	private List<Integer> formatIntegerList(String params){
		String[] paramArray = params.trim().split(",");
		List<Integer> paramList = Lists.newArrayList();
		for(String param : paramArray) {
			paramList.add(Integer.parseInt(param));
		}
		return paramList;
	}

}
