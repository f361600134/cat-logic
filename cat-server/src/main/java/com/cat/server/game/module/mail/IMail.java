package com.cat.server.game.module.mail;

import java.util.Map;

public interface IMail {
	
	/**
	 * 邮件id
	 * @return
	 */
	public long getId();
	
	/**
	 * 标题
	 * @return
	 */
	public String getTitle();
	
	/**
	 * 内容
	 * @return
	 */
	public String getContent();
	
	/**
	 * 奖励
	 * @return
	 */
	public Map<Integer, Integer> getRewardMap();
	
	/**
	 * 创建时间
	 * @return
	 */
	public long getCreateTime();
	
	/**
	 * 过期时间
	 * @return
	 */
	public long getExpireTime();
	
	/**
	 * 是否过期
	 * @return
	 */
	public boolean isExpired();
	
}
