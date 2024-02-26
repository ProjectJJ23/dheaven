package com.jj.dheaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/product")
@Controller
public class ProductController {

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



    //주류별 상품 목록 페이지
    @GetMapping(value = "/beer/list")
    public String beerList(){
        return "product/category-beer";
    }

    @GetMapping(value = "/wine/list")
    public String wineList(){
        return "product/category-wine";
    }

    @GetMapping(value = "/soju/list")
    public String sojuList(){
        return "product/category-soju";
    }

    @GetMapping(value = "/mak/list")
    public String makList(){
        return "product/category-mak";
    }






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
