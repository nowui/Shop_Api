package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class ProductFile extends Model<ProductFile> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "商品文件编号")
    public static final String PRODUCT_FILE_ID = "product_file_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "商品编号")
    public static final String PRODUCT_ID = "product_id";

    @Column(type = ColumnType.VARCHAR, length = 10, comment = "商品文件类型")
    public static final String PRODUCT_FILE_TYPE = "product_file_type";

    @Column(type = ColumnType.VARCHAR, length = 50, comment = "商品文件名称")
    public static final String PRODUCT_FILE_NAME = "product_file_name";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "商品文件路径")
    public static final String PRODUCT_FILE_PATH = "product_file_path";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "商品文件路径")
    public static final String PRODUCT_FILE_THUMBNAIL_PATH = "product_file_thumbnail_path";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "商品文件路径")
    public static final String PRODUCT_FILE_ORIGINAL_PATH = "product_file_original_path";

    @Column(type = ColumnType.VARCHAR, length = 250, comment = "商品文件备注")
    public static final String PRODUCT_FILE_REMARK = "product_file_remark";

    
    public String getProduct_file_id() {
        return getStr(PRODUCT_FILE_ID);
    }

    public void setProduct_file_id(String product_file_id) {
        set(PRODUCT_FILE_ID, product_file_id);
    }

    public String getProduct_id() {
        return getStr(PRODUCT_ID);
    }

    public void setProduct_id(String product_id) {
        set(PRODUCT_ID, product_id);
    }

    public String getProduct_file_type() {
        return getStr(PRODUCT_FILE_TYPE);
    }

    public void setProduct_file_type(String product_file_type) {
        set(PRODUCT_FILE_TYPE, product_file_type);
    }

    public String getProduct_file_name() {
        return getStr(PRODUCT_FILE_NAME);
    }

    public void setProduct_file_name(String product_file_name) {
        set(PRODUCT_FILE_NAME, product_file_name);
    }

    public String getProduct_file_path() {
        return getStr(PRODUCT_FILE_PATH);
    }

    public void setProduct_file_path(String product_file_path) {
        set(PRODUCT_FILE_PATH, product_file_path);
    }

    public String getProduct_file_thumbnail_path() {
        return getStr(PRODUCT_FILE_THUMBNAIL_PATH);
    }

    public void setProduct_file_thumbnail_path(String product_file_thumbnail_path) {
        set(PRODUCT_FILE_THUMBNAIL_PATH, product_file_thumbnail_path);
    }

    public String getProduct_file_original_path() {
        return getStr(PRODUCT_FILE_ORIGINAL_PATH);
    }

    public void setProduct_file_original_path(String product_file_original_path) {
        set(PRODUCT_FILE_ORIGINAL_PATH, product_file_original_path);
    }

    public String getProduct_file_remark() {
        return getStr(PRODUCT_FILE_REMARK);
    }

    public void setProduct_file_remark(String product_file_remark) {
        set(PRODUCT_FILE_REMARK, product_file_remark);
    }
}