package com.cat.server.game.module.activity.component;

/**
 * 活动组件, 用于组合各种活动类型玩法
 * @author Jeremy
 */
public interface IActivityComponent {
	
	public void tick(long now);

	public void onRestart();

	public void onBegin(long now);

	public void onEnd(long now);

	public void onClear(long now);

	void initConfig();

}
