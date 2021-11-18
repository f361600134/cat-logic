package com.cat.server.game.module.recycle;

/**
 * 道具回收接口
 * @author Jeremy
 */
public interface IRecycleService {
	
	/**
	 * 检测道具是否可以被回收<br>
	 * @return true: 可以被回收, false:不可以被回收
	 */
	public boolean checkRecycle(long playerId, long uniqueId, int configid);
	
	/**
	 * 回收道具
	 */
	public void doRecycle(long playerId, long uniqueId, int configid);
	
}
