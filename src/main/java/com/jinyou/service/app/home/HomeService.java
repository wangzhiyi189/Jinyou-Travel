package com.jinyou.service.app.home;

import com.jinyou.pojo.admin.home.Banner;
import com.jinyou.pojo.admin.home.TopBanner;

import java.util.List;

public interface HomeService {

    List<Banner> list();

    List<TopBanner> listTop();
}
