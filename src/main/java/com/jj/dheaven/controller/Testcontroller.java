package com.jj.dheaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


public class Testcontroller {

    @GetMapping(value = "/")
    public String Test(){
        return "main/index";
    }

    @GetMapping(value = "/list")
    public String List(){
        return "sub/shop-grid";
    }

    @GetMapping(value = "/detail")
    public String Detail(){
        return "sub/shop-details";
    }



    @GetMapping(value = "/1")
    public String Test2(){
        return "main/404";
    }

    @GetMapping(value = "/2")
    public String Test3(){
        return "main/about";
    }

    @GetMapping(value = "/3")
    public String Test4(){
        return "main/contact";
    }

    @GetMapping(value = "/4")
    public String Test5(){
        return "main/product";
    }

    @GetMapping(value = "/5")
    public String Test6(){
        return "main/service";
    }

    @GetMapping(value = "/6")
    public String Test7(){
        return "main/team";
    }

    @GetMapping(value = "/7")
    public String Test8(){
        return "main/testimonial";
    }

    @GetMapping(value = "/a")
    public String Testa(){
        return "sub/index";
    }
}
