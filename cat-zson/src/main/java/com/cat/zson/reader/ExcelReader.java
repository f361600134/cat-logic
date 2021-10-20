package com.cat.zson.reader;

import java.io.File;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.zson.defines.ConfigConstant;
import com.cat.zson.exception.ParamParseException;
import com.cat.zson.param.OutputTypes;
import com.cat.zson.param.ParamType;
import com.cat.zson.param.ParamTypes;
import com.cat.zson.structs.ConfigData;
import com.cat.zson.structs.Param;
import com.cat.zson.structs.TableData;
import com.cat.zson.util.FileUtil;

public class ExcelReader {

    private final static Logger logger = LoggerFactory.getLogger(ExcelReader.class);
    private final static String[] EXCEL_FILE_EXTENSIONS = { ".xlsx", ".xls" };
    /**
     * 0宽度字符
     */
    private final static String[] ZERO_WIDTH_SPACE_CHARS = {
            // "\u200a",
            "\u200b", "\u200c", "\u200d",
//            "\u200e"
    };

    private final static NumberFormat numberFormat = initNumberFormat();

    private static NumberFormat initNumberFormat() {
        NumberFormat instance = NumberFormat.getInstance();
        instance.setGroupingUsed(false);
        return instance;
    }

    /**
     * 解析目标文件夹下所有excel文件
     * 
     * @param dirPath
     * @return
     * @throws Exception
     */
    public static List<TableData> parseAllExcels(String dirPath) throws Exception {

        // excel重名检查
        Set<String> tableNameSet = new HashSet<>();

        List<File> excelFiles = FileUtil.getFiles(dirPath, (file) -> {
            if (!file.isFile()) {
                return false;
            }
            String fileName = file.getName();
            for (String extension : EXCEL_FILE_EXTENSIONS) {
                if (fileName.endsWith(extension)) {
                    return true;
                }
            }
            return false;
        });
        // 延迟解析比率
        ZipSecureFile.setMinInflateRatio(-1.0d);
        List<TableData> tableList = new ArrayList<>();
        for (File excelFile : excelFiles) {
            try {
                TableData tableData = ExcelReader.parseExcel(excelFile);

                // excel重名检查
                if (tableNameSet.contains(tableData.getName())) {
                    logger.error("exist same tableName:" + tableData.getName());
                    throw new IllegalArgumentException();
                }
                tableNameSet.add(tableData.getName());

                tableList.add(tableData);
                logger.info("reading [{}] size[{}]", excelFile.getName(), tableData.getConfigList().size());
            } catch (Exception e) {
                logger.error("read excel[" + excelFile.getName() + "] error.", e);
                throw e;
            }
        }
        return tableList;
    }

