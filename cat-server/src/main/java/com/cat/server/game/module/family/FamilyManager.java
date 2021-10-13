package com.cat.server.game.module.family;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.family.domain.FamilyDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FamilyManager, 通过服务器id, 关联本服内所有的家族
 * @author Jeremy
 */
@Component
class FamilyManager extends AbstractModuleManager<Integer, FamilyDomain> {

    @Autowired private ServerConfig serverConfig;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public FamilyDomain getFromDb(Integer id) {
        try {
            FamilyDomain domain = clazz.newInstance();
			List list = process.selectByIndex(domain.getBasePoClazz(), new String[]{Family.PROP_CURSERVERID}, new Object[] {id});
            if (list.isEmpty()) {
                //	无数据创建
                domain.initData(id);
            }else {
                //	有数据初始化
                domain.initData(id, list);
            }
            return domain;
        }catch (Exception e) {
            logger.error("getFromDb error", e);
        }
        return null;
    }

    @Override
    public void init() {
        //初始化, 加载一次, 初始化所有活动数据
        getFromDb(serverConfig.getServerId());
    }
}
