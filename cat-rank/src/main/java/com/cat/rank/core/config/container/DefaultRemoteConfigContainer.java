package com.cat.rank.core.config.container;

import com.cat.rank.common.ServerConfig;
import com.cat.rank.common.SpringContextHolder;
import com.cat.rank.core.config.annotation.ConfigUrl;
import com.cat.rank.utils.HttpClientUtil;
import com.cat.rank.utils.MD5;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 	远程配置容器 
 * @author Jeremy
 */
public class DefaultRemoteConfigContainer<T extends IGameConfig> extends AbstractConfigContainer<T>{

	/**
     * 本次加载成功时 配置所使用的格式
     */
    protected String md5;
    
	public DefaultRemoteConfigContainer(Class<T> configClazz) {
		super(configClazz);
	}
	
	@Override
	public String getFileName() {
		ConfigUrl configUrl = configClazz.getAnnotation(ConfigUrl.class);
		if (configUrl == null) {
			  logger.error("config[{}] file path is null.", configClazz.getName());
	          throw new NullPointerException("config[" + configClazz.getName() + "] file path is null.");
		}
		return configUrl.value();
	}

	@Override
	protected String readContent() throws IOException {
		ServerConfig config = SpringContextHolder.getBean(ServerConfig.class);
		String url = config.getRemoteUrl() + getFileName();
		if (StringUtils.isEmpty(url)) {
            logger.error("config[{}] read url[{}] error.url is empty.", configClazz.getName(), url);
            throw new NullPointerException("config[" + configClazz.getName() + "] url is empty.");
        }
		try {
			String content = HttpClientUtil.doGet(url);
			 if (StringUtils.isEmpty(content)) {
	            /*	后台读到的配置为空字符串
			            * 视为后台正在清空文件开始写入
			            * 直接视为错误
			            * 正确的空配置 至少应有[] 或{}
	             */
	            logger.error("config[{}] read url[{}] fail.content is empty.", configClazz.getName(), url);
	            throw new NullPointerException("config[" + configClazz.getName() + "] url[" + url + "] content is empty.");
	        }
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("config[{}] read url[{}] fail.", configClazz.getName(), url);
		}
		return null;
	}
	
	@Override
	public void load(String content) throws Exception {
		super.load(content);
		md5 = MD5.digest(content, true);
	}
	
	/**
	 * 远程配置刷新
	 */
	@Override
	public void refresh() {
		 try {
            String content = readContent();
            String newMd5 = MD5.digest(content, true);
            if (StringUtils.equals(md5, newMd5)) {
                // 2次配置内容一致不再刷新
                return;
            }
            load(content);
            logger.info("config[{}] refresh.md5[{}]", configClazz.getName(), newMd5);
        } catch (Exception e) {
            logger.error("config[{}] refresh fail.", configClazz.getName(), e);
        }
	}
	

}
