package paster.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void log(String msg) {
        System.out.println("[ImgBed][Log][" + simpleDateFormat.format(new Date()) + "] " + msg);
    }
}
