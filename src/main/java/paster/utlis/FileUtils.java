package paster.utlis;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import paster.bean.History;
import paster.log.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 工具类
 * 针对于文件
 */
public class FileUtils {


    static String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploadImages/";

    static Date date;
    public static File root = new File("/");
    static File uploadDir = new File(path);

    static {
        checkDir(uploadDir);
    }   

    /**
     * 保存上传的文件到本地
     * @param multipartFile 被上传的文件
     */
    public static Map<String, String> saveFile(MultipartFile multipartFile, String address) {
        Map map = new HashMap();
        String time = datePartition();

        String filename = multipartFile.getOriginalFilename();
        try {
            // 后缀名
            String suffixName = LittleTools.getSuffix(filename);
            if (suffixName != null) {
                // 是否是图片文件
                if (LittleTools.isPic(LittleTools.getSuffix(filename))) {
                    String name = UUID.randomUUID() + suffixName;
                    multipartFile.transferTo(new File( time + "/" + name));
                    Logger.log("[Upload] save the " +filename);


                    // 先判断history文件是否存在
                    History history = HistoryTools.getHistory(address);
                    if (history == null) {
                        history = new History(address);
                    }
                    HistoryTools.addRecord(history, filename + ":/" + getDir() + "/" + name);
                    map.put("state", "ok");
                    map.put("msg", "/uploadImages/" + getDir() + "/" + name);
                } else {
                    Logger.log("[Upload] 用户上传非图片文件夹");
                    map.put("state", "!pic");
                    map.put("msg", "不是图片文件");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state", "error");
            map.put("msg", "出错，请重试");
        }

        return map;
    }


    /**
     * 根据时间创建文件夹
     * @return 返回文件夹的路径
     */
    public static String datePartition() {
        date = new Date();

        int today = date.getDate();
        int toMonth = date.getMonth() + 1;
        int toYear = date.getYear() + 1900;

        // D:/uploadImages/2020/2-18 格式
        File todayDir = new File(uploadDir.getAbsolutePath() + "/" + toYear + "/" + toMonth + "-" + today);

        checkDir(todayDir);

        return todayDir.getAbsolutePath();
    }

    /**
     * 判断uploadImage文件夹是否被创建
     */
    public static void checkDir(File dir) {
        if (!dir.exists()) dir.mkdirs();
    }


    /**
     * 网络流下载文件
     * @param address 网址
     */
    public static Map<String, String> cloneFile(String address, String ip) {
        Map map = new HashMap();
        // 获取文件路径
        String path = datePartition();
        String suffix = LittleTools.getSuffix(address);
        if (suffix == null || !LittleTools.isPic(suffix)) {
            Logger.log("[Clone] 用户上传其他文件");
            map.put("state", "!pic");
            map.put("msg", "请上传图片文件!");
            return map;
        }
        String name = address.split("/")[address.split("/").length-1];
        String filename = UUID.randomUUID() + suffix;


        try {
            // 创建URL网络流对象
            URL url = new URL(address);
            // 打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置请求方式
            conn.setRequestMethod("GET");
            // 设置超时时间
            conn.setConnectTimeout(5 * 1000);
            // 通过输入流获取图片数据
            InputStream is = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据
            byte[] data = readInputStream(is);
            File imageFile = new File(path + "/" + filename);
            //创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
            //关闭输出流
            outStream.close();

            History history = HistoryTools.getHistory(ip);
            if (history == null) {
                history = new History(ip);
            }
            HistoryTools.addRecord(history, name + ":/" + getDir() + "/" + filename);
            Logger.log("[Clone] Clone Successfully");

            map.put("state", "ok");
            map.put("org", name);
            map.put("msg", "/uploadImages/" + getDir() + "/" + filename);
            return map;
        } catch (MalformedURLException e) {
            Logger.log("[Clone] 没有指定协议");
            map.put("state", "!full");
            map.put("msg", "地址错误，请重新输入网址!");
            return map;
        } catch (FileNotFoundException e) {
            Logger.log("[Clone] 保存错误");
            map.put("state", "repeat");
            map.put("msg", "克隆失败，请重试!");
            return map;
        } catch (ProtocolException e) {
            Logger.log("[Clone] 无法打开传输");
            map.put("state", "!url");
            map.put("msg", "无法克隆此图片，请检查网址无误后再进行上传!");
            return map;
        } catch (IOException e) {
            map.put("state", "error");
            map.put("msg", "上传错误，请重新上传!");
            Logger.log("[Clone] 上传错误");
            return map;
        }
    }

    /**
     * 获取网络图片的字节数组
     * @param is 网络流得到的输入流
     * @return 返回字节数组
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 创建缓冲
        byte[] buffer = new byte[1024];
        int len = 0;
        while (true) {
            try {
                if (!((len = is.read(buffer)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream.write(buffer, 0, len);
        }
        // 关闭输入流
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 输出字节数组
        return outputStream.toByteArray();
    }

    public static String getDir() {
        date = new Date();

        int today = date.getDate();
        int toMonth = date.getMonth() + 1;
        int toYear = date.getYear() + 1900;

        return toYear + "/" + toMonth + "-" + today;
    }
}
