package com.jj.dheaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    //상품리스트
    @GetMapping(value = "/list2")
    public String productList(){
        return "product/list";
    }


    //등록폼
    @GetMapping(value = "/write2")
    public String productWrite(){
        return "product/write";
    }


    @GetMapping(value = "/detail2")
    public String productDetail(){
        return "product/detail";
    }





}
