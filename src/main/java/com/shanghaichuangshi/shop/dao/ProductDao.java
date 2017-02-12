package com.shanghaichuangshi.shop.dao;

import com.shanghaichuangshi.config.DynamicSQL;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.util.DatabaseUtil;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class ProductDao extends Dao {

    public int count() {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT COUNT(*) FROM ").append(Product.TABLE_PRODUCT).append(" ");
        dynamicSQL.append("WHERE ").append(Product.TABLE_PRODUCT).append(".").append(Product.SYSTEM_STATUS).append(" = ? ", true);

        return DatabaseUtil.count(dynamicSQL.getSql(), dynamicSQL.getParameterList());
    }

    public List<Product> list(String product_name, Integer m, Integer n) {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT ");
        dynamicSQL.append(Product.TABLE_PRODUCT).append(".").append(Product.PRODUCT_ID).append(", ");
        dynamicSQL.append(Product.TABLE_PRODUCT).append(".").append(Product.PRODUCT_NAME).append(", ");
        dynamicSQL.append(Product.TABLE_PRODUCT).append(".").append(Product.PRODUCT_IMAGE).append(", ");
        dynamicSQL.append(Product.TABLE_PRODUCT).append(".").append(Product.PRODUCT_PRICE).append(" ");
        dynamicSQL.append("FROM ").append(Product.TABLE_PRODUCT).append(" ");
        dynamicSQL.append("WHERE ").append(Product.TABLE_PRODUCT).append(".").append(Product.SYSTEM_STATUS).append(" = ? ", true);
        if (!Util.isNullOrEmpty(product_name)) {
            dynamicSQL.append("AND ").append(Product.TABLE_PRODUCT).append(".").append(Product.PRODUCT_NAME).append(" LIKE ? ", "%" + product_name + "%");
        }
        dynamicSQL.append("ORDER BY ").append(Product.TABLE_PRODUCT).append(".").append(Product.SYSTEM_CREATE_TIME).append(" DESC ");
        if (n > 0) {
            dynamicSQL.append("LIMIT ?, ? ", m, n);
        }

        return (List<Product>) DatabaseUtil.list(dynamicSQL.getSql(), dynamicSQL.getParameterList(), Product.class);
    }

    public Product find(String product_id) {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT ");
        dynamicSQL.append(Product.TABLE_PRODUCT).append(".* ");
        dynamicSQL.append("FROM ").append(Product.TABLE_PRODUCT).append(" ");
        dynamicSQL.append("WHERE ").append(Product.TABLE_PRODUCT).append(".").append(Product.SYSTEM_STATUS).append(" = ? ", true);
        dynamicSQL.append("AND ").append(Product.TABLE_PRODUCT).append(".").append(Product.PRODUCT_ID).append(" = ? ", product_id);

        return (Product) DatabaseUtil.find(dynamicSQL.getSql(), dynamicSQL.getParameterList(), Product.class);
    }

    public boolean save(Product product) {
        product.setProduct_id(Util.getRandomUUID());

        return product.save();
    }

    public boolean update(Product product) {
        return product.update();
    }

    public boolean delete(Product product) {
        return product.delete();
    }

}