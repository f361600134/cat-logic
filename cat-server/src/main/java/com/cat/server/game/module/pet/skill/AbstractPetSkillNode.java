package com.cat.server.game.module.pet.skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cat.server.game.data.proto.PBItem.PBPairInfo;
import com.cat.server.game.module.item.proto.PBPairInfoBuilder;
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
    
	/**
	 * 技能信息转协议对象
	 * @return
	 */
	public Collection<PBPairInfo> toProto(){
		List<PBPairInfo> ret = new ArrayList<>();
		this.getDic().getDictionary().forEach((skillId, skillLv)->{
			PBPairInfoBuilder builder = PBPairInfoBuilder.newInstance();
			builder.setConfigId(skillId);
			builder.setValue(skillLv);
			ret.add(builder.build());
		});
		return ret;
	}

}
