package com.cat.server.game.module.groupmail.domain;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.orm.util.StateUtils;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBMail;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.assist.MailState;
import com.cat.server.game.module.mail.proto.PBMailInfoBuilder;
import com.cat.server.game.module.playermail.assist.PlayerMailConstant;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.cat.server.utils.DateUtils;
import com.cat.server.utils.TimeUtil; 

/**
* @author Jeremy
*/
@PO(name = "group_mail")
public class GroupMail extends GroupMailPo implements IPersistence, IMail{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3215406404369917418L;

	@Column(value = PROP_REWARDS)
	private Map<Integer, Integer> rewardMap;
	
	/**
	 * 邮件状态信息<br>
	 * key: 玩家id<br>
	 * value: 邮件状态<br>
	 * 1: 未读取<br>
	 * 2: 已读取<br>
	 * 4: 已领奖
	 */
	@Column(value = PROP_EXTENDSTR)
	private Map<Long, Integer> stateMap;

	public GroupMail() {

	}
	
	public Map<Integer, Integer> getRewardMap() {
		return rewardMap;
	}

	Map<Long, Integer> getStateMap() {
		return stateMap;
	}

	/**
	 * 创建邮件对象
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励内容
	 * @return 邮件对象
	 */
	public static GroupMail create(String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		GroupMail mail = new GroupMail();
		//邮件id唯一,使用雪花生成器生成
		mail.setId(generator.nextId());
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
	
	/**
	 * 获取状态<br>
	 * 返回客户端显示状态
	 * @param state
	 */
	public int getState(long playerId) {
		int state = stateMap.getOrDefault(playerId, MailState.NONE.getState());
		if (StateUtils.check(state, MailState.DELETE.getState())) {
			return MailState.DELETE.getState();
		}else if(StateUtils.check(state, MailState.REWARD.getState())) {
			return MailState.REWARD.getState();
		}else if(StateUtils.check(state, MailState.READ.getState())) {
			return MailState.READ.getState();
		}
		return state;
	}
	
//	/**
//	 * 邮件修改, 仅允许修改以下参数内容
//	 * @param title 标题
//	 * @param content 内容
//	 * @param expiredDays 过期天数
//	 * @param rewards 奖励内容
//	 */
//	public void update(String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
//		this.setTitle(title);
//		this.setContent(content);
//		//计算过期时间
//		long expireTime = this.getCreateTime() + TimeUtil.DAY_MILLISECONDS * expiredDays;
//		this.setExpireTime(expireTime);
//		this.rewardMap.clear();
//		this.rewardMap.putAll(rewards);
//		this.update();
//	}
	
	/**
	 * 是否已经领取
	 * @return true:已领奖
	 */
	public boolean isRewarded(long playerId) {
		int state = stateMap.getOrDefault(playerId, MailState.NONE.getState());
		return state == PlayerMailConstant.REWARD;
	}
	
	/**
	 * 是否过期
	 * @return true:已过期
	 */
	public boolean isExpired() {
		//截止时间大于当前时间,表示过期
		return (TimeUnit.MILLISECONDS.toSeconds(TimeUtil.now())) >= this.getExpireTime();
	}

//	/**
//	 * 是否可以删除
//	 * @return true:可删除
//	 */
//	public boolean canDel(long playerId) {
//		int state = stateMap.getOrDefault(playerId, MailState.NONE.getState());
//		if (state == PlayerMailConstant.READ && this.getRewardMap().isEmpty()) {
//			//状态为已读,并且没有奖励配置 可以删除
//			return true;
//		}else if (this.isRewarded(playerId)) {
//			//领过奖,可以删除
//			return true;
//		}else if (this.isExpired()) {
//			//过期可以删除
//			return true;
//		}
//		return false;
//	}
//	
	/**
	 * 标记状态, 用于玩家行为操作
	 * @param mailId
	 * @param playerId
	 * @param state
	 */
	public void mark(long playerId, MailState mailState) {
		int state = stateMap.getOrDefault(playerId, MailState.NONE.getState());
		state = (int)StateUtils.addState(state, mailState.getState());
		stateMap.put(playerId, state);
		this.update();
	}
	
	/**
	 * 实体对象转协议对象
	 * @return 邮件序列化成消息对象
	 */
	public PBMail.PBMailInfo toProto(long playerId) {
		PBMailInfoBuilder builder = PBMailInfoBuilder.newInstance();
		builder.setMailId(this.getId());
		builder.setState(this.getState(playerId));
		builder.setContent(this.getContent());
		builder.setTitle(this.getTitle());
		//格式化日期
		String createDate = DateFormatUtils.format(Calendar.getInstance().getTime(), DateUtils.PATTERN_NORMAL);
		builder.setDate(createDate);
		//奖励格式化
		builder.addAllRewards(ResourceHelper.toPairProto(rewardMap));
		return builder.build();
	}
	
//	
//	/**
//	 * 标记为已读
//	 * @param mailId 邮件id
//	 * @param playerId 玩家id
//	 */
//	public void markeAsRead(long mailId, long playerId) {
//		int state = stateMap.getOrDefault(playerId, MailState.NONE.getState());
//		stateMap.computeIfAbsent(playerId, k-> (int)StateUtils.addState(state, MailState.READ.getState()));
//	}
//	
//	/**
//	 * 标记为删除
//	 * @param mailId 邮件id
//	 * @param playerId 玩家id
//	 */
//	public void markeForRemoval(long mailId, long playerId) {
//		int state = stateMap.getOrDefault(playerId, MailState.NONE.getState());
//		state = (int)StateUtils.addState(state, MailState.DELETE.getState());
//		stateMap.put(playerId, state);
//	}
//	
//	/**
//	 * 标记为已领奖
//	 * @param mailId 邮件id
//	 * @param playerId 玩家id
//	 */
//	public void markeForReward(long mailId, long playerId) {
//		int state = stateMap.getOrDefault(playerId, MailState.NONE.getState());
//		state = (int)StateUtils.addState(state, MailState.REWARD.getState());
//		stateMap.put(playerId, state);
//	}
//	
}
