package paster.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import paster.bean.History;
import paster.bean.Pic;
import paster.utlis.HistoryTools;
import paster.utlis.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class HistoryApi {

    @RequestMapping("/api/getpic")
    @ResponseBody
    public Pic getMsg(HttpServletRequest request, int id) {
        String address = IPUtils.getIpAddress(request);
        History history = HistoryTools.getHistory(address);
        Pic pic = new Pic();
        pic.setFilename(HistoryTools.getRecord(history, id-1).split(":")[1].split("/")[3]);
        pic.setIp(address);
        pic.setPath(HistoryTools.getRecord(history, id-1).split(":")[1]);

        return pic;
    }

    @RequestMapping("/api/getAllPics")
    @ResponseBody
    public Map<String, Object> allPics(HttpServletRequest request) {
        Map map = new TreeMap();
//        String ip = request.getRemoteAddr();
        String ip = "10.1.1.1";
        History history = HistoryTools.getHistory(ip);
        List list = new ArrayList();
        if (history == null) {
            map.put("msg", "fail");
            map.put("data", ip);
            return map;
        }
        for (String s : HistoryTools.allImg(history)) {
            Pic pic = new Pic();
            pic.setFilename(s.split("/")[3]);
            pic.setIp(ip);
            pic.setPath(s);

            list.add(pic);
        }
        map.put("msg", "success");
        map.put("data", list);
        return map;
    }

    @RequestMapping("/api/getAllApi")
    @ResponseBody
    public Map<String, String> getAllApi() {
        Map map = new TreeMap();
        map.put("0", "/api/getConf?conf=*");
        map.put("1", "/api/setConf?key=*&value=*");
        map.put("2", "/api/getpic?id=*");
        map.put("3", "/api/getAllPics");
        map.put("4", "/api/login?password=*");
        map.put("5", "/api/reload");
        map.put("6", "/api/logout");
        map.put("7", "/api/isLogin");
        map.put("8", "/api/getIp");
        map.put("9", "/api/getPics");

        return map;
    }

    @RequestMapping("/api/getIp")
    @ResponseBody
    public Map<String, String> getIp(HttpServletRequest request) {
        Map map = new TreeMap();
        map.put("ip", IPUtils.getIpAddress(request));
        return map;
    }

    @RequestMapping("/api/getPics")
    @ResponseBody
    public Map<String, String> getPics(HttpServletRequest request, String ip) {
        Map map = new TreeMap();
        History history = HistoryTools.getHistory(ip);
        List list = new ArrayList();
        if (history == null) {
            map.put("msg", "fail");
            map.put("data", ip);
            return map;
        }
        for (String s : HistoryTools.allImg(history)) {
            Pic pic = new Pic();
            pic.setFilename(s.split("/")[3]);
            pic.setIp(ip);
            pic.setPath(s);

            list.add(pic);
        }
        map.put("", "success");
        map.put("data", list);


        return map;
    }
}
