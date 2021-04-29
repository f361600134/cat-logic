package com.cat.robot.util.param;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageBase {

	public static final Logger logger = LoggerFactory.getLogger(ParamAnalysis.class);

	/**
	 * 获取指定目录下的类文件
	 * 
	 * @param path
	 * @return
	 * @return Set<Class<?>>
	 * @date 2019年2月12日下午3:46:37
	 */
	public static Set<Class<?>> getAllClassByPackage(String path) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		String package2Path = path.replace('.', '/');
		try {
			Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(package2Path);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if (protocol.equals("file")) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					doScanPackageClassesByFile(classes, path, filePath);
				}
			}
		} catch (Exception e) {
			logger.error("获取所有类文件错误", e);
		}
		logger.info("classes:{}", classes);
		return classes;
	}

	/**
	 * 以文件的方式扫描包下的所有Class文件
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private static void doScanPackageClassesByFile(Set<Class<?>> classes, String packageName, String packagePath) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义文件过滤规则
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return false;
				}
				String filename = file.getName();
				if (filename.indexOf('$') != -1) {
					return false;
				}
				return true;
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				doScanPackageClassesByFile(classes, packageName + "." + file.getName(), file.getAbsolutePath());
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
					if (loadClass != null) {
						classes.add(loadClass);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		PackageBase.getAllClassByPackage("com.game.robot");
	}

}
