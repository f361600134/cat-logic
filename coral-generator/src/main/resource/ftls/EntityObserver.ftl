package com.cat.server.game.module.${entityName?lower_case}.event;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.${entityName?lower_case}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${entityName}事件类
 */
@Component
public class ${entityName}Observer implements IObserver{

	@Autowired
    private ${entityName}Service ${entityName?uncap_first}Service;


}