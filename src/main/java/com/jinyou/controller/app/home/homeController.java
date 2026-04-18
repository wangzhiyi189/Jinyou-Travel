package com.jinyou.controller.app.home;

import com.jinyou.pojo.Result;
import com.jinyou.pojo.admin.home.Banner;
import com.jinyou.pojo.admin.home.TopBanner;
import com.jinyou.service.app.home.HomeService;
import com.jinyou.vo.app.BannerVO;
import com.jinyou.vo.app.TopBannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
@Validated
public class homeController {
    @Autowired
    HomeService homeService;
    @GetMapping("banner")
    public Result<List<BannerVO>> banner(){
        List<Banner> bannerList = homeService.list();
        List<BannerVO> voList = bannerList.stream().map(banner -> {
            BannerVO vo = new BannerVO();
            vo.setBannerId(Long.valueOf(banner.getBannerId()));
            vo.setImgUrl(banner.getImageUrl());
            vo.setLinkType(banner.getLinkType());
            vo.setLinkUrl(banner.getLinkUrl());
            return vo;
        }).collect(Collectors.toList());
        return Result.success(voList);
    }

    @GetMapping("topBanner")
    public Result<List<TopBannerVO>> topBanner(){
        List<TopBanner> bannerList = homeService.listTop();
        List<TopBannerVO> voList = bannerList.stream().map(topBanner -> {
            TopBannerVO vo = new TopBannerVO();
            vo.setTopBannerId(Long.valueOf(topBanner.getTopBannerId()));
            vo.setImgUrl(topBanner.getImageUrl());
            return vo;
        }).collect(Collectors.toList());
        return Result.success(voList);
    }
}
