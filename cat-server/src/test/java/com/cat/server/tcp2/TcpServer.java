package com.cat.server.tcp2;

import com.cat.net.network.controller.DefaultConnectController;
import com.cat.net.network.controller.IConnectController;
import com.cat.net.network.tcp.TcpServerStarter;

public class TcpServer {
	public static void main(String[] args) {
		try {
			String ip = "0.0.0.0";
			int port = 1111;
			IConnectController serverHandler = new DefaultConnectController();
			TcpServerStarter starter = new TcpServerStarter(serverHandler, ip, port);
			starter.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
