package com.cat.server.tcp2;

import com.cat.net.network.client.TcpClientStarter;
import com.cat.net.network.controller.DefaultConnectControllerDispatcher;
import com.cat.net.network.controller.IControllerDispatcher;

public class TcpClient {
	public static void main(String[] args) {
		try {
			int curId = 1, id = 1;
			String ip = "0.0.0.0";
			int port = 7079;
			IControllerDispatcher handler = new DefaultConnectControllerDispatcher();
			TcpClientStarter starter = new TcpClientStarter(curId, id, "game", ip, port, handler);
			starter.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
