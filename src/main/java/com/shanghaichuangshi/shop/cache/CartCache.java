package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.CartDao;
import com.shanghaichuangshi.shop.model.Cart;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class CartCache extends Cache {

    private final String CART_LIST_BY_USER_ID_CACHE = "cart_list_by_user_id_cache";

    private CartDao cartDao = new CartDao();

    public int count() {
        return cartDao.count();
    }

    public List<Cart> list() {
        return cartDao.list();
    }

    public List<Cart> listByUser_id(String user_id) {
        List<Cart> cartList = CacheUtil.get(CART_LIST_BY_USER_ID_CACHE, user_id);

        if (cartList == null) {
            cartList = cartDao.listByUser_id(user_id);

            CacheUtil.put(CART_LIST_BY_USER_ID_CACHE, user_id, cartList);
        }

        return cartList;
    }

    public Cart find(String cart_id) {
        return cartDao.find(cart_id);
    }

    public Cart save(Cart cart, String request_user_id) {
        CacheUtil.remove(CART_LIST_BY_USER_ID_CACHE, request_user_id);

        return cartDao.save(cart, request_user_id);
    }

    public boolean update(String cart_id, String cart_product_quantity, String request_user_id) {
        CacheUtil.remove(CART_LIST_BY_USER_ID_CACHE, request_user_id);

        return cartDao.update(cart_id, cart_product_quantity, request_user_id);
    }

    public boolean delete(String cart_id, String request_user_id) {
        CacheUtil.remove(CART_LIST_BY_USER_ID_CACHE, request_user_id);

        return cartDao.delete(cart_id, request_user_id);
    }

}