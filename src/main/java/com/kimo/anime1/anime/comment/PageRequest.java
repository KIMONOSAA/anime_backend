package com.kimo.anime1.anime.comment;

import com.kimo.anime1.anime.contant.CommonConstant;
import lombok.Data;

/**
 * 分页请求
 *
 * @author  kimo
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private int current = 0;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_DESC;
}
