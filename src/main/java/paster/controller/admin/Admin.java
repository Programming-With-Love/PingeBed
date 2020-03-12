package paster.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Admin {

    @RequestMapping("/admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("/admin");
        return modelAndView;
    }
}
