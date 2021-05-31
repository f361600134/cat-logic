package com.cat.zson.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static final char EXTENSION_SEPARATOR = '.';

    /**
     * 获取该目录下所有文件 含目录下的目录
     * 
     * @param dirFile
     * @param filter
     * @return
     */
    public static List<File> getFiles(File dirFile, FileFilter filter) {
        List<File> list = new ArrayList<>();
        File[] files = dirFile.listFiles();
        if (files == null) {
            logger.error("dirFile:" + dirFile);
            return list;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                List<File> tmpList = getFiles(file, filter);
                list.addAll(tmpList);
            } else {
                if (filter == null || filter.accept(file)) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * 获取该目录下所有文件
     * 
     * @param filePath
     * @return
     */
    public static List<File> getFiles(String filePath) {
        File file = new File(filePath);
        return getFiles(file, null);
    }

    /**
     * 获取该目录下所有满足条件的文件
     * 
     * @param filePath
     * @param filter
     * @return
     */
    public static List<File> getFiles(String filePath, FileFilter filter) {
        File file = new File(filePath);
        return getFiles(file, filter);
    }

    /**
     * 获取该目录(含目录中的目录)下以该后缀结尾的所有文件
     * 
     * @param filePath
     * @param extension
     * @return
     */
    public static List<File> getFilesWithExtension(String filePath, String extension) {
        File dirFile = new File(filePath);
        return getFilesWithExtension(dirFile, extension);
    }

    /**
     * 获取该目录(含目录中的目录)下以该后缀结尾的所有文件
     * 
     * @param dirFile
     * @param extension
     * @return
     */
    public static List<File> getFilesWithExtension(File dirFile, String extension) {
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                boolean flag = file.isFile() && file.getName().endsWith(extension);
                return flag;
            }
        };
        return getFiles(dirFile, filter);
    }

    public static final String readFile(File file) {
        return readFile(file, Charsets.UTF_8);
    }

    public static final String readFile(File file, Charset charset) {
        if (file == null) {
            throw new NullPointerException("file is null");
        }
        try (InputStream is = new FileInputStream(file)) {
            byte[] data = new byte[is.available()];
            is.read(data);
            return new String(data, charset);
        } catch (IOException e) {
            logger.error("read file[{}] error.", file.getName());
        }
        return StringUtils.EMPTY;
    }

    public static final String readInputStream(InputStream inputStream) {
        try {
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            return new String(data, Charsets.UTF_8);
        } catch (IOException e) {
            logger.error("read inputStream[{}] error.", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 移除文件名中的扩展名
     * 
     * @param fileName
     * @return
     */
    public static String removeExtension(String fileName) {
        int index = fileName.lastIndexOf(EXTENSION_SEPARATOR);
        if (index != -1) {
            return fileName.substring(0, index);
        }
        return fileName;
    }

//    /**
//     * 删除该文件夹下的所有文件/文件夹<br>
//     * 目录本身不删除
//     * 
//     * @param path
//     * @return
//     */
//    public static boolean deleteDir(String path) {
//        File file = new File(path);
//        if (!file.exists()) {
//            return false;
//        }
//        String[] content = file.list();// 取得当前目录下所有文件和文件夹
//        for (String name : content) {
//            File temp = new File(path, name);
//            if (temp.isDirectory()) {// 判断是否是目录
//                // 一些目录不进行删除
//                if (Settings.getNoDeleteDir().contains(temp.getName())) {
//                    continue;
//                }
//                deleteDir(temp.getAbsolutePath());// 递归调用，删除目录里的内容
//                temp.delete();// 删除空目录
//            } else {
//                if (!temp.delete()) {// 直接删除文件
//                    logger.warn("Failed to delete{} ", name);
//                }
//            }
//        }
//        return true;
//    }

//    /**
//     * 清理指定目录<br>
//     * 若目录不存在 则新建
//     * 
//     * @param path
//     * @return
//     */
//    public static File clearOrCreateDir(String path) {
//        deleteDir(path);
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        return file;
//    }

    /**
     * 清理指定目录<br>
     * svn相关文件不清理<br>
     * 若目录不存在 则新建
     * 
     * @param path
     * @return
     */
    public static File clearOrCreateDirWithoutSvn(String path) {
//        deleteDirWithoutSvn(path);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

//    private static boolean deleteDirWithoutSvn(String path) {
//        File file = new File(path);
//        if (!file.exists()) {
//            return false;
//        }
//        String[] content = file.list();// 取得当前目录下所有文件和文件夹
//        for (String name : content) {
//            File temp = new File(path, name);
//            if (temp.isDirectory()) {// 判断是否是目录
//                if (".svn".equals(name) || Settings.getNoDeleteDir().contains(name)) {
//                    // svn相关目录 和指定的不删除的目录
//                    continue;
//                }
//                deleteDir(temp.getAbsolutePath());// 递归调用，删除目录里的内容
//            }
//            boolean success = temp.delete();//
//            logger.warn("delete file[{}] {}", temp.getPath(), success ? "success" : "fail");
//        }
//        return true;
//    }
}
