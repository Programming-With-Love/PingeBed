package paster.controller.admin.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import paster.prop.Prop;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ConfigAction {

    @RequestMapping("/api/getConf")
    @ResponseBody
    public Map<String, String> getConf(HttpSession session, String conf) {
        Map map = new HashMap();
        if (!AdminAction.isLogin(session)) {
            map.put("msg", "fail");
            return map;
        }
        String msg = Prop.getProperty(conf);

        map.put("msg", "success");
        map.put("conf", conf);
        map.put("value", msg);
        return map;
    }

    @RequestMapping("/api/setConf")
    @ResponseBody
    public Map<String, String> setConf(HttpSession session, String key, String value) {
        Map map = new HashMap();
        if (!AdminAction.isLogin(session)) {
            map.put("msg", "fail");
            return map;
        }
        Prop.modify(key, value);

        map.put("msg", "success");
        map.put(key, value);
        return map;
    }

    @RequestMapping("/api/reload")
    @ResponseBody
    public Map<String, Object> reload(HttpSession session) {
        Map map = new HashMap();

        if (!AdminAction.isLogin(session)) {
            map.put("msg", "fail");
            return map;
        }
        Prop.reload();
        map.put("msg", "success");
        return map;
    }
}
