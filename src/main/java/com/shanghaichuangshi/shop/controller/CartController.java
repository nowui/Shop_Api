package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Cart;
import com.shanghaichuangshi.shop.service.CartService;

import java.util.List;

public class CartController extends Controller {

    private final CartService cartService = new CartService();

    @ActionKey(Url.CART_LIST)
    public void list() {
        String request_user_id = getRequest_user_id();

        List<Cart> cartListvice = cartService.listByUser_id(request_user_id);

        renderSuccessJson(cartListvice);
    }

    @ActionKey(Url.CART_ADMIN_LIST)
    public void adminList() {
        Cart model = getParameter(Cart.class);

        int count = cartService.count();

        List<Cart> cartListvice = cartService.list();

        renderSuccessJson(count, cartListvice);
    }

    @ActionKey(Url.CART_FIND)
    public void find() {
        Cart model = getParameter(Cart.class);

        model.validate(Cart.CART_ID);

        Cart cart = cartService.find(model.getCart_id());

        renderSuccessJson(cart.removeUnfindable());
    }

    @ActionKey(Url.CART_ADMIN_FIND)
    public void adminFind() {
        Cart model = getParameter(Cart.class);

        model.validate(Cart.CART_ID);

        Cart cart = cartService.find(model.getCart_id());

        renderSuccessJson(cart.removeSystemInfo());
    }

    @ActionKey(Url.CART_SAVE)
    public void save() {
        Cart model = getParameter(Cart.class);
        String request_user_id = getRequest_user_id();

        model.validate(Cart.USER_ID);

        cartService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.CARTL_UPDATE)
    public void update() {
        Cart model = getParameter(Cart.class);
        String request_user_id = getRequest_user_id();

        model.validate(Cart.CART_ID, Cart.USER_ID);

        cartService.update(model.getCart_id(), model.getCart_product_quantity(), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.CART_DELETE)
    public void delete() {
        Cart model = getParameter(Cart.class);
        String request_user_id = getRequest_user_id();

        model.validate(Cart.CART_ID);

        cartService.delete(model, request_user_id);

        renderSuccessJson();
    }

}