package com.cat.server.game.module.console;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.game.module.console.annotation.Console;
import com.cat.server.game.module.console.type.IConsole;

/**
 * 内部使用, 接收控制台命令, 执行关服, 模拟请求等特定操作, 用于开发人员调试<br>
 * 做了限制, 仅用于开发环境, 不能使用在测试, 以及生产环境
 * @author Jeremy
 *
 */
@Service
class ConsoleService implements ILifecycle {
	
	private static final Logger log = LoggerFactory.getLogger(ConsoleService.class);

	@Autowired
	private List<IConsole> consoleList;
	/**
	 * key: 命令头 value: 命令
	 */
	private Map<String, IConsole> consoles = new HashMap<>();

	@Override
	public void start() throws Throwable {

		// 仅用于调试, 所以系统不匹配, 则不初始化此内容
		if (!System.getProperty("os.name").toLowerCase(Locale.US).contains("windows")) {
			// 只监听 windows 系统
			return;
		}

		// 初始化命令
		for (IConsole obj : consoleList) {
			Console console = obj.getClass().getAnnotation(Console.class);
			String name = console.value();
			this.consoles.put(name, obj);
		}

		Thread consoleThread = new Thread(() -> {
			try (Scanner sc = new Scanner(System.in)) {
				while (true) {
					String cmd = sc.next();
					String[] cmdArr = null;
					if(cmd.contains(",")) {//包含分隔符
						cmdArr = cmd.split(",");
					}
					String key = cmdArr == null ? cmd : cmdArr[0];
					IConsole console = consoles.get(key);
					if(console == null) {
						log.info("can not find console:{}", cmd);
						continue;
					}
					try {
						console.process(cmdArr);
					}catch (Exception e) {
						e.printStackTrace();
					}
					Thread.sleep(1000L);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
		consoleThread.setName("os_console_thread");
		consoleThread.setDaemon(true);
		consoleThread.start();
	}

}
