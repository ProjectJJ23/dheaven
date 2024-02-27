package com.jj.dheaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping("/item")
@Controller
public class ProductController {

    @GetMapping(value = "/item/list")
    public String List(){
        return "item/shop-grid";
    }

    @GetMapping(value = "/item/detail")
    public String Detail(){
        return "item/shop-details";
    }

    @GetMapping(value = "/item/cart")
    public String Cart(){
        return "item/shoping-cart";
    }



    //주류별 상품 목록 페이지
    @GetMapping(value = "/item/beer/list")
    public String beerList(){
        return "item/beer/category-beer";
    }

    @GetMapping(value = "/item/wine/list")
    public String wineList(){
        return "item/wine/category-wine";
    }

    @GetMapping(value = "/item/soju/list")
    public String sojuList(){
        return "item/soju/category-soju";
    }

    @GetMapping(value = "/item/mak/list")
    public String makList(){
        return "item/mak/category-mak";
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
