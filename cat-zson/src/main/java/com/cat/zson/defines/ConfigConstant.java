package com.cat.zson.defines;

public class ConfigConstant {

    public final static int PARAM_KEY_ROW = 0;
    public final static int PARAM_TYPE_ROW = 1;
    public final static int PARAM_OUTPUT_ROW = 2;
    public final static int PARAM_NAME_ROW = 3;
    public final static int PARAM_REMARK_ROW = 4;

    public static final int HEADER_LAST_ROW = PARAM_REMARK_ROW;

    public final static int CONFIG_FIRST_ROW = HEADER_LAST_ROW + 1;
    /**
     * 首列参数 也是设置是否读取的列
     */
    public final static int OUTPUT_COLUMN = 0;
    /**
     * 配置id所在列
     */
    public final static int ID_COLUMN = 1;
    
    public final static String ID_KEY="id";

    public final static int TABLE_TYPE_SERVER = 1;
    public final static int TABLE_TYPE_CLIENT = 2;
    public final static int TABLE_TYPE_SERVER_AND_CLIENT = 3;
    public final static int TABLE_TYPE_CLIENT_DEC =4;
    public final static String CUT_TABLE_NAME = "plot";


}