    public static TableData parseExcel(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file); //
                Workbook workbook = WorkbookFactory.create(fis)) {
            String fileName = file.getName();
            // 目前 只读第一页 FIXME
            Sheet sheet = workbook.getSheetAt(0);
            String sheetName = sheet.getSheetName();

            TableData tableData = new TableData();
            tableData.setName(sheetName);
            tableData.setFileName(fileName);
            // 读取表头
            readHeader(tableData, sheet);
            // 读取表内容
            readContent(tableData, sheet);
            // 判断该配置属于哪个端 FIXME TODO
            int tableType = readTableType(tableData);
            tableData.setType(tableType);
            return tableData;
        } finally {
        }
    }

    /**
     * 判断该配置表是否哪个端的<br>
     * 
     * 
     * @param tableData
     * @return
     */
    private static int readTableType(TableData tableData) {
        // 用id参数的输出类型判断表格类型
        List<Param> paramList = tableData.getParamList();
        Param idParam = paramList.get(0);
        OutputTypes output = idParam.getOutput();
        if (output == OutputTypes.CLIENT_DEC) {
            return ConfigConstant.TABLE_TYPE_CLIENT_DEC;
        }
        if (output == OutputTypes.CLIENT) {
            return ConfigConstant.TABLE_TYPE_CLIENT;
        }
        if (output == OutputTypes.SERVER) {
            return ConfigConstant.TABLE_TYPE_SERVER;
        }
        // 用所有参数判断该表格属于前端还是后端
        int serverSize = 0;
        int clientSize = 0;
        for (Param param : paramList) {
            OutputTypes tmpOutput = param.getOutput();
            if (tmpOutput.isClient()) {
                clientSize++;
            }
            if (tmpOutput.isServer()) {
                serverSize++;
            }
        }
        if (serverSize <= 1) {
            return ConfigConstant.TABLE_TYPE_CLIENT;
        }
        if (clientSize <= 1) {
            return ConfigConstant.TABLE_TYPE_SERVER;
        }
        return ConfigConstant.TABLE_TYPE_SERVER_AND_CLIENT;
    }

    /**
     * 读取内容 加载配置
     * 
     * @param tableData
     * @param sheet
     * @throws Exception
     */
    private static void readContent(TableData tableData, Sheet sheet) throws Exception {
        String name = tableData.getName();
        String fileName = tableData.getFileName();
        List<Param> paramList = tableData.getParamList();
        int lastRowNum = sheet.getLastRowNum();
        List<ConfigData> configList = tableData.getConfigList();
        Set<String> idSet = new HashSet<>();
        for (int rowIndex = ConfigConstant.CONFIG_FIRST_ROW; rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            short lastCellNum = row.getLastCellNum();
            if (lastCellNum <= ConfigConstant.OUTPUT_COLUMN) {
                // 停止读取配置
                break;
            }
            Cell outputCell = row.getCell(ConfigConstant.OUTPUT_COLUMN);
            String outputType = getCellValue(outputCell);
            OutputTypes output = OutputTypes.getOutputTypes(outputType);
            if (output == OutputTypes.END) {
                // 停止读取配置
                break;
            }
            if (lastCellNum < ConfigConstant.ID_COLUMN) {
                logger.info("excel[{}] table[{}] row[{}] is empty.", fileName, name, (rowIndex + 1));
                break;
            }
            Cell idCell = row.getCell(ConfigConstant.ID_COLUMN);
            String idValue = getCellValue(idCell);
            if (StringUtils.isEmpty(idValue)) {
                // id不存在
                logger.info("excel[{}] table[{}] row[{}] is empty.", fileName, name, (rowIndex + 1));
                break;
            }
            if (idSet.contains(idValue)) {
                logger.error("excel[{}] table[{}] row[{}] id is duplicate.", fileName, name, (rowIndex + 1));
                throw new ParamParseException();
            }
            idSet.add(idValue);
            ConfigData configData = new ConfigData();
            configData.setOutput(output);
            List<Object> configParamList = configData.getParamList();
            // 根据参数读取配置
            for (Param param : paramList) {
                if (param.getOutput() == OutputTypes.NONE) {
                    // 该行不输出 不需要读取
                    configParamList.add(StringUtils.EMPTY);
                    continue;
                }
                ParamType paramType = param.getType();
                int cellIndex = param.getIndex();
                if (lastCellNum < cellIndex) {
                    // 该格子不存在 设置默认值
                    Object defaultValue = paramType.defaultValue();
                    configParamList.add(defaultValue);
                    continue;
                }
                Cell cell = row.getCell(cellIndex);
                String str = getCellValue(cell);
                try {
                    Object value = paramType.parseCell(cell);
                    configParamList.add(value);
                    if (cellIndex == ConfigConstant.ID_COLUMN) {
                        int id = (int) value;
                        configData.setId(id);
                    }
                } catch (ParamParseException e) {
                    logger.warn("excel[{}] table[{}] row[{}] column[{}] id[{}] param[{}] paramType[{}] parse[{}] fail.", fileName, name, (rowIndex + 1),
                            (cellIndex + 1), configData.getId(), param.getKey(), paramType, str);
                    if (cellIndex == ConfigConstant.ID_COLUMN) {
                        // id所在行 直接中断
                        throw e;
                    }
                    // 其他行 强行塞个字符串
                    configParamList.add(str);
                } catch (Exception e) {
                    logger.error("excel[{}] table[{}] row[{}] column[{}] id[{}] param[{}] paramType[{}] parse[{}] error.", fileName, name, (rowIndex + 1),
                            (cellIndex + 1), configData.getId(), param.getKey(), paramType, str);
                    throw e;
                }
            }
            if (configData.getId() < 0) {
                logger.error("parse excel[{}] table[{}] row[{}] error.id<=0", fileName, name, (rowIndex + 1));
                throw new RuntimeException("parse excel[" + fileName + "] table[" + name + "] row[" + (rowIndex + 1) + "] error.id<=0");
            }

            configList.add(configData);
        }
    }

    /**
     * 读取头部 解析参数列表
     * 
     * @param tableData
     * @param sheet
     */
    private static void readHeader(TableData tableData, Sheet sheet) {
        String fileName = tableData.getFileName();
        String tableName = tableData.getName();
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < ConfigConstant.HEADER_LAST_ROW) {
            logger.error("parse excel[{}] table[{}] header error.", fileName, tableName);
            throw new RuntimeException("parse excel[" + fileName + "] table[" + tableName + "] header error.");
        }
        // 第一行为0
        Row nameRow = sheet.getRow(ConfigConstant.PARAM_NAME_ROW);
        Row keyRow = sheet.getRow(ConfigConstant.PARAM_KEY_ROW);
        Row outputRow = sheet.getRow(ConfigConstant.PARAM_OUTPUT_ROW);
        Row typeRow = sheet.getRow(ConfigConstant.PARAM_TYPE_ROW);
        Row remarkRow = sheet.getRow(ConfigConstant.PARAM_REMARK_ROW);
        List<Param> paramList = tableData.getParamList();
        Set<String> keyList = new HashSet<>();
        for (int i = ConfigConstant.ID_COLUMN; i < nameRow.getLastCellNum()//
                && i < keyRow.getLastCellNum()//
                && i < typeRow.getLastCellNum()//
                && i < outputRow.getLastCellNum(); i++) {
            // 参数名
            Cell keyCell = keyRow.getCell(i);
            String key = getCellValue(keyCell);
            if (StringUtils.equalsIgnoreCase(key, "end")) {
                break;
            }
            if (StringUtils.isBlank(key) || StringUtils.equals(key, "-") || StringUtils.equalsIgnoreCase(key, "none")) {
                // 若key为空 或者- 或者 none 则跳过
                continue;
            }
            // 参数类型
            Cell typeCell = typeRow.getCell(i);
            String type = getCellValue(typeCell);
            ParamType paramType = ParamTypes.getParamType(type);
            if (paramType == null || paramType == ParamTypes.NULL) {
                logger.error("parse excel[{}] table[{}] header error.unknown param key[{}] type[{}]", fileName, tableName, key, type);
                throw new RuntimeException(
                        "parse excel[" + fileName + "] table[" + tableName + "] header error.unknown param key[" + key + "] type[" + type + "].");
            }
            if (keyList.contains(key)) {
                logger.error("parse excel[{}] table[{}] header error.repeat param key[{}] type[{}]", fileName, tableName, key, type);
                throw new RuntimeException(
                        "parse excel[" + fileName + "] table[" + tableName + "] header error.repeat param key[" + key + "] type[" + type + "].");
            }
            Cell outputCell = outputRow.getCell(i);
            Cell nameCell = nameRow.getCell(i);
            String name = getCellValue(nameCell);
            String output = getCellValue(outputCell);
            String remark = StringUtils.EMPTY;
            if (i < remarkRow.getLastCellNum()) {
                Cell remarkCell = remarkRow.getCell(i);
                remark = getCellValue(remarkCell);
            }
            Param param = new Param();
            param.setIndex(i);
            param.setName(name);
            param.setKey(key);
            param.setType(paramType);
            param.setRemark(remark);
            OutputTypes outputType = OutputTypes.getOutputTypes(output);
            param.setOutput(outputType);
            paramList.add(param);
            keyList.add(key);
        }
        // 除了id 至少还要有个参数
        if (paramList.size() <= 1) {
            logger.error("parse excel[{}] table[{}] header error.lack valid param size.", fileName, tableName);
            throw new RuntimeException("parse excel[" + fileName + "] table[" + tableName + "] header error.lack valid param size.");
        }
        // 首个参数必须是id
        Param param = paramList.get(0);
        // 第一行必须是id
        if (!checkIdParam(param)) {
            logger.error("parse excel[{}] table[{}] header error.first param error.", fileName, tableName);
            throw new RuntimeException("parse excel[" + fileName + "] table[" + tableName + "] header error.first param error.");
        }
    }

    private static boolean checkIdParam(Param param) {
        if (param.getType() != ParamTypes.INT) {
            return false;
        }
        if (param.getOutput() == OutputTypes.NONE) {
            return false;
        }
        if (param.getIndex() != ConfigConstant.ID_COLUMN) {
            // id参数不是id所在列
            return false;
        }
        String key = param.getKey();
        if (!ConfigConstant.ID_KEY.equals(key)) {
            return false;
        }
        return true;
    }

    /**
     * 获取该格子的值
     * 
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return StringUtils.EMPTY;
        }
        String str = StringUtils.EMPTY;
        CellType cellType = cell.getCellType();
        switch (cellType) {
        case NUMERIC:
            double value = cell.getNumericCellValue();
            str = numberFormat.format(value);
            return str;
        case STRING:
            str = cell.getStringCellValue();
            break;
        case FORMULA:
            str = cell.getCellFormula();
            break;
        case BLANK:
            break;
        case BOOLEAN:
            boolean bool = cell.getBooleanCellValue();
            return Boolean.toString(bool);
        default:
            str = cell.getStringCellValue();
            break;
        }
        if (StringUtils.isEmpty(str)) {
            return StringUtils.EMPTY;
        }
        for (String tmp : ZERO_WIDTH_SPACE_CHARS) {
            str = str.replace(tmp, StringUtils.EMPTY);
        }
        return str.trim().intern();
    }

}
