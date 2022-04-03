package com.cat.server.game.module.playermail.domain;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cat.orm.core.annotation.PO;
import com.cat.orm.util.StateUtils;
import com.cat.server.admin.module.mail.BackstageMail;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.mail.IMail;
import com.cat.server.game.module.mail.assist.MailConstant;
import com.cat.server.game.module.mail.assist.MailState;
import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.utils.TimeUtil;

/**
* @author Jeremy
*/
@PO(name = "player_mail")
public class PlayerMail extends PlayerMailPo implements IPersistence, IMail{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942618617969545324L;
	
	private ResourceGroup rewardMap;

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
	 * 根据后台邮件创建游戏邮件对象
	 * @param backstageMail 后台邮件
	 * @return 游戏邮件
	 */
	public static PlayerMail create(BackstageMail backstageMail) {
		final long playerId = backstageMail.getPlayerId();
		final String title = backstageMail.getTitle();
		final String content = backstageMail.getContent();
		final int expiredDays = backstageMail.getExpireDays();
		final ResourceGroup rewards = new ResourceGroup(backstageMail.getReward());
		return create(playerId, title, content, expiredDays, rewards);
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
	public static PlayerMail create(long playerId, String title, String content, int expiredDays, ResourceGroup rewards) {
		SnowflakeGenerator generator = SpringContextHolder.getBean(SnowflakeGenerator.class);
		PlayerMail mail = new PlayerMail(playerId);
		//邮件id唯一,使用雪花生成器生成
		mail.setId(generator.nextId());
		mail.setTitle(title);
		mail.setContent(content);
		long now = TimeUtil.now();
		mail.setCreateTime(now);
		//计算过期时间
		expiredDays = expiredDays <= 0 ? MailConstant.expiredDays : expiredDays;
		long expireTime = now + TimeUtil.DAY_MILLISECONDS * expiredDays;
		mail.setExpireTime(expireTime);
		mail.save();
		return mail;
	}

	/**
	 * @return 邮件奖励列表
	 */
	public ResourceGroup getRewardMap() {
		return rewardMap;
	}

	@Override
	public int getState(long playerId) {
		return getState();
	}

	@Override
	public void addState(long playerId, MailState state) {
		this.state = (byte)StateUtils.addState(this.state, state.getState());
		this.update();
	}

	@Override
	public void deleteMail(long playerId) {
		this.delete();
	}
	
	@Override
	public void afterLoad() {
		if (!StringUtils.isBlank(rewards)){
			this.rewardMap = JSONObject.parseObject(rewards, new TypeReference<ResourceGroup>(){});
		}else {
			this.rewardMap = new ResourceGroup();
		}
	}

}
