package com.cat.server.game.module.chat.domain;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.utils.TimeUtil;


/**
 * 玩家聊天约束,是一个聊天检测机制.分两个阶段:违规和禁止.
 * 如果玩家在一定时间内连续违规次数不超过x次时,可继续聊天,否则禁止聊天.
 * 此对象仅记录聊天相关时间, 以及违规相关时间, 违规和禁止没有具体状态, 每次触发时检测计算.
 * 
 * @author Jeremy
 */
public class ChatRule{
	
	private static final Logger log = LoggerFactory.getLogger(ChatRule.class);
	
	//默认60s违规次数,连续5次违规则延长时限
	private static final int DEFAULT_AGAINST_COUNT = 5;
	//默认60s违规次数
	private static final int DEFAULT_LIMIT_TIME = 60;
	//默认说话间隔时长 10s内
	private static final int DEFAULT_SPEAK_DELAY = 10;
	
	//private long lastTime; //最后一次说话时间
	private long nextSpeakTime; //下次说话时间, 超过这个值可说话
	private Queue<Long> againstQueue;//违规时间列表
	
	private int againstCount; //自定义1分钟内违规次数
	private int limitTime;//自定义限制时间
	private int speakDelay; //发言间隔时间,毫秒
	
	public ChatRule() {
		this.againstCount = DEFAULT_AGAINST_COUNT;
		this.limitTime = DEFAULT_LIMIT_TIME;
		this.speakDelay = DEFAULT_SPEAK_DELAY;
		//this.lastTime = System.currentTimeMillis();
		this.nextSpeakTime = TimeUtil.now();
		this.againstQueue = new ArrayBlockingQueue<Long>(this.againstCount);
	}
	
	public ChatRule(int customAgainstCount, int limitTime, int speakDelay) {
		this.againstCount = customAgainstCount;
		this.limitTime = limitTime;
		this.speakDelay = speakDelay;
		//this.lastTime = System.currentTimeMillis();
		this.nextSpeakTime = TimeUtil.now();;
		this.againstQueue = new ArrayBlockingQueue<Long>(this.againstCount);
	}
	
	public static ChatRule create() {
		return new ChatRule();
	}
	
	public static ChatRule create(int customAgainstCount, int limitTime, int speakDelay) {
		return new ChatRule(customAgainstCount, limitTime, speakDelay);
	}

	public long getNextSpeakTime() {
		return nextSpeakTime;
	}

	/**
	 * 聊天成功, 设置最后聊聊天时间以及下次说话时间
	 * @param curTime 当前时间戳
	 */
	public void onChatSuccess(long curTime) {
		this.nextSpeakTime = curTime + (speakDelay * 1000);//下次发言间隔
		//log.debug("onChatSuccess this.lastTime:{}, , this.nextSpeakTime:{}", TimeUtils.format(this.lsatTime), TimeUtils.format(this.nextSpeakTime));
		clearAgainst(curTime);
		//log.debug("onChatSuccess , this.nextSpeakTime:{}, againstQueue:{}", TimeUtils.format(this.nextSpeakTime), againstQueue);
	}
	
	/**
	 * 是否违规
	 * @return  
	 * @return boolean  
	 * @date 2021年5月6日上午12:33:02
	 */
	public boolean isAgainst(long curTime) {
		boolean bool = checkSpeak(curTime);
		if(bool) {
			onAgainst(curTime);
		}
		return bool;
	}

	/**
	 * 当玩家违规
	 * 1.违规次数累加
	 * 2.清洗违规时间
	 * 3.检测是否被禁止,如果被禁止聊天,延长下次说话时间
	 * @param againstTime 违规时间
	 */
	private void onAgainst(long againstTime) {
		againstQueue.offer(againstTime);
		//log.debug("onTrigger this.lastTime:{}, this.nextSpeakTime:{}", TimeUtils.format(this.lsatTime), TimeUtils.format(this.nextSpeakTime));
		clearAgainst(againstTime);
		boolean bool = isProhibited();
		if (bool) {
			this.nextSpeakTime += (limitTime * 1000);
		}
		//log.debug("onTrigger , this.nextSpeakTime:{}, againstQueue:{}", TimeUtils.format(this.nextSpeakTime), againstQueue);
	}
	
	/**
	 * 清洗违规时间列表, 清除掉1分钟之前的违规时间.
	 * @return void  
	 * @date 2021年5月5日下午11:44:28
	 */
	private void clearAgainst(long againstTime) {
		long expireTime = againstTime - this.limitTime * 1000;
		Iterator<Long> iter =  againstQueue.iterator();
		while (iter.hasNext()) {
			if (iter.next() < expireTime) {
				iter.remove();
			}else {
				break;
			}
		}
	}
	
	/**
	 * 是否禁止聊天
	 * 未到达可聊天时间, 并且禁止队列满, 表示禁止聊天
	 * @return true means it is against the rules.
	 * @date 2021年5月5日下午11:37:03
	 */
	public boolean isProhibited(long curTime) {
		return checkSpeak(curTime) && isProhibited();
	}
	
	/**
	 * 是否禁止,一定时间内违规次数超过限制, 表示禁止行动
	 * @return true means it is against the rules.
	 * @date 2021年5月5日下午11:37:03
	 */
	private boolean isProhibited() {
		return againstQueue.size() >= this.againstCount ? true : false;
	}
	
	/**
	 * 检测是否可以说话,当前时间大于下次说话时间表示可以说话
	 * @return  true 表示可以说话, false 表示不可以说话
	 * @date 2021年5月5日下午11:52:44
	 */
	public boolean checkSpeak(long curTime) {
		return curTime > getNextSpeakTime();
	}

}