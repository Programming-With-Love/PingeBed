package paster.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String WARN = "WARN";

    public static final String DEBUG = "DEBUG";

    public static final String INFO = "INFO";

    public static void log(String level, String msg) {
        System.out.println("[ImgBed][" + level +"][" + simpleDateFormat.format(new Date()) + "] " + msg);
    }
}
