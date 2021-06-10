package com.cat.zproto.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压缩攻击类
 * 
 * @author Administrator
 */
public class ZipUtil {
	private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
		if (file == null) {
			return;
		}
		if (file.isFile()) {
			int bufferLen = 1024;
			byte[] data = new byte[bufferLen];

			String subPath = file.getAbsolutePath();
			int index = subPath.indexOf(srcRootDir);
			if (index != -1) {
				subPath = subPath.substring(srcRootDir.length() + File.separator.length());
			}
			ZipEntry entry = new ZipEntry(subPath);
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			int count;
			while ((count = bis.read(data, 0, bufferLen)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
			zos.closeEntry();
		} else {
			File[] childFileList = file.listFiles();
			for (int n = 0; n < childFileList.length; n++) {
				childFileList[n].getAbsolutePath().indexOf(file.getAbsolutePath());
				zip(srcRootDir, childFileList[n], zos);
			}
		}
	}

	/**
	 * 压缩,
	 * 
	 * @param srcPath     源目录
	 * @param zipPath     压缩路径
	 * @param zipFileName 压缩文件名
	 * @throws Exception
	 */
	public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception {
		if ((srcPath == null) || (zipPath == null) || (zipFileName == null) || ("".equals(srcPath))
				|| ("".equals(zipPath)) || ("".equals(zipFileName))) {
			throw new Exception("参数错误");
		}
		if (!zipFileName.endsWith(".zip")) {
			zipFileName = zipFileName.concat(".zip");
		}
		CheckedOutputStream cos = null;
		ZipOutputStream zos = null;
		try {
			File srcFile = new File(srcPath);
			srcPath = srcFile.getAbsolutePath();
			if ((srcFile.isDirectory()) && (zipPath.indexOf(srcPath) != -1)) {
				throw new Exception("参数错误");
			}
			File zipDir = new File(zipPath);
			zipPath = zipDir.getAbsolutePath();
			if ((!zipDir.exists()) || (!zipDir.isDirectory())) {
				zipDir.mkdirs();
			}
			String zipFilePath = zipPath + File.separator + zipFileName;
			File zipFile = new File(zipFilePath);
			if (zipFile.exists()) {
				//文件已存在, 则删除
//                SecurityManager securityManager = new SecurityManager();
//                securityManager.checkDelete(zipFilePath);
				zipFile.delete();
			}
			cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
			zos = new ZipOutputStream(cos);

			String srcRootDir = srcPath;
			if (srcFile.isFile()) {
				int index = srcPath.lastIndexOf(File.separator);
				if (index != -1) {
					srcRootDir = srcPath.substring(0, index);
				}
			}
			zip(srcRootDir, srcFile, zos);
			zos.flush();
			return;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解压缩
	 * 
	 * @param zipFilePath        压缩路径
	 * @param unzipFilePath      解压缩路径
	 * @param includeZipFileName 是否包含压缩
	 * @throws Exception
	 */
	public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception {
		if ((zipFilePath == null) || (unzipFilePath == null) || ("".equals(zipFilePath))
				|| ("".equals(unzipFilePath))) {
			throw new Exception("参数错误");
		}
		File zipFile = new File(zipFilePath);
		if (includeZipFileName) {
			String fileName = zipFile.getName();
			if ((fileName != null) && (!"".equals(fileName))) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			unzipFilePath = unzipFilePath + File.separator + fileName;
		}
		File unzipFileDir = new File(unzipFilePath);
		if ((!unzipFileDir.exists()) || (!unzipFileDir.isDirectory())) {
			unzipFileDir.mkdirs();
		}
		ZipEntry entry = null;
		String entryFilePath = null;
		String entryDirPath = null;
		File entryFile = null;
		File entryDir = null;
		int index = 0;
		int count = 0;
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
		while (entries.hasMoreElements()) {
			entry = (ZipEntry) entries.nextElement();

			entryFilePath = unzipFilePath + File.separator + entry.getName();

			index = entryFilePath.lastIndexOf(File.separator);
			if (index != -1) {
				entryDirPath = entryFilePath.substring(0, index);
			} else {
				entryDirPath = "";
			}
			entryDir = new File(entryDirPath);
			if ((!entryDir.exists()) || (!entryDir.isDirectory())) {
				entryDir.mkdirs();
			}
			entryFile = new File(entryFilePath);
			if (entryFile.exists()) {
				SecurityManager securityManager = new SecurityManager();
				securityManager.checkDelete(entryFilePath);

				entryFile.delete();
			}
			bos = new BufferedOutputStream(new FileOutputStream(entryFile));
			bis = new BufferedInputStream(zip.getInputStream(entry));
			while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
				bos.write(buffer, 0, count);
			}
			bos.flush();
			bos.close();
		}
	}

	public static void main(String[] args) {
		try {
			zip("E:\\aaa", "E:\\", "aaa.zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
