package com.cat.server.admin.module.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.http.annatation.RequestMapping;
import com.cat.server.admin.helper.cache.BackstageDataManager;
import com.cat.server.admin.helper.result.IResult;
import com.cat.server.admin.helper.result.SystemCodeEnum;
import com.cat.server.admin.helper.result.SystemResult;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.player.domain.PlayerContext;

/**
 * @author Jeremy
 */
@Controller
@RequestMapping("/player")
public class PlayerHandler {
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private BackstageDataManager dataManager;
	
	@Autowired private IPlayerMapper playerMapper;
	
	/**
	 * 查看玩家信息
	 * http://localhost:8001/player/selectPlayer?playerId=1
	 */
	@RequestMapping("/selectPlayer")
	public IResult selectPlayer(long playerId) {
		Player player = null;
		//先查在线玩家
		PlayerContext context = playerService.getPlayerContext(playerId);
		if (context != null) {
			//如果玩家在线, 获取到player对象
			player = context.getPlayer();
		}else {
			player = dataManager.selectByPrimaryKey(Player.class, playerId);
		}
		if (player == null) {
			return SystemResult.build(SystemCodeEnum.PLAYER_NOT_FOUND);
		}
		BackstagePlayer backPlayer = playerMapper.toDto(player);
		//离线玩家数据封装成DTO,返回给后台
		return SystemResult.build(SystemCodeEnum.SUCCESS, backPlayer);
	}

}
