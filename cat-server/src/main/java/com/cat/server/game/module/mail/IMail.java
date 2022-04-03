package com.cat.server.game.module.mail;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBMail;
import com.cat.server.game.module.mail.assist.MailState;
import com.cat.server.game.module.mail.proto.PBMailInfoBuilder;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.cat.server.utils.DateUtils;
import com.cat.server.utils.TimeUtil;

public interface IMail extends IPersistence{
	
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
	 * 获取状态
	 * @return  
	 * @return byte  
	 * @date 2021年11月28日上午9:36:46
	 */
	public int getState(long playerId);
	
	/**
	 * 奖励
	 * @return
	 */
	public ResourceGroup getRewardMap();
	
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
	 * 添加新状态
	 * @param state  
	 * @return void  
	 * @date 2021年11月28日上午9:09:24
	 */
	public void addState(long playerId, MailState state);
	
	/**
	 * 是否获取奖励了
	 * @param playerId
	 * @return  
	 * @return boolean  
	 * @date 2021年11月28日上午9:48:25
	 */
	default public boolean isRewarded(long playerId) {
		return getState(playerId) == MailState.REWARD.getState();
	}
	
	/**
	 * 是否过期
	 * @return true:已过期
	 */
	default public boolean isExpired() {
		//截止时间大于当前时间,表示过期
		return (TimeUnit.MILLISECONDS.toSeconds(TimeUtil.now())) >= this.getExpireTime();
	}
	
	/**
	 * 是否可以删除
	 * @return true:可删除
	 */
	default public boolean canDel(long playerId) {
		if (this.getState(playerId) == MailState.READ.getState() && this.getRewardMap().empty()) {
			//状态为已读,并且没有奖励配置 可以删除
			return true;
		}else if (this.isRewarded(playerId)) {
			//领过奖,可以删除
			return true;
		}else if (this.isExpired()) {
			//过期可以删除
			return true;
		}
		return false;
	}
	
	/**
	 * 实体对象转协议对象
	 * @return 邮件序列化成消息对象
	 */
	default public PBMail.PBMailInfo toProto(long playerId) {
		PBMailInfoBuilder builder = PBMailInfoBuilder.newInstance();
		builder.setMailId(this.getId());
		builder.setState(this.getState(playerId));
		builder.setContent(this.getContent());
		builder.setTitle(this.getTitle());
		//格式化日期
		String createDate = DateFormatUtils.format(Calendar.getInstance().getTime(), DateUtils.PATTERN_NORMAL);
		builder.setDate(createDate);
		//奖励格式化
		//builder.addAllRewards(ResourceHelper.toPairProto(getRewardMap()));
		builder.addAllRewards(getRewardMap().toCollPairProto());
		return builder.build();
	}
	
	/**
	 * 删除邮件, 群邮件删除和个人玩家邮件删除处理方式不同
	 * @param playerId  
	 * @return void  
	 * @date 2021年11月28日上午10:00:59
	 */
	public void deleteMail(long playerId);
}
