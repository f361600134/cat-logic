package com.cat.server.core.lifecycle;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  控制系统启动的生命周期, 根据权重排序
 * @author Jeremy
 *
 */
@Component
public class LifecycleManager {
	
	@Autowired
	private List<ILifecycle> lifecycles;
	
	private Logger logger = LoggerFactory.getLogger(LifecycleManager.class);

	public void start() {
        lifecycles.sort(Comparator.comparingInt(l -> l.priority()));
        for (ILifecycle lifecycle : lifecycles) {
            logger.info("启动 [{}]服务!", lifecycle.name());
            try {
                lifecycle.start();
            } catch (Throwable e) {
                logger.error("服务[{}]启动失败...", lifecycle.name(), e);
            }
        }
	}
	
	public void stop() {
        // 倒序关闭服务
        lifecycles.sort((l1, l2) -> Integer.compare(l2.priority(), l1.priority()));
        lifecycles.forEach(service -> {
            logger.info("关闭 [{}] 服务!", service.name());
            try {
                service.stop();
            } catch (Throwable e) {
                logger.error("服务{}关闭失败...", service.name(), e);
            }
        });
	}
	
}
