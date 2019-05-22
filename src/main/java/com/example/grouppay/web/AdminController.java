package com.example.grouppay.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author gm
 * @Date 2019/5/22 10:51
 * @Version 1.0
 **/
@Controller
public class AdminController {

    @RequestMapping(value="/index")
    public String index(){
        return "index";
    }

}
