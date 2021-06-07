package com.cat.server.game.module.${entity.getEntityName()?lower_case}.event;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.${entity.getEntityName()?lower_case}.service.${entity.getEntityName()}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${entity.getEntityName()}事件类
 */
@Component
public class ${entity.getEntityName()}Observer implements IObserver{

	@Autowired
    private ${entity.getEntityName()}Service ${entity.getEntityName()?uncap_first}Service;


}