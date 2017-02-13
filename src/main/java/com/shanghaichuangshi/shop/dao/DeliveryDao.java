package com.shanghaichuangshi.shop.dao;

import com.shanghaichuangshi.config.DynamicSQL;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.util.DatabaseUtil;
import com.shanghaichuangshi.util.Util;

import java.util.List;

public class DeliveryDao extends Dao {

    public int count() {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT COUNT(*) FROM ").append(Delivery.TABLE_DELIVERY).append(" ");
        dynamicSQL.append("WHERE ").append(Delivery.TABLE_DELIVERY).append(".").append(Delivery.SYSTEM_STATUS).append(" = ? ", true);

        return DatabaseUtil.count(dynamicSQL.getSql(), dynamicSQL.getParameterList());
    }

    public List<Delivery> list(String delivery_name, Integer m, Integer n) {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT ");
        dynamicSQL.append(Delivery.TABLE_DELIVERY).append(".").append(Delivery.DELIVERY_ID).append(", ");
        dynamicSQL.append(Delivery.TABLE_DELIVERY).append(".").append(Delivery.DELIVERY_NAME).append(" ");
        dynamicSQL.append("FROM ").append(Delivery.TABLE_DELIVERY).append(" ");
        dynamicSQL.append("WHERE ").append(Delivery.TABLE_DELIVERY).append(".").append(Delivery.SYSTEM_STATUS).append(" = ? ", true);
        if (!Util.isNullOrEmpty(delivery_name)) {
            dynamicSQL.append("AND ").append(Delivery.TABLE_DELIVERY).append(".").append(Delivery.DELIVERY_NAME).append(" LIKE ? ", "%" + delivery_name + "%");
        }
        dynamicSQL.append("ORDER BY ").append(Delivery.TABLE_DELIVERY).append(".").append(Delivery.SYSTEM_CREATE_TIME).append(" DESC ");
        dynamicSQL.append("LIMIT ?, ? ", m, n);

        return (List<Delivery>) DatabaseUtil.list(dynamicSQL.getSql(), dynamicSQL.getParameterList(), Delivery.class);
    }

    public Delivery find(String delivery_id) {
        DynamicSQL dynamicSQL = new DynamicSQL();

        dynamicSQL.append("SELECT ");
        dynamicSQL.append(Delivery.TABLE_DELIVERY).append(".* ");
        dynamicSQL.append("FROM ").append(Delivery.TABLE_DELIVERY).append(" ");
        dynamicSQL.append("WHERE ").append(Delivery.TABLE_DELIVERY).append(".").append(Delivery.SYSTEM_STATUS).append(" = ? ", true);
        dynamicSQL.append("AND ").append(Delivery.TABLE_DELIVERY).append(".").append(Delivery.DELIVERY_ID).append(" = ? ", delivery_id);

        return (Delivery) DatabaseUtil.find(dynamicSQL.getSql(), dynamicSQL.getParameterList(), Delivery.class);
    }

    public boolean save(Delivery delivery) {
        delivery.setDelivery_id(Util.getRandomUUID());

        return delivery.save();
    }

    public boolean update(Delivery delivery) {
        return delivery.update();
    }

    public boolean delete(Delivery delivery) {
        return delivery.delete();
    }

}