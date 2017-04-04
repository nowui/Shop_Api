package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Landwind extends Model<Landwind> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "经销商编号")
    public static final String LANDWIND_ID = "landwind_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "")
    public static final String LANDWIND_NAME = "landwind_name";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "")
    public static final String LANDWIND_PHONE = "landwind_phone";

    @Column(type = ColumnType.VARCHAR, length = 1, comment = "")
    public static final String LANDWIND_SEX = "landwind_sex";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "")
    public static final String LANDWIND_CAR = "landwind_car";

    
    public String getLandwind_id() {
        return getStr(LANDWIND_ID);
    }

    public void setLandwind_id(String landwind_id) {
        set(LANDWIND_ID, landwind_id);
    }

    public String getLandwind_name() {
        return getStr(LANDWIND_NAME);
    }

    public void setLandwind_name(String landwind_name) {
        set(LANDWIND_NAME, landwind_name);
    }

    public String getLandwind_phone() {
        return getStr(LANDWIND_PHONE);
    }

    public void setLandwind_phone(String landwind_phone) {
        set(LANDWIND_PHONE, landwind_phone);
    }

    public String getLandwind_sex() {
        return getStr(LANDWIND_SEX);
    }

    public void setLandwind_sex(String landwind_sex) {
        set(LANDWIND_SEX, landwind_sex);
    }

    public String getLandwind_car() {
        return getStr(LANDWIND_CAR);
    }

    public void setLandwind_car(String landwind_car) {
        set(LANDWIND_CAR, landwind_car);
    }
}