package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Guangfeng extends Model<Guangfeng> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String GUANGFENG_ID = "guangfeng_id";

    @Column(type = ColumnType.INT, length = 2, comment = "")
    public static final String GUANGFENG_NUMBER = "guangfeng_number";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "")
    public static final String GUANGFENG_NAME = "guangfeng_name";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "")
    public static final String GUANGFENG_PHONE = "guangfeng_phone";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "")
    public static final String GUANGFENG_SCHOOL = "guangfeng_school";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "")
    public static final String GUANGFENG_MAJOR = "guangfeng_major";

    
    public String getGuangfeng_id() {
        return getStr(GUANGFENG_ID);
    }

    public void setGuangfeng_id(String guangfeng_id) {
        set(GUANGFENG_ID, guangfeng_id);
    }

    public Integer getGuangfeng_number() {
        return getInt(GUANGFENG_NUMBER);
    }

    public void setGuangfeng_number(Integer guangfeng_number) {
        set(GUANGFENG_NUMBER, guangfeng_number);
    }

    public String getGuangfeng_name() {
        return getStr(GUANGFENG_NAME);
    }

    public void setGuangfeng_name(String guangfeng_name) {
        set(GUANGFENG_NAME, guangfeng_name);
    }

    public String getGuangfeng_phone() {
        return getStr(GUANGFENG_PHONE);
    }

    public void setGuangfeng_phone(String guangfeng_phone) {
        set(GUANGFENG_PHONE, guangfeng_phone);
    }

    public String getGuangfeng_school() {
        return getStr(GUANGFENG_SCHOOL);
    }

    public void setGuangfeng_school(String guangfeng_school) {
        set(GUANGFENG_SCHOOL, guangfeng_school);
    }

    public String getGuangfeng_major() {
        return getStr(GUANGFENG_MAJOR);
    }

    public void setGuangfeng_major(String guangfeng_major) {
        set(GUANGFENG_MAJOR, guangfeng_major);
    }
}