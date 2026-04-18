package com.jinyou.controller.admin.home;

import com.jinyou.DTO.admin.home.TopBannerQueryDTO;
import com.jinyou.pojo.PageBean;
import com.jinyou.pojo.Result;
import com.jinyou.pojo.admin.home.TopBanner;
import com.jinyou.service.admin.home.TopBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/topBanner")
@Validated
public class TopBannerController {
    @Autowired
    TopBannerService topBannerService;
    @GetMapping
    public Result <PageBean<TopBanner>> list(TopBannerQueryDTO query){
        PageBean<TopBanner> pb = topBannerService.list(query);
        return Result.success(pb);
    }
    @PostMapping("add")
    public Result<String> add(@RequestBody @Validated TopBanner topBanner){
        topBannerService.add(topBanner);
        return Result.success("添加成功");
    }
    @PutMapping("update")
    public Result<String> update(@RequestBody @Validated TopBanner topBanner){
        topBannerService.update(topBanner);
        return Result.success("修改成功");
    }
    @DeleteMapping("delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        topBannerService.delete(id);
        return Result.success("删除成功");
    }
    @PutMapping("update/status")
    public Result<String> updateStatus(@RequestBody Map<String,Object> params){
        Long topBannerId = ((Number) params.get("topBannerId")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        topBannerService.updateStatus(topBannerId,status);
        return Result.success("修改成功");
    }
}
