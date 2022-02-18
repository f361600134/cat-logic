package com.cat.server.admin.module.mail;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.cat.server.admin.helper.mapstruct.IBaseMapper;
import com.cat.server.game.module.mail.IMail;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMailMapper extends IBaseMapper<BackstageMail, IMail>{
	
	/**
	 * 邮件对象转后台邮件
	 * @param mail
	 * @return
	 */
	@Mappings({
		@Mapping(target = "mailId", expression = "java(mail.getId())"),
	  @Mapping(target = "content", expression = "java(mail.getContent())"),
	  @Mapping(target = "expireDays", expression = "java(com.cat.server.utils.TimeUtil.getDifferDay(mail.getCreateTime(), mail.getExpireTime()))"),
	  @Mapping(target = "reward", expression = "java(mail.getRewardMap())")
	})
	public BackstageMail toDto(IMail mail);
	
	/**
	 * 邮件对象列表转后台邮件列表
	 * @param mail
	 * @return
	 */
	public Collection<BackstageMail> toDto(Collection<? extends IMail> mail);
	
}
