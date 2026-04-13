package com.jinyou.controller;

import com.jinyou.pojo.Banner;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @GetMapping
    public Result<PageBean<Banner>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer status
    ){
        PageBean<Banner> pb = bannerService.list(pageNum,pageSize,title,status);
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
    public Result<String> delete(@PathVariable Integer id){
        bannerService.delete(id);
        return Result.success("删除成功");
    }

    @PutMapping("update/status")
    public Result<String> updateStatus(@RequestBody Map<String,Integer> params){
        Integer bannerId = params.get("bannerId");
        Integer status = params.get("status");
        bannerService.updateStatus(bannerId,status);
        return Result.success("修改成功");
    }
}
