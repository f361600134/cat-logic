package com.cat.server.game.module.playermail.domain;

import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.utils.TimeUtil;

/**
* @author Jeremy
*/
@PO(name = "player_mail")
public class PlayerMail extends PlayerMailPo implements IPersistence{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942618617969545324L;
	
	@Column(PROP_REWARDS)
	private Map<Integer, Integer> rewardStr;

	public PlayerMail() {
		
	}
	
	public PlayerMail(long playerId) {
		this.playerId = playerId;
	}
	
	@Override
	public Object key() {
		return getPlayerId();
	}

	@Override
	public String keyColumn() {
		return PROP_PLAYERID;
	}
	
	/**
	 * 创建邮件对象
	 * @param playerId
	 * @param title
	 * @param content
	 * @param expiredDays
	 * @param rewards
	 * @return
	 */
	public static PlayerMail create(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		PlayerMail mail = new PlayerMail(playerId);
		mail.setTitle(title);
		mail.setContent(content);
		long now = TimeUtil.now();
		mail.setCreateTime(now);
		//计算过期时间
		long expireTime = now + TimeUtil.DAY_MILLISECONDS * expiredDays;
		mail.setExpireTime(expireTime);
		return mail;
	}
	
	/**
	 * 实体对象转协议对象
	 * @return
	 */
	public Object toProto() {
//		PBPlayer.EmailInfo.Builder builder = PBPlayer.EmailInfo.newBuilder();
//		builder.setId(this.getId());
//		builder.setEmailTitle(this.getTitle());
//		builder.setContent(this.getContent());
//		int beginTime = TimeUtils.currentTimeSeconds() >= this.getCreateAt() ? TimeUtils.currentTimeSeconds() - this.getCreateAt() : 0;
//		builder.setBeginTime(beginTime+"");
//		int endTime = this.getEndTime() > TimeUtils.currentTimeSeconds() ? this.getEndTime() - TimeUtils.currentTimeSeconds() : 0;
//		builder.setEndTime(endTime+"");
//		builder.setState(this.getState());
//		
//		ItemInfoBuilder infoBuilder = null;
//		for (Integer key : this.getRewardMap().keySet()) {
//			infoBuilder = ItemInfoBuilder.newInstance();
//			infoBuilder.addReward(key, this.getRewardMap().get(key));
//			builder.addRewardList(infoBuilder.build());
//		}
		return null;
	}
	
}
