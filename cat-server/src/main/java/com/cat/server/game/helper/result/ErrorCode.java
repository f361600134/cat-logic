package com.cat.server.game.helper.result;

import com.cat.server.core.result.CodeEnum;
import com.cat.server.game.module.common.proto.RespTipsBuilder;

/**
 * 错误码(返回码)<br>
 * 需要生成excel提供给客户端读取
 * @author Jeremy
 */
public enum ErrorCode implements ModuleDefines, CodeEnum{
    /**
     *	成功消息
     */
    SUCCESS(0, 0, "成功"),
    // ---------------公共-------------------
    UNKNOWN_ERROR(COMMON, 1, "未知异常"),
    CONFIG_NOT_EXISTS(COMMON, 2, "配置不存在"),
    CONFIG_PARAM_ERROR(COMMON, 3, "配置参数错误"),
    INVALID_PARAM(COMMON, 4, "无效参数"),
    RESOURCE_USE_NUM_ERROR(COMMON, 5, "资源使用数量错误，有些资源必须消耗或者增加必须是特定的数量"),
    RESOURCE_CANNOT_CONSUME(COMMON, 6, "该资源不能被消耗"),
    NO_GM_PERMISSION(COMMON, 7, "没有GM权限"),
    AMOUNT_NOT_ENOUGH(COMMON, 8, "数量不足"),// 对于非资源数据，数量不足的通用描述
    FORMULA_NOT_EXIST(COMMON, 9, "公式不存在"),
    CONTENT_EXIST_BAD_WORD(COMMON, 10, "输入的内容包含敏感字"),
    CONTENT_UNSUPPORT_OPERATION(COMMON, 11, "不支持的操作"),
    CONTENT_INVALID_NAME(COMMON, 11, "无效名称"),
    DOMAIN_IS_NULL(COMMON, 12, "模块数据异常"),
    // ---------------账号-------------------
    INVALID_ACCOUNT_NAME(ACCOUNT, 1, "无效的账号名"),
    ACCOUNT_NOT_LOGIN(ACCOUNT, 2, "账号未登陆"),
    ACCOUNT_ILLEGAL(ACCOUNT, 3, "账号验证失败"),
    ACCOUNT_ERROR(ACCOUNT, 4, "验证出现异常"),
    ACCOUNT_REPEAT(ACCOUNT, 5, "登录数据重复异常"),
    ACCOUNT_NOT_FOUNT(ACCOUNT, 6, "无该玩家数据"),
    // ---------------登录-------------------
    
 // ---------------玩家-------------------
    INVALID_PLAYER_NAME(PLAYER, 1, "无效玩家名"),
    PLAYER_NAME_TOO_LENGTH(PLAYER, 2, "玩家名过长"),
    EXISTS_SAME_PLAYER_NAME(PLAYER, 3, "存在相同的玩家名"),
    PLAYER_NOT_EXISTS(PLAYER, 5, "玩家不存在"),
    FUNCTION_NOT_OPEN(PLAYER, 6, "功能未开启"),
    INVALID_COUNTRY_NAME(PLAYER, 7, "无效国家名"),
    COUNTRY_NAME_TOO_LENGTH(PLAYER, 8, "国家名过长"),
    EXISTS_SAME_COUNTRY_NAME(PLAYER, 9, "存在相同的国家名"),
    NAME_NO_CHANGE(PLAYER, 10, "名字没有变化"),
    PLAYER_LEVEL_LIMIT(PLAYER, 11, "玩家等级已满"),
    PLAYER_LEVEL_TOO_LOW(PLAYER, 12, "玩家等级过低"),
    PLAYER_EXP_NOT_ENOUGH(PLAYER, 13, "玩家经验不足"),
    PLAYER_RENAME_ERROR(PLAYER, 14, "一天只能更改一次名字"),

 // -----------------聊天---------------------

