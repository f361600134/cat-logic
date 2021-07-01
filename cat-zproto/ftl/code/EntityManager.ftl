package com.cat.server.game.module.${entity.getEntityName()?lower_case}.manager;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.${entity.getEntityName()?lower_case}.domain.${entity.getEntityName()}Domain;
import org.springframework.stereotype.Component;

/**
* @author Jeremy
*/
@Component
public class ${entity.getEntityName()}Manager extends AbstractModuleManager<Long, ${entity.getEntityName()}Domain>{

}
