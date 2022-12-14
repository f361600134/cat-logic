package com.cat.server.game.module.resource.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.cat.server.game.data.proto.PBCommon.PBPairInfo;
import com.cat.server.game.data.proto.PBItem.PBRewardInfo;
import com.cat.server.game.module.common.proto.PBPairInfoBuilder;
import com.cat.server.game.module.item.proto.PBRewardInfoBuilder;
import com.cat.server.game.module.item.proto.RespRewardsBuilder;

/**
 * 资源组
 */
public class ResourceGroup {
	
	/**
	 * 提供一个静态的,全局的,不可变的空资源组
	 */
	public static final ResourceGroup emptyGroup = new ImmutableResourceGroup();

    /**
     * key:资源id，可以是属性，道具
     * value:值
     */
    protected Map<Integer, Integer> dictionary;
    
    public ResourceGroup() {
    	this.dictionary = new HashMap<>();
    }
    
    public ResourceGroup(Map<Integer, Integer> dictionary) {
    	this.dictionary = dictionary;
    }

//    public ResourceGroup(Map<Integer, Integer> dictionary) {
//        if (dictionary == null) {
//            throw new NullPointerException();
//        }
//        //JDK
//        this.dictionary = Collections.unmodifiableMap(dictionary);
//        //Guava
//        //this.dictionary = ImmutableMap.copyOf(dictionary);
//    }

    /**
     * 	获取奖励字典
     * @return
     */
    public Map<Integer, Integer> getDictionary() {
        return dictionary;
    }
    
    /**
     * 	根据资源id获取值
     * @param configId 资源id
     */
    public Integer getCount(int configId) {
        return dictionary.getOrDefault(configId, 0);
    }

    /**
     * 	增加数量
     * @param configId 资源id
     * @param added 增加的数量
     */
    public void addCount(int configId, int added) {
        if(added <= 0) return;
        int count = getCount(configId);
        count+=added;
        dictionary.put(configId, count);
    }
    
    /**
     * 	根据给定的类型, 值增加属性
     * @param dictionary 需要合并的奖励
     */
    public <T extends Map<Integer, Integer>> void merge(T dictionary) {
        dictionary.forEach((key, value)->{
        	addCount(key, value);
        });
    }
    
    /**
     * 	其他字典合并到此字典
     * @param otherReward 其他奖励字典
     */
    public void merge(ResourceGroup otherReward) {
        if (otherReward == null || otherReward.empty()) {
            return;
        }
        this.merge(otherReward.getDictionary());
    }
    
    /**
     * 	根据给定的类型, 值减少属性
     */
    public <T extends Map<Integer, Integer>> void subCount(T dictionary) {
        if (dictionary == null) {
            return;
        }
        dictionary.forEach((key, value)->{
        	subAttr(key, (Integer)value);
        });
    }

    /**
     * 	根据基础类型减少值
     * @param configId 资源id
     * @param added 数量
     */
    public void subAttr(int configId, int added) {
        if (added <= 0) return;
        int count = getCount(configId);
        count = count - added;
        if (count <= 0){
            count = 0;
        }
        dictionary.put(configId, count);
    }

    /**
     * 	判断字典是否为空
     * @return
     */
    public boolean empty() {
        return dictionary.isEmpty();
    }
    
    /**
     * 	清掉字典内容
     * @return
     */
    public void clear() {
        dictionary.clear();
    }
    
    /**
     * 奖励序封装成奖励消息对象
     * @return 奖励消息对象
     */
    public RespRewardsBuilder toProto(){
    	RespRewardsBuilder resp = RespRewardsBuilder.newInstance();
        this.dictionary.forEach((key, val)->{
            PBRewardInfoBuilder builder = new PBRewardInfoBuilder();
            builder.setConfigId(key);
            builder.setCount(val);
            resp.addRewards(builder.build());
        });
        return resp;
    }
    
    /**
     * 奖励序封装成奖励消息对象
     * @return 奖励消息对象
     */
    public Collection<PBPairInfo> toCollPairProto(){
    	Collection<PBPairInfo> ret = new ArrayList<>();
        this.dictionary.forEach((key, val)->{
            PBPairInfoBuilder builder = new PBPairInfoBuilder();
            builder.setKey(key);
            builder.setValue(val);
            ret.add(builder.build());
        });
        return ret;
    }
    
    /**
     * 奖励序封装成奖励消息对象
     * @return 奖励消息对象
     */
    public Collection<PBRewardInfo> toCollProto(){
    	Collection<PBRewardInfo> ret = new ArrayList<>();
        this.dictionary.forEach((key, val)->{
            PBRewardInfoBuilder builder = new PBRewardInfoBuilder();
            builder.setConfigId(key);
            builder.setCount(val);
            ret.add(builder.build());
        });
        return ret;
    }
    
	@Override
	public String toString() {
		return "ResourceGroup [dictionary=" + dictionary + "]";
	}
    
    

}
