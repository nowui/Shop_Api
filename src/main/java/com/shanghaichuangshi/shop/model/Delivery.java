package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnTypeEnum;

public class Delivery extends Model<Delivery> {

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "快递编号")
    public static final String DELIVERY_ID = "delivery_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "用户编号", findable = false)
    public static final String USER_ID = "user_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 10, comment = "收货人")
    public static final String DELIVERY_NAME = "delivery_name";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 20, comment = "手机号码")
    public static final String DELIVERY_PHONE = "delivery_phone";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "省份")
    public static final String DELIVERY_PROVINCE = "delivery_province";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "城市")
    public static final String DELIVERY_CITY = "delivery_city";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "区域")
    public static final String DELIVERY_AREA = "delivery_area";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 100, comment = "街道")
    public static final String DELIVERY_STREET = "delivery_street";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 250, comment = "详细地址")
    public static final String DELIVERY_ADDRESS = "delivery_address";

    @Column(type = ColumnTypeEnum.TINYINT, length = 1, comment = "默认地址")
    public static final String DELIVERY_IS_DEFAULT = "delivery_is_default";

    public String getDelivery_id() {
        return getStr(DELIVERY_ID);
    }

    public void setDelivery_id(String delivery_id) {
        set(DELIVERY_ID, delivery_id);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }

    public String getDelivery_name() {
        return getStr(DELIVERY_NAME);
    }

    public void setDelivery_name(String delivery_name) {
        set(DELIVERY_NAME, delivery_name);
    }

    public String getDelivery_phone() {
        return getStr(DELIVERY_PHONE);
    }

    public void setDelivery_phone(String delivery_phone) {
        set(DELIVERY_PHONE, delivery_phone);
    }

    public String getDelivery_province() {
        return getStr(DELIVERY_PROVINCE);
    }

    public void setDelivery_province(String delivery_province) {
        set(DELIVERY_PROVINCE, delivery_province);
    }

    public String getDelivery_city() {
        return getStr(DELIVERY_CITY);
    }

    public void setDelivery_city(String delivery_city) {
        set(DELIVERY_CITY, delivery_city);
    }

    public String getDelivery_area() {
        return getStr(DELIVERY_AREA);
    }

    public void setDelivery_area(String delivery_area) {
        set(DELIVERY_AREA, delivery_area);
    }

    public String getDelivery_street() {
        return getStr(DELIVERY_STREET);
    }

    public void setDelivery_street(String delivery_street) {
        set(DELIVERY_STREET, delivery_street);
    }

    public String getDelivery_address() {
        return getStr(DELIVERY_ADDRESS);
    }

    public void setDelivery_address(String delivery_address) {
        set(DELIVERY_ADDRESS, delivery_address);
    }

    public Boolean getDelivery_is_default() {
        return getBoolean(DELIVERY_IS_DEFAULT);
    }

    public void setDelivery_is_default(Boolean delivery_is_default) {
        set(DELIVERY_IS_DEFAULT, delivery_is_default);
    }

}