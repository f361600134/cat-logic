package com.cat.server.json.parse;

import java.time.LocalDateTime;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 测试 POJO
 *
 * @author LiYangDi
 * @since 2019/12/23
 */
public class Pojo {

    @JSONField(name = "time", deserializeUsing = FastJsonLocalDateTimeDeserializer.class)
    //@JSONField(name = "time")
    private LocalDateTime localDateTime;

    @JSONField(name = "name")
    private String name;

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Pojo [localDateTime=" + localDateTime + ", name=" + name + "]";
	}
    
}