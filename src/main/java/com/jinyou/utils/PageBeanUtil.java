package com.jinyou.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jinyou.pojo.PageBean;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 通用分页工具类
 */
public class PageBeanUtil {

    /**
     * 通用分页封装
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @param query    数据查询逻辑（Supplier 函数式接口，传入查询方法）
     * @return 封装好的 PageBean
     */
    public static <T> PageBean<T> toPageBean(int pageNum, int pageSize, Supplier<List<T>> query) {
        // 1. 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 2. 执行查询
        List<T> list = query.get();
        // 3. 强转为 Page 对象，获取分页信息
        Page<T> page = (Page<T>) list;
        // 4. 封装 PageBean 并返回
        PageBean<T> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }
    public static <T> PageBean<T> toPageBeanWithUser(int pageNum, int pageSize, Function<Long, List<T>> query) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        return toPageBean(pageNum, pageSize, () -> query.apply(userId));
    }
}