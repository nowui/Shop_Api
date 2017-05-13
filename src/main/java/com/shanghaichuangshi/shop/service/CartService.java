package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.CartDao;
import com.shanghaichuangshi.shop.model.Cart;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class CartService extends Service {

    private final CartDao cartDao = new CartDao();

    public int count(Cart cart) {
        return cartDao.count(cart.getUser_id());
    }

    public List<Cart> list(Cart cart) {
        return cartDao.list(cart.getUser_id());
    }

    public Cart find(String cart_id) {
        return cartDao.find(cart_id);
    }

    public Cart save(Cart cart, String request_user_id) {
        return cartDao.save(cart, request_user_id);
    }

    public boolean update(String cart_id, String cart_product_quantity, String request_user_id) {
        return cartDao.update(cart_id, cart_product_quantity, request_user_id);
    }

    public boolean delete(Cart cart, String request_user_id) {
        return cartDao.delete(cart.getCart_id(), request_user_id);
    }

}