package paster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import paster.log.Logger;
import paster.prop.Prop;
import paster.utlis.FileUtils;
import paster.utlis.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;


@Controller
public class UploadController {

    public static boolean anonymous = true;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> upload(MultipartFile file, HttpServletRequest request) {
        anonymous = Boolean.parseBoolean(Prop.getProperty("anonymous"));
        if (anonymous) {
            String address = IPUtils.getIpAddress(request);
            Logger.log("地址: " + address + "上传了文件 " + file.getOriginalFilename());
            return FileUtils.saveFile(file, address);
        }
        Map map = new TreeMap();
        map.put("state", "!admin");
        map.put("msg", "管理员禁止了匿名用户上传文件");
        return map;
    }


    @RequestMapping(value = "/clone", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> clone(@RequestBody Map map, HttpServletRequest request) {
        anonymous = Boolean.parseBoolean(Prop.getProperty("anonymous"));
        if (anonymous) {
            // 获取要克隆的网址
            String url = (String) map.get("url");
            return FileUtils.cloneFile(url, IPUtils.getIpAddress(request));
        }
        Map map2 = new TreeMap();
        map2.put("state", "!admin");
        map2.put("msg", "管理员禁止了匿名用户上传文件");

        return map2;

    }
}