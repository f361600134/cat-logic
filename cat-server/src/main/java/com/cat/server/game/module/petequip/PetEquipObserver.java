package com.cat.server.game.module.petequip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.core.event.IObserver;

/**
 * PetEquip事件类
 */
@Component
public class PetEquipObserver implements IObserver{

	@Autowired
    private PetEquipService petEquipService;


}