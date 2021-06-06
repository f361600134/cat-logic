package com.cat.zproto.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;

/**
 * 命令服务, 执行指定的命令
 * @auth Jeremy
 * @date 2021年6月6日下午6:13:07
 */
@Service
public class CommandService {

	private static Logger logger = LoggerFactory.getLogger(CommandService.class);
	
	 /**
     * 执行命令
     * 
     * @param command
     */
    public SystemResult execCommand(String command) {
        StringBuffer errorResultSb = new StringBuffer();
        try {
            // 执行脚本并等待脚本执行完成
            Process process = Runtime.getRuntime().exec(command);
            // 正常返回
            BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 错误返回
            BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = infoInput.readLine()) != null) {
                logger.info(line);
            }
            while ((line = errorInput.readLine()) != null) {
                logger.error(line);
                errorResultSb.append(line);
            }
            infoInput.close();
            errorInput.close();
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            int code = process.waitFor();
            if (code == 0) {
                return SystemResult.build(SystemCodeEnum.SUCCESS);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("exec cmd[" + command + "] error.", e);
        }
        return SystemResult.build(SystemCodeEnum.ERROR_EXECUTE_COMMAND_FAIL, errorResultSb.toString());
    }
    
    
}
