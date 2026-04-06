package com.jinyou.controller;

import com.jinyou.pojo.Banner;
import com.jinyou.pojo.Result;
import com.jinyou.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @GetMapping
    public Result<List<Banner>> list(){
        List<Banner> cs = bannerService.list();
        return Result.success(cs);
    }
}
