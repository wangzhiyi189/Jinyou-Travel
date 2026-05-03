package com.jinyou.service.app.user.impl;

import com.jinyou.mapper.app.user.PassengerMapper;
import com.jinyou.pojo.app.user.Passenger;
import com.jinyou.service.app.user.PassengerService;
import com.jinyou.utils.EntityFillUtil;
import com.jinyou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    PassengerMapper passengerMapper;

    @Override
    public List<Passenger> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        return passengerMapper.list(userId);
    }

    @Override
    public void add(Passenger passenger) {
        EntityFillUtil.fillCreateFields(passenger);
        passengerMapper.add(passenger);
    }

    @Override
    public void delete(Long id) {
        passengerMapper.delete(id);
    }

    @Override
    public  Passenger details(Long id) {
        return passengerMapper.details(id);
    }

    @Override
    public void update(Passenger passenger) {
        EntityFillUtil.fillUpdateFields(passenger);
        System.out.println(passenger);
        passengerMapper.update(passenger);
    }

    @Override
    public List<Passenger> getDetails(List<Long> passengerIdList) {
        return passengerMapper.getDetails(passengerIdList);
    }
}
