package com.cat.zson.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceUtil {
    private final static Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

    /** Pseudo URL prefix for loading from the class path: "classpath:". */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    public static final String SETTINGS_DIR_FILE = "setting";

    /**
     * 获取资源路径
     * 
     * @param resource
     * @return
     */
    public static URL getURL(String resource) {
        if (resource.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = resource.substring(CLASSPATH_URL_PREFIX.length());
            return getURL(path);
        }
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        // 尝试直接获取该资源的url jar包中调用时 只能获取jar包中的该资源
        URL url = classLoader.getResource(resource);
        if (url != null) {
            logger.debug("reource[{}] getResource success.url:{}", resource, url);
            return url;
        }
        url = ClassLoader.getSystemResource(resource);
        if (url != null) {
            logger.debug("reource[{}] getSystemResource success.url:{}", resource, url);
            return url;
        }
        try {
            // try URL
            url = new URL(resource);
            logger.debug("reource[{}] new URL success.url:{}", resource, url);
            return url;
        } catch (MalformedURLException e) {
        }
        // 执行jar包 尝试获取jar包外的资源
        try {
            // 该方式获取的url是在执行启动脚本所在路径下的文件
            File file = new File(resource);
            if (file.exists()) {
                url = file.toURI().toURL();
                logger.debug("reource[{}] find at run path success.url:{}", resource, url);
                return url;
            }
        } catch (MalformedURLException e) {
        }
        try {
            // 尝试获取jar包所在目录下的文件
            // 获取jar包所在目录
            String jarFilePath = ResourceUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            // 转换处理中文及空格
            jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
            File jarFile = new File(jarFilePath);
            File parentFile = jarFile.getParentFile();
            File file = new File(parentFile, resource);
            if (file.exists()) {
                url = file.toURI().toURL();
                logger.debug("reource[{}] find at jar path success.url:{}", resource, url);
                return url;
            }
        } catch (Exception e) {
            logger.error("getURL[" + resource + "] error.", e);
        }
        throw new NullPointerException("Resource location [" + resource + "] is neither a URL not a well-formed file path");
    }

    /**
     * 获取服务器配置文件路径
     * 
     * @param fileName
     * @return
     */
    public static URL getSettingsFileUrl(String fileName) {
        String resource = SETTINGS_DIR_FILE + "/" + fileName;
        URL url = getURL(resource);
        logger.info("reource[{}] getSettingsFileUrl.url:{}", resource, url);
        return url;
    }

    /**
     * 获取该url目录下的所有符合条件的url
     * 
     * @param dirUrl
     * @param filter
     * @return
     * @throws IOException
     */
    public static List<URL> getUrls(URL dirUrl, Predicate<URL> filter) throws IOException {
        if (dirUrl == null) {
            return Collections.emptyList();
        }
        List<URL> urlList = new ArrayList<>();
        String protocol = dirUrl.getProtocol();
        if ("file".equals(protocol)) {
            // jar包外
            String packagePath = URLDecoder.decode(dirUrl.getFile(), "UTF-8");
            File packageFile = new File(packagePath);
            if (!packageFile.exists()) {
                return urlList;
            }
            if (!packageFile.isDirectory()) {
                return urlList;
            }
            File[] childFiles = packageFile.listFiles();
            for (File childFile : childFiles) {
                URL childUrl = childFile.toURI().toURL();
                if (childFile.isDirectory()) {
                    // 文件夹 递归查找子目录
                    List<URL> childUrlList = getUrls(childUrl, filter);
                    urlList.addAll(childUrlList);
                } else {
                    // 文件
                    if (filter != null && !filter.test(childUrl)) {
                        continue;
                    }
                    urlList.add(childUrl);
                }
            }
        } else if ("jar".equals(protocol)) {
            // jar包中
            JarURLConnection jarURLConnection = (JarURLConnection) dirUrl.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            String dirPath = dirUrl.getPath() + "/";
            ClassLoader classLoader = ResourceUtil.class.getClassLoader();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                if (jarEntry.isDirectory()) {
                    continue;
                }
                String name = jarEntry.getName();
                URL url = classLoader.getResource(name);
                if (url == null) {
                    continue;
                }
                // 判断该url是否dirUrl目录下
                String path = url.getPath();
                if (!path.startsWith(dirPath)) {
                    continue;
                }
                if (filter != null && !filter.test(url)) {
                    continue;
                }
                urlList.add(url);
            }
        }
        return urlList;
    }

    /**
     * 获取该资源文件<br>
     * 无法获取jar包中的文件
     * 
     * @param resource
     * @return
     */
    public static File getFile(String resource) {
        URL url = getURL(resource);
        File file = new File(url.getFile());
        return file;
    }
}
