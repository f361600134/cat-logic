package com.cat.server.game.module.petequip;

import com.cat.server.core.event.IObserver;
import com.cat.server.game.module.petequip.PetEquipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PetEquip事件类
 */
@Component
public class PetEquipObserver implements IObserver{

	@Autowired
    private PetEquipService petEquipService;


}