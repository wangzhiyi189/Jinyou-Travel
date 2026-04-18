package com.jinyou.mapper.admin.operation;

import com.jinyou.DTO.admin.operation.LineQueryDTO;
import com.jinyou.pojo.admin.operation.Line;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LineMapper {
    // 获取线路列表
    List<Line> list(@Param("userId") Long userId, @Param("query") LineQueryDTO query);
    // 新建线路
    @Insert("insert into line(" +
            "line_name, " +
            "station_list, " +
            "start_city, " +
            "end_city, " +
            "status, " +
            "create_time, " +
            "remark, " +
            "create_user" +
            ") values (" +
            "#{lineName}, " +
            "#{stationList}, " +
            "#{startCity}, " +
            "#{endCity}, " +
            "#{status}, " +
            "#{createTime}, " +
            "#{remark}, " +
            "#{createUser}" +
            ")")
    void add(Line line);

//    修改
    @Insert("update line set " + "line_name = #{lineName}, station_list = #{stationList} , start_city = #{startCity}, end_city = #{endCity}, status = #{status}, remark = #{remark}, create_user = #{createUser}  where line_id = #{lineId}")
    void update(Line line);
// 删除
    @Insert("delete from line where line_id = #{id}")
    void delete(Long id);

    @Insert("update line set status = #{status} where line_id = #{lineId}")
    void updateStatus(Long lineId, Integer status);

    List<Line> listAll(Long userId);

    // 根据ID查询线路
    Line selectById(@Param("lineId") Long lineId);
}
