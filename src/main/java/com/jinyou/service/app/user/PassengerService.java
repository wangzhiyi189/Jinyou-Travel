package com.jinyou.service.app.user;

import com.jinyou.pojo.app.user.Passenger;

import java.util.List;

public interface PassengerService {
    List<Passenger> list();

    void add(Passenger passenger);

    void delete(Long id);
    
    Passenger details(Long id);

    void update(Passenger passenger);

    List<Passenger> getDetails(List<Long> passengerIdList);
}
