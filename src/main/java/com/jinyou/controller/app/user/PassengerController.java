package com.jinyou.controller.app.user;
// 乘客
import com.jinyou.pojo.Result;
import com.jinyou.pojo.app.user.Passenger;
import com.jinyou.service.app.user.PassengerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
@Validated
public class PassengerController {
    @Autowired
    PassengerService passengerService;
    @GetMapping("list")
    public Result<List<Passenger>> list(){
        List<Passenger> list = passengerService.list();
        for (Passenger p : list) {
            String phone = p.getPhone();
            if (phone != null && phone.length() == 11) {
                p.setPhone(phone.substring(0, 3) + "****" + phone.substring(7));
            }
        }
        return Result.success(list);
    }

    @PostMapping("add")
    public Result<String> add(@RequestBody @Validated Passenger passenger){
        passengerService.add(passenger);
        return Result.success("添加成功");
    }

    @DeleteMapping("delete/{id}")
    public Result<String> delete(@PathVariable Long id){
        passengerService.delete(id);
        return Result.success("删除成功");
    }

    @GetMapping("details")
    public Result<Passenger> details(@Param("id") Long id){
        Passenger passenger = passengerService.details(id);
        return Result.success(passenger);
    }

    @PutMapping("update")
    public Result<String> update(@RequestBody @Validated Passenger passenger){
        passengerService.update(passenger);
        return Result.success("修改成功");
    }
}
