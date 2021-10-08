package com.cat.server.json.parse;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * FastJson 时间格式自定义反序列化类
 *
 * @author LiYangDi
 * @since 2019/12/23
 */
public class FastJsonLocalDateTimeDeserializer implements ObjectDeserializer {

    private static List<DateTimeFormatter> dateTimeFormatters = new LinkedList<>();

    static {
        // Add your own formatter to there
        dateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        dateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        dateTimeFormatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public LocalDateTime deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
    	System.out.println(parser);
    	System.out.println(type);
    	System.out.println(fieldName);
    	System.out.println(parser.lexer.stringVal());
        final String input = parser.lexer.stringVal();
        LocalDateTime localDateTime = null;
        for (DateTimeFormatter dateTimeFormatter : dateTimeFormatters) {
            try {
                localDateTime = LocalDateTime.parse(input, dateTimeFormatter);
                // Format success, step over other DateTimeFormatter
                break;
            } catch (DateTimeParseException ex) {
                // do nothing to use next formatter
            }
        }
        Assert.notNull(localDateTime, "FastJson LocalDateTime use" +
                " FastJsonTimestampDeserializer format error: " + input);
        return localDateTime;
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}