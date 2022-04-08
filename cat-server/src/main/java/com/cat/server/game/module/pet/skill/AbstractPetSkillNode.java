package com.cat.server.game.module.pet.skill;

import com.cat.server.game.module.pet.domain.Pet;
import com.cat.server.game.module.skill.domain.AbstractSkillNode;

public abstract class AbstractPetSkillNode extends AbstractSkillNode {

	protected final Pet pet;

	public AbstractPetSkillNode(Pet pet) {
		this.pet = pet;
	}

	public Pet getPet() {
		return pet;
	}
	
	 /**
     * 玩家id-宠物唯一id-宠物配置id<br>
     * eg:xxxx-202201291001-202201292001-40001
     */
    @Override
    public String getName() {
    	String name = String.valueOf(pet.getPlayerId())
		.concat("-").concat(String.valueOf(pet.getUniqueId()))
		.concat("-" ).concat(String.valueOf(pet.getConfigId()));
    	return this.getClass().getSimpleName().concat("-").concat(name);
    }

}
