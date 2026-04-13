package com.jinyou.mapper;

import com.jinyou.pojo.Banner;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BannerMapper {

    // 查询banner；列表

    List<Banner> list(Integer userId, String title, Integer status);

    // 添加banner
    @Insert("insert into banner(" +
            "title,image_url,link_type,link_url,sort,status,create_time,update_time,remark,create_user)" +
            " values(" +
            "#{title},#{imageUrl},#{linkType},#{linkUrl},#{sort},#{status},#{createTime},#{updateTime},#{remark},#{createUser})")
    void add(Banner banner);
    // 修改banner
    @Update("update banner set title=#{title},image_url=#{imageUrl},link_type=#{linkType},link_url=#{linkUrl},sort=#{sort},status=#{status},update_time=#{updateTime},remark=#{remark},create_user=#{createUser} where banner_id=#{bannerId}")
    void update(Banner banner);

    // 删除banner
    @Update("delete from banner where banner_id=#{id}")
    void delete(Integer id);
    // 修改banner状态
    @Update("update banner set status=#{status},update_time=now() where banner_id=#{bannerId}")
    void updateStatus(Integer bannerId, Integer status);
}
