package com.cat.server.tcp2;

import com.cat.net.network.controller.DefaultConnectControllerDispatcher;
import com.cat.net.network.controller.IControllerDispatcher;
import com.cat.net.network.tcp.TcpServerStarter;

public class TcpServer {
	public static void main(String[] args) {
		try {
			String ip = "0.0.0.0";
			int port = 7079;
			IControllerDispatcher serverHandler = new DefaultConnectControllerDispatcher();
			TcpServerStarter starter = new TcpServerStarter(serverHandler, ip, port);
			starter.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
