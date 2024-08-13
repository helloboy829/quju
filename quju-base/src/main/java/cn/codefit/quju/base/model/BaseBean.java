package cn.codefit.quju.base.model;


import cn.codefit.quju.base.util.GuavaUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class BaseBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    private Integer pageNum = 1;
    /**
     * 每页记录数
     */
    private Integer pageSize = 20;
    /**
     * 排序字段(驼峰字段)
     */
    private String fieldSort;
    /**
     * 排序类型
     * 正序 asc
     * 倒序 desc
     */
    private String orderSort;


    public String getFieldSort() {
        if (StringUtils.isNotBlank(fieldSort)) {
            return GuavaUtils.transformToDatabaseFieldLower(fieldSort);
        }
        return fieldSort;
    }

    public void setFieldSort(String fieldSort) {
        this.fieldSort = fieldSort;
    }

    public String getOrderSort() {
        if (StringUtils.isNotBlank(orderSort)) {
            return orderSort.split("end")[0];
        }
        return orderSort;
    }


    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
