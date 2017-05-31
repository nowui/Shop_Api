package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.cache.CartCache;
import com.shanghaichuangshi.shop.model.Cart;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class CartService extends Service {

    private final CartCache cartCache = new CartCache();

    public int count() {
        return cartCache.count();
    }

    public List<Cart> list() {
        return cartCache.list();
    }

    public List<Cart> listByUser_id(String user_id) {
        return cartCache.listByUser_id(user_id);
    }

    public Cart find(String cart_id) {
        return cartCache.find(cart_id);
    }

    public Cart save(Cart cart, String request_user_id) {
        return cartCache.save(cart, request_user_id);
    }

    public boolean update(String cart_id, String cart_product_quantity, String request_user_id) {
        return cartCache.update(cart_id, cart_product_quantity, request_user_id);
    }

    public boolean delete(Cart cart, String request_user_id) {
        return cartCache.delete(cart.getCart_id(), request_user_id);
    }

}