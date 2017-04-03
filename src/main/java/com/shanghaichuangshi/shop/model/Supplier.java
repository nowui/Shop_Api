package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Supplier extends Model<Supplier> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "供应商编号")
    public static final String SUPPLIER_ID = "supplier_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "品牌编号")
    public static final String BRAND_ID = "brand_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "供应商名称")
    public static final String SUPPLIER_NAME = "supplier_name";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "用户编号")
    public static final String USER_ID = "user_id";

    
    public String getSupplier_id() {
        return getStr(SUPPLIER_ID);
    }

    public void setSupplier_id(String supplier_id) {
        set(SUPPLIER_ID, supplier_id);
    }

    public String getBrand_id() {
        return getStr(BRAND_ID);
    }

    public void setBrand_id(String brand_id) {
        set(BRAND_ID, brand_id);
    }

    public String getSupplier_name() {
        return getStr(SUPPLIER_NAME);
    }

    public void setSupplier_name(String supplier_name) {
        set(SUPPLIER_NAME, supplier_name);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }
}