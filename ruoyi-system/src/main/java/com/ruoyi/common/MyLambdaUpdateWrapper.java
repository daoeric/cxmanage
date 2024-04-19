package com.ruoyi.common;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

public class MyLambdaUpdateWrapper<T> extends LambdaUpdateWrapper<T> {
    public MyLambdaUpdateWrapper(Class<T> entityClass) {
        super(entityClass);
    }

    /**
     * 指定列自增
     * @param columns           列引用
     * @param value             增长值
     */
    public MyLambdaUpdateWrapper<T> incrField(SFunction<T, ?> columns, Object value) {
        String columnsToString = super.columnToString(columns);

        String format = String.format("%s =  %s + %s", columnsToString,columnsToString, value);

        setSql(format);

        return this;
    }

    /**
     * 指定列自减
     * @param columns           列引用
     * @param value             减少值
     */
    public MyLambdaUpdateWrapper<T> descField(SFunction<T, ?> columns, Object value) {
        String columnsToString = super.columnToString(columns);

        String format = String.format("%s =  %s - %s", columnsToString,columnsToString, value);

        setSql(format);

        return this;
    }
}
