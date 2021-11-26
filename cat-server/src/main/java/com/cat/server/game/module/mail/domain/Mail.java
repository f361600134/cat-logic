package com.cat.server.game.module.mail.domain;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBMail;
import com.cat.server.game.data.proto.PBPlayerMail;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.mail.assist.MailConstant;
import com.cat.server.game.module.mail.proto.PBMailInfoBuilder;
import com.cat.server.game.module.resource.helper.ResourceHelper;
import com.cat.server.utils.DateUtils;
import com.cat.server.utils.TimeUtil; 

/**
* @author Jeremy
*/
@PO(name = "mail")
public class Mail extends MailPo implements IPersistence{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942618617969545324L;
	
	private Map<Integer, Integer> rewardMap;

	public Mail() {
		
	}
	
	public Mail(long playerId) {
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
	 * @param playerId 玩家id
	 * @param title 标题
	 * @param content 内容
	 * @param expiredDays 过期天数
	 * @param rewards 奖励内容
	 * @return 邮件对象
	 */
	public static Mail create(long playerId, String title, String content, int expiredDays, Map<Integer, Integer> rewards) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		Mail mail = new Mail(playerId);
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
	 * @return 邮件奖励列表
	 */
	public Map<Integer, Integer> getRewardMap() {
		return rewardMap;
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
		builder.addAllRewards(ResourceHelper.toPairProto(rewardMap));
		return builder.build();
	}
	
	/**
	 * 是否已经领取
	 * @return true:已领奖
	 */
	public boolean isRewarded() {
		return this.getState() == MailConstant.REWARD;
	}
	
	/**
	 * 是否过期
	 * @return true:已过期
	 */
	public boolean isExpired() {
		//截止时间大于当前时间,表示过期
		return (TimeUnit.MILLISECONDS.toSeconds(TimeUtil.now())) >= this.getExpireTime();
	}

	/**
	 * 是否可以删除
	 * @return true:可删除
	 */
	public boolean canDel() {
		if (this.getState() == MailConstant.READ && this.getRewardMap().isEmpty()) {
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
	
	@Override
	public void afterLoad() {
		if (!StringUtils.isBlank(rewards)){
			this.rewardMap = JSONObject.parseObject(rewards, new TypeReference<Map<Integer, Integer>>(){});
		}else {
			this.rewardMap = new HashMap<>();
		}
	}
	
}
