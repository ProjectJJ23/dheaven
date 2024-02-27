package com.jj.dheaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AaaaaController {




    @GetMapping(value = "/main")
    public String whitemain(){
        return "aaa/main";
    }

    @GetMapping(value = "/index")
    public String index(){
        return "aaa/index";
    }


    @GetMapping(value = "/orderForm")
    public String orderForm(){
        return "aaa/orderForm";
    }

    @GetMapping(value = "/item-details")
    public String itemDetails(){
        return "aaa/item-details";
    }

    @GetMapping(value = "/contact")
    public String customerCenter(){
        return "aaa/contact";
    }

    @GetMapping(value = "/item-list")
    public String itemList(){
        return "aaa/item-list";
    }

    @GetMapping(value = "/cart")
    public String cart(){
        return "aaa/shoping-cart";
    }



/*  @GetMapping(value = "/blog")
    public String blog(){
        return "aaa/blog";
    }*/


}
