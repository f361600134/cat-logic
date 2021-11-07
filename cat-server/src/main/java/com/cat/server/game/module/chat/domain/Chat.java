package com.cat.server.game.module.chat.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.type.IChatType;
import com.cat.server.utils.ConcurrentFixSizeArrayList;
import com.cat.server.utils.Pair;

/**
 * @author Jeremy
 */
@PO(name = "chat")
public class Chat extends ChatPo implements IPersistence {

	/**
	 * 唯一id, 由两个long类型数值生成
	 */
	private BigInteger uniqueId;

	/**
	 * 聊天记录信息
	 */
	@Column(value = PROP_DATA, clazzType = ConcurrentFixSizeArrayList.class)
	private List<ChatDetail> chatDetails;
	
	
	//	非序列化数据
	private transient int viewLimit = 10;

	public Chat() {

	}

	public BigInteger getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(BigInteger uniqueId) {
		this.uniqueId = uniqueId;
	}

	public List<ChatDetail> getChatDetails() {
		return chatDetails;
	}

	public void setChatDetails(List<ChatDetail> chatDetails) {
		this.chatDetails = chatDetails;
	}

	@Override
	public Object key() {
		return null;
	}

	public String keyColumn() {
		return null;
	}

	@Override
	public void beforeSave() {
		// 存储uniqueId
		IChatType chatType = ChannelType.getChannelType(channel).getChatType();
		Pair<Long, Long> pair = chatType.getOriginalIds(uniqueId);
		this.leftKey = pair.getLeft();
		this.rightKey = pair.getRight();
		// 存储聊天记录
		this.data = JSON.toJSONString(chatDetails);
	}

	@Override
	public void afterLoad() {
		// 根据channel构造uniqueId
		IChatType chatType = ChannelType.getChannelType(channel).getChatType();
		this.uniqueId = chatType.getUniqueId(leftKey, rightKey);
		// 聊天信息构造
		List<ChatDetail> ret = JSON.parseObject(this.data, new TypeReference<ArrayList<ChatDetail>>() {
		});
		//ConfigChat config = ConfigManager.getInstance().getConfig(ConfigChat.class, channel);
		//this.chatDetails = new ConcurrentFixSizeArrayList<ChatDetail>(config.getCacheNum());
		this.chatDetails = new ConcurrentFixSizeArrayList<ChatDetail>(10);
		this.chatDetails.addAll(ret);
	}

	/**
	 * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
	 * 先进后出
	 * 取出指定数量的聊天记录,
	 * 策划指定显示数量viewLimit, 比如5
	 * 客户端指定获取数量num, 比如5
	 * to = list.size - num;
	 * from = to - viewLimit;
	 * 
	 * num为0,viewLimit为5, to为10,from为5, 表示6~10, 取出[6, 7, 8, 9, 10]
	 * num为5,viewLimit为5, to为5,from为0, 表示1~5, 取出[1, 2, 3, 4, 5]
	 * 所以只需要计算出end就能知道from
	 * @param num
	 * @return
	 */
	public List<ChatDetail> getChatDetail(int num){
		int toIndex = chatDetails.size() - num;
		int fromIndex = toIndex - viewLimit;
		fromIndex = fromIndex < 0 ? 0 : fromIndex;
		List<ChatDetail> ret = new ArrayList<>(chatDetails.subList(fromIndex, toIndex));
		return ret;
	}
	
	/**
	 * 获取最新的一条聊天记录
	 * @return
	 */
	public ChatDetail getLastChatDetail() {
		return chatDetails.get(chatDetails.size());
	}
	
	/**
	 * 添加新聊天内容
	 * @param chatDetail
	 */
	public void addChatDetail(ChatDetail chatDetail) {
		this.chatDetails.add(chatDetail);
	}
	
}
