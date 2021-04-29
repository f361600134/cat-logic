package com.cat.robot.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.Packet;
import com.cat.robot.util.protocol.ProtocolBuilder;
import com.cat.robot.util.protocol.ProtocolManager;
import com.google.protobuf.GeneratedMessageLite;

public class RecordingReadUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RecordingReadUtil.class);
	
	private static final String PATH = "resource/operation.properties";
	
	public static LinkedList<SavePacket> fromFile() {
		LinkedList<SavePacket> scriptRecords = new LinkedList<>();
		try {
			List<String> strs = FileUtils.readLines(new File(PATH), "utf8");
			for (String cmd : strs) {
				if (cmd.equals("")) {//过滤空行
					continue;
				}
				if (cmd.startsWith("#")) {//过滤#号
					continue;
				}
				int sharpIndex = cmd.indexOf("#"); //过滤注释
				if (sharpIndex > 0) {
					cmd = cmd.substring(0, sharpIndex);
				}
				String[] intact = cmd.split(" ");
				String protocolStr = intact[0];
				String [] inputValues = new String[] {};
				if (intact.length > 1 && intact[1] != null) {
					inputValues = intact[1].split("_");
				}
				GeneratedMessageLite lite = (GeneratedMessageLite)ProtocolBuilder.build(protocolStr, inputValues);
				if (lite == null) {
					continue;
				}
				//FIXME
				Packet packet = new Packet((short)ProtocolManager.getProtocolId(protocolStr), lite.toByteArray());
				SavePacket savePacket = new SavePacket(packet);
				scriptRecords.add(savePacket);
			}
//			System.out.println(scriptRecords);
		} catch (IOException e1) {
			logger.error("e1:{}", e1);
		}
		return scriptRecords;
	}
	
	public static class SavePacket {
		public final long time;
		public final Packet packet;

		public SavePacket(Packet packet) {
			this.time = System.currentTimeMillis();
			this.packet = packet;
		}

		public SavePacket(long time, Packet packet) {
			this.time = time;
			this.packet = packet;
		}
	}
}


