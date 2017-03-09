package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.OrderProduct;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class OrderProductDao extends Dao {

    public OrderProduct find(String order_product_id) {
        JMap map = JMap.create();
        map.put(OrderProduct.ORDER_PRODUCT_ID, order_product_id);
        SqlPara sqlPara = Db.getSqlPara("order_product.find", map);

        List<OrderProduct> order_productList = new OrderProduct().find(sqlPara.getSql(), sqlPara.getPara());
        if (order_productList.size() == 0) {
            return null;
        } else {
            return order_productList.get(0);
        }
    }

    public OrderProduct save(OrderProduct order_product, String request_user_id) {
        order_product.setOrder_product_id(Util.getRandomUUID());
        order_product.setSystem_create_user_id(request_user_id);
        order_product.setSystem_create_time(new Date());
        order_product.setSystem_update_user_id(request_user_id);
        order_product.setSystem_update_time(new Date());
        order_product.setSystem_status(true);

        order_product.save();

        return order_product;
    }

    public boolean update(OrderProduct order_product, String request_user_id) {
        order_product.remove(OrderProduct.SYSTEM_CREATE_USER_ID);
        order_product.remove(OrderProduct.SYSTEM_CREATE_TIME);
        order_product.setSystem_update_user_id(request_user_id);
        order_product.setSystem_update_time(new Date());
        order_product.remove(OrderProduct.SYSTEM_STATUS);

        return order_product.update();
    }

    public boolean delete(String order_product_id, String request_user_id) {
        JMap map = JMap.create();
        map.put(OrderProduct.ORDER_PRODUCT_ID, order_product_id);
        map.put(OrderProduct.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(OrderProduct.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order_product.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}