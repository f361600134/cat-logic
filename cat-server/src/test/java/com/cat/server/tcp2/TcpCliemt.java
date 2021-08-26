package com.cat.server.tcp2;

import com.cat.net.network.client.TcpClientStarter;
import com.cat.net.network.controller.DefaultConnectController;
import com.cat.net.network.controller.IConnectController;

public class TcpCliemt {
	public static void main(String[] args) {
		try {
			String ip = "0.0.0.0";
			int port = 1111;
			IConnectController handler = new DefaultConnectController();
			TcpClientStarter starter = new TcpClientStarter(handler, ip, port);
			starter.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
