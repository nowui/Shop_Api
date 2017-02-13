package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.annotation.Id;
import com.shanghaichuangshi.annotation.Table;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Delivery extends Model<Delivery> {

    @Table()
    public static final String TABLE_DELIVERY = "table_delivery";

    @Id
    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String DELIVERY_ID = "delivery_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "")
    public static final String USER_ID = "user_id";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "")
    public static final String DELIVERY_NAME = "delivery_name";

    @Column(type = ColumnType.VARCHAR, length = 11, comment = "")
    public static final String DELIVERY_PHONE = "delivery_phone";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "")
    public static final String DELIVERY_PROVINCE = "delivery_province";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "")
    public static final String DELIVERY_CITY = "delivery_city";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "")
    public static final String DELIVERY_AREA = "delivery_area";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "")
    public static final String DELIVERY_STREET = "delivery_street";

    @Column(type = ColumnType.VARCHAR, length = 6, comment = "")
    public static final String DELIVERY_ZIP = "delivery_zip";

    public String getDelivery_id() {
        return getString(DELIVERY_ID);
    }

    public void setDelivery_id(String delivery_id) {
        set(DELIVERY_ID, delivery_id);
    }

    public String getUser_id() {
        return getString(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }

    public String getDelivery_name() {
        return getString(DELIVERY_NAME);
    }

    public void setDelivery_name(String delivery_name) {
        set(DELIVERY_NAME, delivery_name);
    }

    public String getDelivery_phone() {
        return getString(DELIVERY_PHONE);
    }

    public void setDelivery_phone(String delivery_phone) {
        set(DELIVERY_PHONE, delivery_phone);
    }

    public String getDelivery_province() {
        return getString(DELIVERY_PROVINCE);
    }

    public void setDelivery_province(String delivery_province) {
        set(DELIVERY_PROVINCE, delivery_province);
    }

    public String getDelivery_city() {
        return getString(DELIVERY_CITY);
    }

    public void setDelivery_city(String delivery_city) {
        set(DELIVERY_CITY, delivery_city);
    }

    public String getDelivery_area() {
        return getString(DELIVERY_AREA);
    }

    public void setDelivery_area(String delivery_area) {
        set(DELIVERY_AREA, delivery_area);
    }

    public String getDelivery_street() {
        return getString(DELIVERY_STREET);
    }

    public void setDelivery_street(String delivery_street) {
        set(DELIVERY_STREET, delivery_street);
    }

    public String getDelivery_zip() {
        return getString(DELIVERY_ZIP);
    }

    public void setDelivery_zip(String delivery_zip) {
        set(DELIVERY_ZIP, delivery_zip);
    }

}