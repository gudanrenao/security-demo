package com.zhangwenit.security.demo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description mybatis 执行 update/insert时自动填充时间/人员/id等信息
 * @Author ZWen
 * @Date 2019/5/16 9:46 AM
 * @Version 1.0
 **/
@Component
public class BaseFieldAutoFillHandler implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createTime", new Date(), metaObject);
        this.setInsertFieldValByName("modifyTime", new Date(), metaObject);
        this.setInsertFieldValByName("isDelete", 0, metaObject);
        this.setInsertFieldValByName("creatorId", -1, metaObject);
        this.setInsertFieldValByName("modifyUserId", -1, metaObject);


    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("modifyTime", new Date(), metaObject);
        this.setInsertFieldValByName("modifyUserId", "-1", metaObject);
    }
}