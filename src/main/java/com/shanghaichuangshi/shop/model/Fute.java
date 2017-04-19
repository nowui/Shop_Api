package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Fute extends Model<Fute> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "福特编号")
    public static final String FUTE_ID = "fute_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "福特名称")
    public static final String FUTE_NAME = "fute_name";

    @Column(type = ColumnType.VARCHAR, length = 2, comment = "福特性别")
    public static final String FUTE_SEX = "fute_sex";

    @Column(type = ColumnType.VARCHAR, length = 15, comment = "福特电话")
    public static final String FUTE_PHONE = "fute_phone";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "福特省份")
    public static final String FUTE_PROVINCE = "fute_province";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "福特城市")
    public static final String FUTE_CITY = "fute_city";

    @Column(type = ColumnType.VARCHAR, length = 30, comment = "福特经销商")
    public static final String FUTE_DISTRIBUTOR = "fute_distributor";

    
    public String getFute_id() {
        return getStr(FUTE_ID);
    }

    public void setFute_id(String fute_id) {
        set(FUTE_ID, fute_id);
    }

    public String getFute_name() {
        return getStr(FUTE_NAME);
    }

    public void setFute_name(String fute_name) {
        set(FUTE_NAME, fute_name);
    }

    public String getFute_sex() {
        return getStr(FUTE_SEX);
    }

    public void setFute_sex(String fute_sex) {
        set(FUTE_SEX, fute_sex);
    }

    public String getFute_phone() {
        return getStr(FUTE_PHONE);
    }

    public void setFute_phone(String fute_phone) {
        set(FUTE_PHONE, fute_phone);
    }

    public String getFute_province() {
        return getStr(FUTE_PROVINCE);
    }

    public void setFute_province(String fute_province) {
        set(FUTE_PROVINCE, fute_province);
    }

    public String getFute_city() {
        return getStr(FUTE_CITY);
    }

    public void setFute_city(String fute_city) {
        set(FUTE_CITY, fute_city);
    }

    public String getFute_distributor() {
        return getStr(FUTE_DISTRIBUTOR);
    }

    public void setFute_distributor(String fute_distributor) {
        set(FUTE_DISTRIBUTOR, fute_distributor);
    }
}