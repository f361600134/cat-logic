package com.cat.battle.service.module;

import java.util.Locale;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.api.request.ReqKickUpPlayer;
import com.cat.battle.common.ServerConstant;
import com.cat.battle.service.module.player.handler.KickUpPlayerCallback;
import com.cat.net.network.tcp.RpcServerStarter;
import com.rpc.core.server.RpcNetService;

@Component
public class InitialRunner implements InitializingBean{
	
	private static final Logger log = LoggerFactory.getLogger(InitialRunner.class);
	
//	@Autowired 
//	private RequesterManager requesterManager;
	
	@Autowired
	private RpcNetService netService;
	
	
	public InitialRunner() {
	}
	
	public void run() throws Exception {
		try {
			this.consoleListener();
		} catch (Exception e) {
			log.error("服务器初始化过程出现异常, 启动失败", e);
		}
	}
	
	public void consoleListener() {
		if (!System.getProperty("os.name").toLowerCase(Locale.US).contains("windows")) {
            // 只监听 windows 系统
            return;
        }
		Thread consoleThread = new Thread(() -> {
            try (Scanner sc = new Scanner(System.in)) {
            	while (true) {
            		 String cmd = sc.next();
                     if(cmd.equals("close")) {
                     	break;
                     }else if(cmd.equals("rpc")) {
                    	 try {
                    		//rpc请求
                    		RpcServerStarter server = (RpcServerStarter)netService.getRpcServer();
//                 			RpcClientStarter client = requesterManager.getClient(ServerConstant.NODE_TYPE_GAME);
                 			if(server == null) {
                 				log.info("没有找到合适的节点, 节点类型{}", ServerConstant.NODE_TYPE_GAME);
                 				continue;
                 			}
                 			ReqKickUpPlayer req = ReqKickUpPlayer.create(1, "GM踢人");
                 			log.info("目标节点类型: {}, server:{}, 被踢玩家id:{}, 原因:{}"
                 					, ServerConstant.NODE_TYPE_GAME, server, req.getPlayerId(), req.getReason());
                 			
                 			server.ask(ServerConstant.NODE_TYPE_GAME, req, new KickUpPlayerCallback());
     					} catch (Exception e) {
     						e.printStackTrace();
     					}
                     }
                     try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
            }finally {
            	System.exit(0);
            }
        });
        consoleThread.setName("os_console_thread");
        consoleThread.setDaemon(true);
        consoleThread.start();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.run();
	}
	
}
