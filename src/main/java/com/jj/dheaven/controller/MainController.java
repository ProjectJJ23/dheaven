package com.jj.dheaven.controller;

import com.jj.dheaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/test")
    public String test(Model model){

     //   List<User> userlist = userService.memlist();
     //   model.addAttribute("userlist",userlist);
        System.out.println("메인컨트롤러 - db test");

        return "main/test";
    }



    @GetMapping(value = "/")
    public String index(){
        return "main/index";
    }

    @GetMapping(value = "/error")
    public String error(){
        return "main/404";
    }

    @GetMapping(value = "/contact2")
    public String contact(){
        return "main/contact";
    }


    @GetMapping(value = "/2")
    public String Test3(){
        return "main/about";
    }

    @GetMapping(value = "/3")
    public String Test5(){
        return "main/product";
    }

    @GetMapping(value = "/4")
    public String Test6(){
        return "main/service";
    }

    @GetMapping(value = "/5")
    public String Test7(){
        return "main/team";
    }

    @GetMapping(value = "/6")
    public String Test8(){
        return "main/testimonial";
    }

    @GetMapping(value = "/a")
    public String Testa(){
        return "sub/index";
    }




}
