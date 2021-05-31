package com.cat.server.game.module.${entityName?lower_case}.manager;

import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.${entityName?lower_case}.domain.${entityName}Domain;
import org.springframework.stereotype.Component;

/**
* @author Jeremy
*/
@Component
public class ${entityName}Manager extends AbstractModuleManager<Long, ${entityName}Domain>{

}
