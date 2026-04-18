package com.jinyou.controller.admin.home;

import com.jinyou.DTO.admin.home.BannerQueryDTO;
import com.jinyou.pojo.admin.home.Banner;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.service.admin.home.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @GetMapping
    public Result<PageBean<Banner>> list(BannerQueryDTO query){
        PageBean<Banner> pb = bannerService.list(query);
        return Result.success(pb);
    }
    @PostMapping("add")
    public Result<String> add(@RequestBody @Validated Banner banner){
        bannerService.add(banner);
        return Result.success("添加成功");
    }
    @PutMapping("update")
    public Result<String> update(@RequestBody @Validated Banner banner){
        bannerService.update(banner);
        return Result.success("修改成功");
    }

    @DeleteMapping("delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        bannerService.delete(id);
        return Result.success("删除成功");
    }

    @PutMapping("update/status")
    public Result<String> updateStatus(@RequestBody Map<String,Integer> params){
        Long bannerId = Long.valueOf(params.get("bannerId"));
        Integer status = params.get("status");
        bannerService.updateStatus(bannerId,status);
        return Result.success("修改成功");
    }
}
