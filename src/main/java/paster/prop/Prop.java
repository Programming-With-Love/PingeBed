package paster.prop;

import paster.log.Logger;
import paster.utlis.LittleTools;

import java.io.*;
import java.util.Properties;

public class Prop {

    private static Properties properties = null;

    /**
     * 初始化配置文件
     */
    public static void init() {
        // 没有配置文件
        if (!new File(System.getProperty("user.dir") + "/config.ini").exists()) {
            properties = new Properties();
            Logger.log(Logger.INFO,"[Prop] init Properties");
            properties.setProperty("password", "123456");
            properties.setProperty("size", LittleTools.freeSize());
            properties.setProperty("anonymous", "true");
            properties.setProperty("imageUploadCount", "0");
            properties.setProperty("version", "1.1");
            save();
            return;
        }

        // 有配置文件
        try {
            properties = new Properties();
            properties.load(new FileReader(System.getProperty("user.dir") + "/config.ini"));
            Logger.log(Logger.INFO,"[Prop] properties haven loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存配置
     */
    public static void save() {
        try {
            FileOutputStream config = new FileOutputStream(System.getProperty("user.dir") + "/config.ini");
            properties.store(config, "Save Config File");
            Logger.log(Logger.INFO,"[Prop] Saved the Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void modify(String key, String value) {
        properties.setProperty(key, value);
        Logger.log(Logger.INFO,"[Prop] Modify " + key + "=" + value);
        save();
    }

    public static void modify(String key, int value) {
        properties.setProperty(key, String.valueOf(value));
        Logger.log(Logger.INFO,"[Prop] Modify " + key + "=" + value);
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

        Logger.log(Logger.INFO,"[Prop] Properties Reload");
    }
}