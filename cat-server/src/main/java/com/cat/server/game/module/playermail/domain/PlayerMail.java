package com.cat.server.game.module.playermail.domain;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBMail;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.playermail.assist.PlayerMailConstant;
import com.cat.server.game.module.playermail.proto.PBMailInfoBuilder;
import com.cat.server.game.module.resource.domain.ResourceMap;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.cat.server.utils.DateUtils;
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
	
	private Map<Integer, Integer> rewardMap = new HashMap<>();

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
	
//	public ResourceMap getRewardMap() {
//		return rewardMap;
//	}
//
//	public void setRewardMap(ResourceMap rewardMap) {
//		this.rewardMap = rewardMap;
//	}

	/**
	 * 创建邮件对象
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励内容
	 * @return 邮件对象
	 */
	public static PlayerMail create(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		PlayerMail mail = new PlayerMail(playerId);
		mail.setId(generator.nextId());//邮件id唯一,使用雪花生成器生成
		mail.setTitle(title);
		mail.setContent(content);
		long now = TimeUtil.now();
		mail.setCreateTime(now);
		//计算过期时间
		long expireTime = now + TimeUtil.DAY_MILLISECONDS * expiredDays;
		mail.setExpireTime(expireTime);
		mail.save();
		return mail;
	}
	
	public Map<Integer, Integer> getRewardMap() {
		return rewardMap;
	}

	public void setRewardMap(Map<Integer, Integer> rewardMap) {
		this.rewardMap = rewardMap;
	}

	/**
	 * 实体对象转协议对象
	 * @return 邮件序列化成消息对象
	 */
	public PBMail.PBMailInfo toProto() {
		PBMailInfoBuilder builder = PBMailInfoBuilder.newInstance();
		builder.setMailId(this.getId());
		builder.setState(this.getState());
		builder.setContent(this.getContent());
		builder.setTitle(this.getTitle());
		//格式化日期
		String createDate = DateFormatUtils.format(Calendar.getInstance().getTime(), DateUtils.PATTERN_NORMAL);
		builder.setDate(createDate);
		//奖励格式化
		builder.addAllRewards(ResourceHelper.toCollProto(rewardMap));
		return builder.build();
	}
	
	/**
	 * 是否已经领取
	 * @return
	 */
	public boolean isRewarded() {
		if (this.getState() == PlayerMailConstant.REWARD) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否过期
	 * @return
	 */
	public boolean isExpired() {
		if ((TimeUtil.now() / 1000) >= this.getExpireTime()) {//截止时间大于当前时间,表示过期
			return true;
		}
		return false;
	}
	
	/**
	 * 是否可以删除
	 * @return
	 */
	public boolean canDel() {
		if (this.getState() == PlayerMailConstant.READ && this.getRewardMap().isEmpty()) {
			//状态为已读,并且没有奖励配置 可以删除
			return true;
		}else if (this.isRewarded()) {
			//领过奖,可以删除
			return true;
		}else if (this.isExpired()) {
			//过期可以删除
			return true;
		}
		return false;
	}
	
}
