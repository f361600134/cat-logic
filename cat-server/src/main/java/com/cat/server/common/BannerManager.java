package com.cat.server.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class BannerManager {

	public static void banner() {
		//输出banner
    	File file = new File("src/main/resources/banner.txt");
    	if (!file.isFile()) {
			return;
		}
		try {
			String context = FileUtils.readFileToString(file, "utf-8");
			System.out.println(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
