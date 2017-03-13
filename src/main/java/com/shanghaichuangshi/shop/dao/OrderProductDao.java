package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.OrderProduct;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
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

    public void save(List<OrderProduct> orderProductList, String request_user_id) {
        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("order_product.save", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(OrderProduct orderProduct : orderProductList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(Util.getRandomUUID());
            objectList.add(orderProduct.getOrder_id());
            objectList.add(orderProduct.getProduct_id());
            objectList.add(orderProduct.getCategory_id());
            objectList.add(orderProduct.getCategory_name());
            objectList.add(orderProduct.getBrand_id());
            objectList.add(orderProduct.getBrand_name());
            objectList.add(orderProduct.getProduct_name());
            objectList.add(orderProduct.getProduct_image());
            objectList.add(orderProduct.getProduct_image_list());
            objectList.add(orderProduct.getProduct_is_new());
            objectList.add(orderProduct.getProduct_is_recommend());
            objectList.add(orderProduct.getProduct_is_bargain());
            objectList.add(orderProduct.getProduct_is_hot());
            objectList.add(orderProduct.getProduct_is_sale());
            objectList.add(orderProduct.getProduct_content());
            objectList.add(orderProduct.getSku_id());
            objectList.add(orderProduct.getProduct_attribute());
            objectList.add(orderProduct.getProduct_market_price());
            objectList.add(orderProduct.getProduct_price());
            objectList.add(orderProduct.getProduct_stock());
            objectList.add(orderProduct.getProduct_number());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(true);
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("订单商品保存不成功");
            }
        }
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