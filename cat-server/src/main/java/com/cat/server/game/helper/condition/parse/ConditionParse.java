package com.cat.server.game.helper.condition.parse;

import java.lang.reflect.Type;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.cat.server.game.helper.condition.AndCondition;
import com.cat.server.game.helper.condition.EmptyCondition;
import com.cat.server.game.helper.condition.ICondition;
import com.cat.server.game.helper.condition.OrCondition;
import com.cat.server.game.helper.condition.define.ConditionEnum;

/**
 * 
 * 时间点解析器
 * @author Jeremy
 */
public class ConditionParse implements ObjectDeserializer {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    /**
     * &分割
     */
    private final static String SPLIT_AND = "&";
    /**
     * |分割
     */
    private final static String SPLIT_OR = "|";
    /**
     * :分割
     */
    private final static String SPLIT_COLON = ":";

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		final String value = parser.lexer.stringVal();
		if (StringUtils.isBlank(value)) {
            return (T)new EmptyCondition();
        }
		if (StringUtils.contains(value, SPLIT_AND)) {
			AndCondition condition = new AndCondition();
			String[] arrs = value.split(SPLIT_AND);
			for (String arr : arrs) {
				condition.addCondition(this.buildCondition(arr));
			}
			return (T)condition;
		}else if(StringUtils.contains(value, SPLIT_OR)){
			OrCondition condition = new OrCondition();
			String[] arrs = value.split(SPLIT_OR);
			for (String arr : arrs) {
				condition.addCondition(this.buildCondition(arr));
			}
			return (T)condition;
		}else {
			//不是null,空字符,也没有与或逻辑,所以视为单条件
			return (T)buildCondition(value);
		}
	}
	
	public ICondition buildCondition(String value) {
		String []conditionArr = value.split(SPLIT_COLON);
		if (conditionArr.length != 2) {
			throw new RuntimeException("配置错误, 请检查. value:"+value);
		}
		final int conditionId = Integer.valueOf(conditionArr[0]);
		final int conditionValue = Integer.valueOf(conditionArr[1]);
		ConditionEnum cEnum = ConditionEnum.valueOf(conditionId);
		ICondition condition = cEnum.newCondition(conditionValue);
		return condition;
	}
	
	@Override
	public int getFastMatchToken() {
		return JSONToken.LITERAL_INT;
	}

}
