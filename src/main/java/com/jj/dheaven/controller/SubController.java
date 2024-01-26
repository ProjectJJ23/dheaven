package com.jj.dheaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubController {
    @GetMapping(value = "/list")
    public String List(){
        return "sub/shop-grid";
    }

    @GetMapping(value = "/detail")
    public String Detail(){
        return "sub/shop-details";
    }

    @GetMapping(value = "/cart")
    public String Cart(){
        return "sub/shoping-cart";
    }


}
