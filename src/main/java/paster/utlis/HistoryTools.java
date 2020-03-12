package paster.utlis;

import paster.bean.History;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HistoryTools {

    static FileOutputStream fileOutputStream;
    static Properties properties;

    public static void createHistoryFile(History history) {
        String name = history.getUrl();
        try {
            fileOutputStream = new FileOutputStream("history/" + name + ".txt", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static int addRecord(History history, String value) {


        try {
            // 获取当前
//            int count = Integer.parseInt(HistoryTools.readLastLine(history.getUrl()).split("=")[0]);
            int count = history.getCount();
            write(history.getFileOutputStream(), count + "=" + value + "\n");
            history.setCount(count + 1);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getRecord(History history, int count) {
        properties = history.getProperties();
        String str = null;
        try {
            properties.load(history.getFileInputStream());
            str = properties.getProperty(String.valueOf(count));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static void write(FileOutputStream fileOutputStream, String value) throws IOException {
        fileOutputStream.write(value.getBytes());
    }


    /**
     * 从文件获取History对象
     */
    public static History getHistory(String name) {
        if (!new File("history/" + name + ".txt").exists()) {
            return null;
        }
        String lastLine = null;
        try {
            lastLine = readLastLine(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        History history = new History(name);
        int count = Integer.parseInt(lastLine.split("=")[0]) + 1;
        history.setCount(count);
        try {
            history.setFileInputStream(new FileInputStream("history/" + name + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return history;
    }


    public static String readLastLine(String name) throws IOException {
        File file = new File("history/" + name + ".txt");

        BufferedReader bf = new BufferedReader(new FileReader(file));

        String buf = null;
        String str = null;

        // 1024byte *1024byte = 1MB
        if (file.length() > 1024 * 1024) {
            // 当文件超过1M大小的时候，跳过文本长度的2/3
            bf.skip(file.length() / 3 * 2);
        }

        while ((buf = bf.readLine()) != null) {
            str = buf;
        }
        return str;
    }

    public static List<String> allImg(History history) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < history.getCount(); i++) {
            list.add(HistoryTools.getRecord(history, i).split(":")[1]);
        }

        return list;
    }

    /**
     * history文件夹是否存在
     * @return
     */
    public static boolean historyExist() {
        return new File("history/").exists();
    }
}