package com.cat.zson.param;

import org.apache.commons.lang3.StringUtils;

public enum OutputTypes {
    /**
     * 不输出
     */
    NONE(0, false, false, new String[] { "none", "-", "false" }),
    /**
     * 前后端都需要
     */
    ALL(1, true, true, new String[] { "all", "a", "both", "b", "true" }),
    /**
     * 只是服务端需要
     */
    SERVER(2, true, false, new String[] { "server", "s" }),
    /**
     * 只是客户端需要
     */
    CLIENT(3, false, true, new String[] { "client", "c" }),
    /**
     * 客户端剧情文件<br>
     * 只是客户端需要<br>
     * 标记到id所在列的output行 则对应配置表 输出时 每1行参数分割到配置表中
     * 
     */
    CLIENT_DEC(4, false, true, new String[] { "clientdec", "cd" }),
    /**
     * 
     */
    END(5, false, false, new String[] { "end" }),

    ;
    private final int value;

    private final boolean server;
    private final boolean client;
    private final String[] names;

    private OutputTypes(int value, boolean server, boolean client, String[] names) {
        this.value = value;
        this.server = server;
        this.client = client;
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }

    public int getValue() {
        return value;
    }

    public boolean isServer() {
        return server;
    }

    public boolean isClient() {
        return client;
    }

    public static OutputTypes getOutputTypes(String type) {
        OutputTypes[] values = OutputTypes.values();
        for (OutputTypes tmp : values) {
            String[] names = tmp.getNames();
            for (String tmpName : names) {
                if (StringUtils.equalsIgnoreCase(type, tmpName)) {
                    return tmp;
                }
            }
        }
        return OutputTypes.NONE;
    }

    public static OutputTypes valueOf(int value) {
        OutputTypes[] values = OutputTypes.values();
        for (OutputTypes tmp : values) {
            if (tmp.getValue() == value) {
                return tmp;
            }
        }
        return OutputTypes.NONE;
    }
}
