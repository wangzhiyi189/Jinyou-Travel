package com.jinyou.mapper.app.user;

import com.jinyou.pojo.app.user.Passenger;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PassengerMapper {
    @Select("select * from wx_passenger where create_user = #{userId}")
    List<Passenger> list(Long userId);

    @Insert("insert into wx_passenger(name,phone,id_card,ticket_type,id_type,create_time,update_time,create_user)" +
            " values(#{name},#{phone},#{idCard},#{ticketType},#{idType},now(),now(),#{createUser})")
    void add(Passenger passenger);

    @Insert("delete from wx_passenger where passenger_id = #{id}")
    void delete(Long id);

    @Select("select * from wx_passenger where passenger_id = #{id}")
    Passenger details(Long id);

    @Insert("update wx_passenger set name = #{name},phone = #{phone},id_card = #{idCard},ticket_type = #{ticketType},id_type = #{idType},update_time = now(),create_user=#{createUser}" +
            " where passenger_id = #{passengerId}")
    void update(Passenger passenger);

    List<Passenger> getDetails(List<Long> passengerIdList);
}
