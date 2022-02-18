package com.cat.server.admin.module.player;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.cat.server.admin.helper.mapstruct.IBaseMapper;
import com.cat.server.game.module.player.domain.Player;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPlayerMapper extends IBaseMapper<BackstagePlayer, Player> {
	
//	@Mappings({
//        @Mapping(target = "regTime", expression = "java(player.getRegTime().getTime())"),
//        @Mapping(target = "lastTime", expression = "java(player.getLastTime().getTime())")
//	})
//	BackstagePlayer toDto(Player player);
//	
//	@Mappings({
//        @Mapping(target = "regTime", expression = "java(new java.util.Date(backstagePlayer.getRegTime()))"),
//        @Mapping(target = "lastTime", expression = "java(new java.util.Date(backstagePlayer.getLastTime()))")
//	})
//	Player toEntity(BackstagePlayer backstagePlayer);
	
}
