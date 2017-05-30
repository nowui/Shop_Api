package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Commission;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.shop.service.ProductService;

import java.util.*;

public class ProductController extends Controller {

    private final ProductService productService = new ProductService();

    @ActionKey(Url.PRODUCT_LIST)
    public void list() {
        List<Product> productList = productService.hotList();

        renderSuccessJson(productList);
    }

    @ActionKey(Url.PRODUCT_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Product model = getParameter(Product.class);

        model.validate(Product.PRODUCT_NAME);

        int count = productService.count(model);

        List<Product> productList = productService.list(model, getM(), getN());

        renderSuccessJson(count, productList);
    }

    @ActionKey(Url.PRODUCT_HOT_LIST)
    public void hotList() {
        List<Product> productList = productService.hotList();

        renderSuccessJson(productList);
    }

    @ActionKey(Url.PRODUCT_CATEGORY_LIST)
    public void categoryList() {
        List<Category> categoryList = productService.categoryList();

        renderSuccessJson(categoryList);
    }

    @ActionKey(Url.PRODUCT_ALL_LIST)
    public void allList() {
        List<Product> productList = productService.allList();

        renderSuccessJson(productList);
    }

    @ActionKey(Url.PRODUCT_MY_LIST)
    public void myList() {
        String request_user_id = getRequest_user_id();

        List<Product> productList = productService.myList(request_user_id);

        if (productList == null) {
            productList = new ArrayList<Product>();
        }

        renderSuccessJson(productList);
    }

    @ActionKey(Url.PRODUCT_FIND)
    public void find() {
        Product model = getParameter(Product.class);
        String request_user_id = getRequest_user_id();

        model.validate(Product.PRODUCT_ID);

        Product product = productService.findByUser_id(model.getProduct_id(), request_user_id);

        renderSuccessJson(product.removeUnfindable());
    }

    @ActionKey(Url.PRODUCT_ADMIN_FIND)
    public void adminFind() {
        Product model = getParameter(Product.class);

        model.validate(Product.PRODUCT_ID);

        Product product = productService.adminFind(model.getProduct_id());

        renderSuccessJson(product.removeSystemInfo());
    }

    @ActionKey(Url.PRODUCT_SAVE)
    public void save() {
        Product model = getParameter(Product.class);
        String request_user_id = getRequest_user_id();

        model.validate(Product.PRODUCT_NAME);

        validate(Product.PRODUCT_IMAGE_LIST, Sku.SKU_LIST, Commission.COMMISSION_LIST);

        productService.save(model, getAttr(Constant.REQUEST_PARAMETER), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.PRODUCTL_UPDATE)
    public void update() {
        Product model = getParameter(Product.class);
        String request_user_id = getRequest_user_id();

        model.validate(Product.PRODUCT_ID, Product.CATEGORY_ID, Product.BRAND_ID, Product.PRODUCT_NAME, Product.PRODUCT_PRICE, Product.PRODUCT_STOCK);

        validate(Product.PRODUCT_IMAGE_LIST, Sku.SKU_LIST, Commission.COMMISSION_LIST);

        productService.update(model, getAttr(Constant.REQUEST_PARAMETER), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.PRODUCT_DELETE)
    public void delete() {
        Product model = getParameter(Product.class);
        String request_user_id = getRequest_user_id();

        model.validate(Product.PRODUCT_ID);

        productService.delete(model, request_user_id);

        renderSuccessJson();
    }

}