    CHAT_CD(CHAT, 1, "聊天太快"),
    CHAT_SILENCE(CHAT, 2, "已被禁言"),
    CHAT_CONDITION_LEVEL_NOT_REACH(CHAT, 3, "等级未达到当前频道聊天条件"),
    CHAT_MESSAGE_IS_EMPTY(CHAT, 4, "聊天信息为空"),
    CHAT_MESSAGE_TOO_LONG(CHAT, 5, "聊天信息太长"),
    CHAT_CHANNEL_NOT_EXISTS(CHAT, 6, "聊天频道不存在"),
    CHAT_REPORT_LIMIT(CHAT, 7, "举报过于频繁，请稍后再试"),
    CHAT_RECORD_NOT_EXIST(CHAT, 8, "聊天记录不存在"),
    CHAT_ALLIANCE_NOT_EXIST(CHAT, 9, "加入联盟才能聊天"),
    CHAT_FRIEND_NOT_EXIST(CHAT, 10, "聊天好友不存在或已不是好友，无法聊天"),
    CHAT_CONDITION_VIP_NOT_REACH(CHAT, 11, "VIP等级未达到当前频道聊天条件"),
    CHAT_SYSTEM_NOT_ALLOWED(CHAT, 12, "该频道禁止聊天"),
    CHAT_TIME_LIMIT(CHAT, 13, "剩余x秒后可聊天"),
    CHAT_TARGET_IS_SELF(CHAT, 14, "聊天目标不能是自己"),
    CHAT_CHANNEL_NOT_ALLOWED_DELETE(CHAT, 15, "聊天不允许被删除"),
    
    // -----------------任务---------------------
    MISSION_NOT_COMPLATE(MISSION, 1, "任务未完成"),
    MISSION_REWARDES(MISSION, 2, "任务奖励已领取"),
    MISSION_NOT_EXIST(MISSION, 3, "任务奖励已领取"),
    
    // -----------------邮件---------------------
    MAIL_BOX_NOT_FOUND(MAIL, 1, "邮箱不存在"),
    MAIL_NOT_FOUND(MAIL, 2, "邮件不存在"),
    MAIL_ALREADY_REWARD(MAIL, 3, "邮件已领取"),
    MAIL_ALREADY_EXPIRED(MAIL, 4, "邮件已过期"),
    MAIL_ALREADY_NO_REWARD(MAIL, 5, "没有可以领取的邮件"),
    MAIL_NOT_FOUND_PLAYER(MAIL, 6, "邮件发送失败没有这个玩家"),
    
    // -----------------家族---------------------
    FAMILY_NAME_EXIST(FAMILY, 1, "家族名被占用"),
    FAMILY_ALREADY_IN_FAMILY(FAMILY, 2, "不能重复进入家族"),
    FAMILY_NO_FAMILY(FAMILY, 3, "家族不存在"),
    FAMILY_JOIN_TIME_LIMIT(FAMILY, 4, "退出家族后1天后才可以再次加入"),
    FAMILY_TAG_EXIST(FAMILY, 5, "家族号被占用"),
    FAMILY_NO_PRIVILEGE(FAMILY, 6, "没有该权限"),
    FAMILY_FIRE_SELF(FAMILY, 7, "请先退位再退家族"),
    FAMILY_PLAYER_NOT_EXIST(FAMILY, 8, "此玩家不在家族"),

    // -----------------组队---------------------
    TEAM_NAME_EXIST(TEAM, 1, "队伍名被占用"),
    TEAM_MEMBER_NOT_EXIST(TEAM, 2, "此队员不存在"),
    TEAM_FIRE_SELF(TEAM, 3, "被操作对象不能是自己"),
    TEAM_NOT_EXIST(TEAM, 4, "队伍不存在"),
    TEAM_ALREADY_IN_TEAM(TEAM, 5, "已处在队伍中不能创建队伍"),
    TEAM_NOT_LEADER(TEAM, 6, "不是队长无权限操作"),
    TEAM_APPLY_EXPIRE(TEAM, 7, "该申请已过期"),
    
    // -----------------排行榜---------------------
    RANK_NOT_EXIST(TEAM, 1, "排行榜不存在"),
    
    // -----------------活动---------------------
    ACTIVITY_NOT_EXIST(ACTIVITY, 1, "活动不存在"),
    ACTIVITY_NOT_IN_ACTIVITY_TIME(ACTIVITY, 2, "未处于活动时间"),
    ;
	
	
	
    private final int code;
    private final String desc;
    
    ErrorCode(int moduleId, int seq, String desc) {
        this.code = moduleId * 100 + seq;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
    
    public int getStatus(){
    	return getCode();
    }
    
    /*通常不需要此描述, 仅用于服务器*/
    public String getDesc() {
		return desc;
	}
    
    public boolean isSuccess() {
        return this.code == SUCCESS.getCode();
    }
    
    /**
     * 构建默认不带参数协议
     * @return
     */
    public RespTipsBuilder toProto() {
    	RespTipsBuilder builder = RespTipsBuilder.newInstance();
    	builder.setTipsId(getCode());
    	return builder;
    }
    
    /**
     * 构建带参数协议
     * @return
     */
    public RespTipsBuilder toProto(int param) {
    	RespTipsBuilder builder = RespTipsBuilder.newInstance();
    	builder.setTipsId(getCode());
    	builder.setParams(param);
    	return builder;
    }
}
