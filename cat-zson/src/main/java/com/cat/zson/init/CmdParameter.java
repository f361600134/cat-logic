package com.cat.zson.init;

/**
 * 命令行解释处理
 * 
 * @since 2018-08-30
 * @version 1.0
 */
import com.beust.jcommander.Parameter;

public class CmdParameter {
    @Parameter(names = "-act", description = "导出类型")
    /**
     *    json or java
     */
    public String act = "";

    @Parameter(names = "-source", description = "excel源文件路径")
    public String path = "";

    @Parameter(names = "-target", description = "json生成输出路径")
    public String target = "";
}