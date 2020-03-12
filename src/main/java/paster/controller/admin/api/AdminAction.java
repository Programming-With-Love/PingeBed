package paster.controller.admin.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import paster.prop.Prop;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class AdminAction {

    @RequestMapping("/api/isLogin")
    @ResponseBody
    public static boolean isLogin(HttpSession session) {
        Boolean logged;
        try {
            logged = Boolean.valueOf(session.getAttribute("login").toString());
            if (logged) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
//        Boolean logged = Boolean.valueOf(session.getAttribute("login").toString());
    }

    public void logOut(HttpSession session) {
        session.setAttribute("login", false);
    }

    @RequestMapping("/api/login")
    @ResponseBody
    public Map<String, Object> login(HttpSession session, String password) {
        Map map = new TreeMap();
        String pwd = Prop.getProperty("password");
        if (!pwd.equals(password)) {
            map.put("msg", "fail");
            return map;
        }
        session.setAttribute("login", true);
        List<String> conf = new ArrayList<>();
        conf.add(Prop.getProperty("anonymous"));
        conf.add(Prop.getProperty("imageUploadCount"));
        conf.add(Prop.getProperty("size"));
        conf.add(Prop.getProperty("password"));
        conf.add(Prop.getProperty("version"));

        map.put("msg", "success");
        map.put("data", conf);

        return map;
    }


    @RequestMapping("/api/logout")
    @ResponseBody
    public Map<String, String> logout(HttpSession session) {
        Map map = new TreeMap();

        if (isLogin(session)) {
            logOut(session);
            map.put("msg", "success");

            return map;
        }

        map.put("msg", "fail");
        return map;
    }

}
