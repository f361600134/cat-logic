package com.cat.server.admin.module.player;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cat.net.http.annatation.RequestMapping;
import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.admin.helper.cache.BackstageDataManager;
import com.cat.server.admin.helper.result.IResult;
import com.cat.server.admin.helper.result.SystemCodeEnum;
import com.cat.server.admin.helper.result.SystemResult;
import com.cat.server.game.module.chat.domain.Chat;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.player.domain.PlayerContext;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

/**
 * @author Jeremy
 */
@Controller
@RequestMapping("/player")
public class PlayerHandler {
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private BackstageDataManager dataManager;
	
	/**
	 * 查看玩家信息
	 * http://localhost:8001/mail/selectPlayer?playerId=10001
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
		BackstagePlayer backPlayer = BackstagePlayer.create(player);
		//离线玩家数据封装成DTO,返回给后台
		return SystemResult.build(SystemCodeEnum.SUCCESS, backPlayer);
	}

}
