package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.Cart;
import com.shanghaichuangshi.util.Util;

import java.util.Date;
import java.util.List;

public class CartDao extends Dao {

    public int count() {
        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("cart.count", map);

        Number count = Db.queryFirst(sqlPara.getSql(), sqlPara.getPara());
        return count.intValue();
    }

    public List<Cart> list() {
        Kv map = Kv.create();
        SqlPara sqlPara = Db.getSqlPara("cart.list", map);

        return new Cart().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public List<Cart> listByUser_id(String user_id) {
        Kv map = Kv.create();
        map.put(Cart.USER_ID, user_id);
        SqlPara sqlPara = Db.getSqlPara("cart.listByUser_id", map);

        return new Cart().find(sqlPara.getSql(), sqlPara.getPara());
    }

    public Cart find(String cart_id) {
        Kv map = Kv.create();
        map.put(Cart.CART_ID, cart_id);
        SqlPara sqlPara = Db.getSqlPara("cart.find", map);

        List<Cart> cartList = new Cart().find(sqlPara.getSql(), sqlPara.getPara());
        if (cartList.size() == 0) {
            return null;
        } else {
            return cartList.get(0);
        }
    }

    public Cart save(Cart cart, String request_user_id) {
        cart.setCart_id(Util.getRandomUUID());
        cart.setSystem_create_user_id(request_user_id);
        cart.setSystem_create_time(new Date());
        cart.setSystem_update_user_id(request_user_id);
        cart.setSystem_update_time(new Date());
        cart.setSystem_status(true);

        cart.save();

        return cart;
    }

    public boolean update(String cart_id, String cart_product_quantity, String request_user_id) {
        Kv map = Kv.create();
        map.put(Cart.CART_ID, cart_id);
        map.put(Cart.CART_PRODUCT_QUANTITY, cart_product_quantity);
        map.put(Cart.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Cart.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("cart.update", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

    public boolean delete(String cart_id, String request_user_id) {
        Kv map = Kv.create();
        map.put(Cart.CART_ID, cart_id);
        map.put(Cart.SYSTEM_UPDATE_USER_ID, request_user_id);
        map.put(Cart.SYSTEM_UPDATE_TIME, new Date());
        SqlPara sqlPara = Db.getSqlPara("cart.delete", map);

        return Db.update(sqlPara.getSql(), sqlPara.getPara()) != 0;
    }

}