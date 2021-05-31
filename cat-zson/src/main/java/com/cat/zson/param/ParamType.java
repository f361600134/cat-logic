package com.cat.zson.param;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import com.cat.zson.exception.ParamParseException;
import com.cat.zson.reader.ExcelReader;

public interface ParamType {

    String[] getNames();

    default Object parseCell(Cell cell) throws ParamParseException {
        String param = ExcelReader.getCellValue(cell);
        if (param == null || StringUtils.isEmpty(param)) {
            return defaultValue();
        }
        return parseValue0(param);
    }
    
    
    default Object parseValue(String param) throws ParamParseException {
        if (param == null || StringUtils.isEmpty(param)) {
            return defaultValue();
        }
        return parseValue0(param);
    }

    Object defaultValue();

    Object parseValue0(@Nonnull String param) throws ParamParseException;

    /**
     * java配置类中 <br>
     * 参数类型
     * 
     * @return
     */
    String getFieldClazz();

    /**
     * java配置类中 需要引用的类型
     * 
     * @return
     */
    String getImportClazz();

}
