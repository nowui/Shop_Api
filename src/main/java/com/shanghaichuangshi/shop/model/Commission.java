package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnTypeEnum;

public class Commission extends Model<Commission> {

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "佣金编号")
    public static final String COMMISSION_ID = "commission_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "商品编号")
    public static final String PRODUCT_ID = "product_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 1000, comment = "商品属性")
    public static final String PRODUCT_ATTRIBUTE = "product_attribute";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 1000, comment = "商品佣金")
    public static final String PRODUCT_COMMISSION = "product_commission";

    public static final String COMMISSION_LIST = "commission_list";
    public static final String COMMISSION_AMOUNT = "commission_amount";

    
    public String getCommission_id() {
        return getStr(COMMISSION_ID);
    }

    public void setCommission_id(String commission_id) {
        set(COMMISSION_ID, commission_id);
    }

    public String getProduct_id() {
        return getStr(PRODUCT_ID);
    }

    public void setProduct_id(String product_id) {
        set(PRODUCT_ID, product_id);
    }

    public String getProduct_attribute() {
        return getStr(PRODUCT_ATTRIBUTE);
    }

    public void setProduct_attribute(String product_attribute) {
        set(PRODUCT_ATTRIBUTE, product_attribute);
    }

    public String getProduct_commission() {
        return getStr(PRODUCT_COMMISSION);
    }

    public void setProduct_commission(String product_commission) {
        set(PRODUCT_COMMISSION, product_commission);
    }
}