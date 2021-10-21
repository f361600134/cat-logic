package com.cat.server.game.module.shadow;

import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.shadow.domain.Shadow;

/**
 * Shadow接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IShadowService {
	
	/**
	 * 根据玩家id获取影子数据<br>
	 * 先从缓存获取, 获取不到从数据库获取, 数据库获取不到返回null
	 * @return null or 影子数据
	 * @param playerId 玩家id
	 * @date 2021年10月20日下午7:26:57
	 */
	public Shadow get(long playerId);
	
	/**
	 * 根据玩家id获取影子数据<br>
	 * 先从缓存获取, 获取不到从数据库获取, 数据库获取不到创建一个影子对象
	 * @return null or 影子数据
	 * @param playerId 玩家id
	 * @date 2021年10月20日下午7:27:57
	 */
	public Shadow getOrCreate(long playerId);
	
	/**
	 * 当玩家的信息发生了修改, 影子数据对应变动<br>
	 * 这里玩家数据直接覆盖影子数据内容, 在内存中操作<br>
	 * 数据不需要立刻保存,被挤出缓存时/或停服时保存
	 * @param Player 玩家对象
	 * @date 2021年10月21日上午10:20:57
	 */
	public void onPlayerUpdate(Player player);

}