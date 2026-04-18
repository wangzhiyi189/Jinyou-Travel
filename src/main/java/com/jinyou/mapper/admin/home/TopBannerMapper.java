package com.jinyou.mapper.admin.home;

import com.jinyou.DTO.admin.home.TopBannerQueryDTO;
import com.jinyou.pojo.admin.home.TopBanner;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TopBannerMapper {
    List<TopBanner> list(@Param("userId") Long userId, @Param("query") TopBannerQueryDTO query);

    // 添加
//    @Insert("insert into top_banner(title,image_url,status,create_time,update_time,create_user,remark) " +
//            "values(#{title},#{imageUrl},#{status},#{createTime},#{updateTime},#{createUser},#{remark})")
    @Insert("insert into top_banner(" +
            "title,image_url,sort,status,create_time,update_time,remark,create_user)" +
            " values(" +
            "#{title},#{imageUrl},#{sort},#{status},#{createTime},#{updateTime},#{remark},#{createUser})")
    void add(TopBanner topBanner);

    // 修改
    @Update("update top_banner set title=#{title},image_url=#{imageUrl},sort=#{sort},status=#{status},update_time=#{updateTime},remark=#{remark},create_user=#{createUser} where top_banner_id=#{topBannerId}")
    void update(TopBanner topBanner);

    // 删除
    @Update("delete from top_banner where top_banner_id=#{id}")
    void delete(Long id);

    @Update("update top_banner set status=#{status},update_time=now() where top_banner_id=#{topBannerId}")
    void updateStatus(Long topBannerId, Integer status);






}
