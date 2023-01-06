package paster.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import paster.bean.History;
import paster.prop.Prop;
import paster.utlis.HistoryTools;
import paster.utlis.IPUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String size;

    @RequestMapping(value = "/")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("uploadSize", size);
        modelAndView.addObject("uploadImageCount", Prop.getProperty( "imageUploadCount"));
        modelAndView.addObject("freeSize", Prop.getProperty("size"));
        return modelAndView;
    }


    @RequestMapping(value = "/history")
    public ModelAndView history(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/history");
        String ip = IPUtils.getIpAddress(request);
        modelAndView.addObject("ip", ip);
        return modelAndView;
    }
}
