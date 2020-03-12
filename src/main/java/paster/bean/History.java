package paster.bean;


import paster.utlis.HistoryTools;

import java.io.*;
import java.util.Properties;

public class History {

    Properties properties;

    // 地址
    String url;

    // 上传图片数量
    int count = 0;

    // 获取History文件的FileOutputStream对象
    FileOutputStream fileOutputStream;

    FileInputStream fileInputStream;

    public History(String url) {
        this.url = url;
        try {
            if (!HistoryTools.historyExist()) {
                File history = new File("history/");
                history.mkdir();
            }
            this.fileOutputStream = new FileOutputStream("history/" + url + ".txt", true);
            this.fileInputStream = new FileInputStream("history/" + url + ".txt");
            this.properties = new Properties();
            this.properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return count;
    }

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public FileOutputStream getFileOutputStream() {
        return fileOutputStream;
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}