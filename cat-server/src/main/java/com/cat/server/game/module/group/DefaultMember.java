package com.cat.server.game.module.group;

/**
 * 默认团体成员
 * @author Jeremy
 */
public class DefaultMember implements IMember{
	
	/**
	 * 成员id,即玩家id
	 */
	private long memberId;
	
	/**
	 * 职位, 可以根据职位获取到权限
	 * 0:默认成员,无职位
	 */
	private int position;
	
	public DefaultMember() {
	}
	
	public DefaultMember(long memberId) {
		this.memberId = memberId;
		//0表示默认职位,无职位
		this.position = 0;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	@Override
	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public long getMemberId() {
		return memberId;
	}

	@Override
	public int getPosition() {
		return position;
	}

}
