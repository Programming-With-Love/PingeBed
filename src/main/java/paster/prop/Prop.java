package paster.prop;

import paster.log.Logger;
import paster.utlis.LittleTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Prop {

    public static Properties properties = new Properties();
    private static FileOutputStream config;

    // 上传的图片数量
    static int count = 0;

    static {
        init();
    }


    /**
     * 初始化配置文件
     */
    public static void init() {
        if (!new File(System.getProperty("user.dir") + "/config.ini").exists()) {
            Logger.log("[Prop] init Properties");
            properties.setProperty("password", "123456");
            properties.setProperty("size", LittleTools.freeSize());
            properties.setProperty("anonymous", "true");
            properties.setProperty("imageUploadCount", String.valueOf(count));
            properties.setProperty("version", "1.1");
            save();
            return;
        }
        try {
            properties.load(new FileInputStream(System.getProperty("user.dir") + "/config.ini"));
            Logger.log("[Prop] properties haven loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存配置
     */
    public static void save() {
        try {
            config = new FileOutputStream(System.getProperty("user.dir") + "/config.ini");
            properties.store(config, "Save Config File");
            Logger.log("[Prop] Saved the Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void modify(String key, String value) {
        properties.setProperty(key, value);
        Logger.log("[Prop] Modify " + key + "=" + value);
        save();
    }

    public static void modify(String key, int value) {
        properties.setProperty(key, String.valueOf(value));
        Logger.log("[Prop] Modify " + key + "=" + value);
        save();
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void reload() {
        properties.setProperty("password", "123456");
        properties.setProperty("size", LittleTools.freeSize());
        properties.setProperty("anonymous", "on");
        properties.setProperty("imageUploadCount", "0");
        properties.setProperty("version", "1.1");

        Logger.log("[Prop] Properties Reload");
    }
}