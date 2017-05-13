package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.OrderProduct;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderProductDao extends Dao {

    private final String ORDER_PRODUCT_LIST_BY_ORDER_ID_CACHE = "order_product_list_by_order_id_cache";
    private final String ORDER_PRODUCT_LIST_BY_MEMBER_ID_CACHE = "order_product_list_by_member_id_cache";

    public List<OrderProduct> listByOder_id(String order_id) {
        List<OrderProduct> orderProductList = CacheUtil.get(ORDER_PRODUCT_LIST_BY_ORDER_ID_CACHE, order_id);

        if (orderProductList == null) {
            Kv map = Kv.create();
            map.put(OrderProduct.ORDER_ID, order_id);
            SqlPara sqlPara = Db.getSqlPara("order_product.listByOder_id", map);

            orderProductList = new OrderProduct().find(sqlPara.getSql(), sqlPara.getPara());

            if (orderProductList.size() > 0) {
                CacheUtil.put(ORDER_PRODUCT_LIST_BY_ORDER_ID_CACHE, order_id, orderProductList);
            }
        }

        return orderProductList;
    }

    public List<OrderProduct> listByMember_id(String member_id) {
        List<OrderProduct> orderProductList = CacheUtil.get(ORDER_PRODUCT_LIST_BY_MEMBER_ID_CACHE, member_id);

        if (orderProductList == null) {
            Kv map = Kv.create();
            map.put(OrderProduct.MEMBER_ID, member_id);
            SqlPara sqlPara = Db.getSqlPara("order_product.listByMember_id", map);

            orderProductList = new OrderProduct().find(sqlPara.getSql(), sqlPara.getPara());

            if (orderProductList.size() > 0) {
                CacheUtil.put(ORDER_PRODUCT_LIST_BY_MEMBER_ID_CACHE, member_id, orderProductList);
            }
        }

        return orderProductList;
    }

    public OrderProduct find(String order_product_id) {
        Kv map = Kv.create();
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
        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("order_product.save", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(OrderProduct orderProduct : orderProductList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(orderProduct.getOrder_product_id());
            objectList.add(orderProduct.getOrder_id());
            objectList.add(orderProduct.getOrder_status());
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
            objectList.add(orderProduct.getCommission_id());
            objectList.add(orderProduct.getMember_id());
            objectList.add(orderProduct.getOrder_product_commission());
            objectList.add(orderProduct.getProduct_attribute());
            objectList.add(orderProduct.getProduct_market_price());
            objectList.add(orderProduct.getProduct_price());
            objectList.add(orderProduct.getProduct_stock());
            objectList.add(orderProduct.getOrder_product_price());
            objectList.add(orderProduct.getOrder_product_quantity());
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

//    public boolean update(OrderProduct order_product, String request_user_id) {
//        order_product.remove(OrderProduct.SYSTEM_CREATE_USER_ID);
//        order_product.remove(OrderProduct.SYSTEM_CREATE_TIME);
//        order_product.setSystem_update_user_id(request_user_id);
//        order_product.setSystem_update_time(new Date());
//        order_product.remove(OrderProduct.SYSTEM_STATUS);
//
//        return order_product.update();
//    }

    public boolean updateByOrder_idAndOrder_status(String order_id, Boolean order_status, String member_id) {
        CacheUtil.remove(ORDER_PRODUCT_LIST_BY_ORDER_ID_CACHE, order_id);
        CacheUtil.remove(ORDER_PRODUCT_LIST_BY_MEMBER_ID_CACHE, member_id);

        Kv map = Kv.create();
        map.put(OrderProduct.ORDER_ID, order_id);
        map.put(OrderProduct.ORDER_STATUS, order_status);
        map.put(OrderProduct.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("order_product.updateByOrder_idAndOrder_status", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

//    public boolean delete(String order_product_id, String request_user_id) {
//        Kv map = Kv.create();
//        map.put(OrderProduct.ORDER_PRODUCT_ID, order_product_id);
//        map.put(OrderProduct.SYSTEM_UPDATE_USER_ID, request_user_id);
//        map.put(OrderProduct.SYSTEM_UPDATE_TIME, new Date());
//        SqlPara sqlPara = Db.getSqlPara("order_product.delete", map);
//
//        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
//    }

}