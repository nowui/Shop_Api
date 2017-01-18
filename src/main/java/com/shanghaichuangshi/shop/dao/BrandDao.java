package com.shanghaichuangshi.shop.dao;

import com.shanghaichuangshi.config.DynamicSQL;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Brand;
import com.shanghaichuangshi.util.DatabaseUtil;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class BrandDao extends Dao {

    public int count() {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT COUNT(*) FROM ").append(Brand.TABLE_BRAND).append(" ");
        dynamicSQL.append("WHERE ").append(Brand.TABLE_BRAND).append(".").append(Brand.SYSTEM_STATUS).append(" = ? ", true);

        return DatabaseUtil.count(dynamicSQL.getSql(), dynamicSQL.getParameterList());
    }

    public List<Brand> list(String brand_name, Integer m, Integer n) {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT ");
        dynamicSQL.append(Brand.TABLE_BRAND).append(".").append(Brand.BRAND_ID).append(", ");
        dynamicSQL.append(Brand.TABLE_BRAND).append(".").append(Brand.BRAND_NAME).append(" ");
        dynamicSQL.append("FROM ").append(Brand.TABLE_BRAND).append(" ");
        dynamicSQL.append("WHERE ").append(Brand.TABLE_BRAND).append(".").append(Brand.SYSTEM_STATUS).append(" = ? ", true);
        if (!Util.isNullOrEmpty(brand_name)) {
            dynamicSQL.append("AND ").append(Brand.TABLE_BRAND).append(".").append(Brand.BRAND_NAME).append(" LIKE ? ", "%" + brand_name + "%");
        }
        dynamicSQL.append("ORDER BY ").append(Brand.TABLE_BRAND).append(".").append(Brand.SYSTEM_CREATE_TIME).append(" DESC ");
        dynamicSQL.append("LIMIT ?, ? ", m, n);

        return (List<Brand>) DatabaseUtil.list(dynamicSQL.getSql(), dynamicSQL.getParameterList(), Brand.class);
    }

    public Brand find(String brand_id) {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT ");
        dynamicSQL.append(Brand.TABLE_BRAND).append(".* ");
        dynamicSQL.append("FROM ").append(Brand.TABLE_BRAND).append(" ");
        dynamicSQL.append("WHERE ").append(Brand.TABLE_BRAND).append(".").append(Brand.SYSTEM_STATUS).append(" = ? ", true);
        dynamicSQL.append("AND ").append(Brand.TABLE_BRAND).append(".").append(Brand.BRAND_ID).append(" = ? ", brand_id);

        return (Brand) DatabaseUtil.find(dynamicSQL.getSql(), dynamicSQL.getParameterList(), Brand.class);
    }

    public boolean save(Brand brand) {
        brand.setBrand_id(Util.getRandomUUID());

        return brand.save();
    }

    public boolean update(Brand brand) {
        return brand.update();
    }

    public boolean delete(Brand brand) {
        return brand.delete();
    }

}