package com.shanghaichuangshi.shop.controller;

import com.shanghaichuangshi.annotation.Path;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Shop;
import com.shanghaichuangshi.shop.service.ShopService;

import java.util.List;

public class ShopController extends Controller {

    private final ShopService shopService = new ShopService();

    @Path(Url.SHOP_LIST)
    public void list() {
        Shop shopModel = getModel(Shop.class);

        shopModel.validate(Shop.SHOP_NAME, Shop.PAGE_INDEX, Shop.PAGE_SIZE);

        List<Shop> shopList = shopService.list(shopModel);

        renderJson(shopList);
    }

    @Path(Url.SHOP_ADMIN_LIST)
    public void adminList() {
        Shop shopModel = getModel(Shop.class);

        shopModel.validate(Shop.PAGE_INDEX, Shop.PAGE_SIZE);

        int count = shopService.count(shopModel);

        List<Shop> shopList = shopService.list(shopModel);

        renderJson(count, shopList);
    }

    @Path(Url.SHOP_FIND)
    public void find() {
        Shop shopModel = getModel(Shop.class);

        shopModel.validate(Shop.SHOP_ID);

        Shop shop = shopService.find(shopModel);

        shop.removeUnfindable();

        renderJson(shop);
    }

    @Path(Url.SHOP_ADMIN_FIND)
    public void adminFind() {
        Shop shopModel = getModel(Shop.class);

        shopModel.validate(Shop.SHOP_ID);

        Shop shop = shopService.find(shopModel);

        renderJson(shop);
    }

    @Path(Url.SHOP_SAVE)
    public void save() {
        Shop shopModel = getModel(Shop.class);

        shopModel.validate(Shop.SHOP_NAME);

        shopService.save(shopModel);

        renderJson("");
    }

    @Path(Url.SHOPL_UPDATE)
    public void update() {
        Shop shopModel = getModel(Shop.class);

        shopModel.validate(Shop.SHOP_ID, Shop.SHOP_NAME);

        shopService.update(shopModel);

        renderJson("");
    }

    @Path(Url.SHOP_DELETE)
    public void delete() {
        Shop shopModel = getModel(Shop.class);

        shopModel.validate(Shop.SHOP_ID);

        shopService.delete(shopModel);

        renderJson("");
    }

}