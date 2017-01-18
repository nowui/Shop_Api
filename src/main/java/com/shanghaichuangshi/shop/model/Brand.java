package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.annotation.Id;
import com.shanghaichuangshi.annotation.Table;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Brand extends Model<Brand> {

    @Table()
    public static final String TABLE_BRAND = "table_brand";

    @Id
    @Column(type = ColumnType.VARCHAR, length = 32, comment = "品牌编号")
    public static final String BRAND_ID = "brand_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "分类编号")
    public static final String CATEGORY_ID = "category_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "品牌名称")
    public static final String BRAND_NAME = "brand_name";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "品牌图片")
    public static final String BRAND_IMAGE = "brand_image";

    @Column(type = ColumnType.LONGTEXT, length = 0, comment = "品牌协议书")
    public static final String BRAND_CONTENT = "brand_content";

    public String getBrand_id() {
        return getString(BRAND_ID);
    }

    public void setBrand_id(String brand_id) {
        set(BRAND_ID, brand_id);
    }

    public String getCategory_id() {
        return getString(CATEGORY_ID);
    }

    public void setCategory_id(String category_id) {
        set(CATEGORY_ID, category_id);
    }

    public String getBrand_name() {
        return getString(BRAND_NAME);
    }

    public void setBrand_name(String brand_name) {
        set(BRAND_NAME, brand_name);
    }

    public String getBrand_image() {
        return getString(BRAND_IMAGE);
    }

    public void setBrand_image(String brand_image) {
        set(BRAND_IMAGE, brand_image);
    }

    public String getBrand_content() {
        return getString(BRAND_CONTENT);
    }

    public void setBrand_content(String brand_content) {
        set(BRAND_CONTENT, brand_content);
    }

}