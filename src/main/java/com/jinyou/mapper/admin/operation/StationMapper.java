package com.jinyou.mapper.admin.operation;

import com.jinyou.DTO.admin.operation.StationQueryDTO;
import com.jinyou.pojo.admin.operation.Station;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StationMapper {
    // 获取列表
    List<Station> list(@Param("userId") Long userId, @Param("query") StationQueryDTO query);
    // 添加站点
    @Insert("insert into station(station_name,city,address,lng,lat,sort,status,create_time,update_time,remark,create_user)" +
            " values(" +
            " #{stationName},#{city},#{address},#{lng},#{lat},#{sort},#{status},#{createTime},#{updateTime},#{remark},#{createUser})")
    void add(Station station);

    // 修改站点
    @Insert("update station set station_name=#{stationName},city=#{city},address=#{address},lng=#{lng},lat=#{lat},sort=#{sort},status=#{status},update_time=#{updateTime},remark=#{remark},create_user=#{createUser} where station_id=#{stationId}")
    void update(Station station);

    // 删除站点
    @Insert("delete from station where station_id= #{id}")
    void delete(Long id);

    // 修改站点状态
    @Insert("update station set status= #{status},update_time=now() where station_id= #{stationId}")
    void updateStatus(Long stationId, Integer status);

//    获取所有站点数据
    List<Station> listAll(Long userId);
// 根据数组获取站点数据

    List<Station> listByStationIds(Integer[] stationIds);
}
