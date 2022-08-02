package com.base.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropUtils {
    public static String path;
    private static Properties properties;

    // 初始化
    static {
        load();
    }

    // 初始化
    private static boolean load() {
        if (properties != null) return true;// 已经加载过

        // 有文件的路径才进行加载
        if (StringUtils.isNotBlank(path)) {
            properties = new Properties();
            InputStream in = null;
            try {
                in = new BufferedInputStream(new FileInputStream(path));
                properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }

    // 获取Properties
    public static Properties getProp() {
        if (load()) {
            return properties;
        }

        return null;
    }

    // 获取值
    public static String getValue(String key) {
        if (load()) {
            return properties.getProperty(key);
        }

        return null;
    }

    // 存储值
    public static void setValue(String key, String value) {
        if (load()) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(path, false);
                properties.setProperty(key, value);
                properties.store(new OutputStreamWriter(out, StandardCharsets.UTF_8), "serial");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != out) {
                    try {
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
