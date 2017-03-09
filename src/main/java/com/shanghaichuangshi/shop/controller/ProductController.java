package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.shop.service.ProductService;

import java.util.List;

public class ProductController extends Controller {

    private static final ProductService productService = new ProductService();

    @ActionKey(Url.PRODUCT_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Product model = getParameter(Product.class);

        model.validate(Product.PRODUCT_NAME);

        List<Product> productList = productService.list(model, getM(), getN());

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

    @ActionKey(Url.PRODUCT_CATEGORY_LIST)
    public void categoryList() {
        List<Category> categoryList = productService.categoryList();

        renderSuccessJson(categoryList);
    }

    @ActionKey(Url.PRODUCT_FIND)
    public void find() {
        Product model = getParameter(Product.class);
        String request_user_id = getRequest_user_id();

        model.validate(Product.PRODUCT_ID);

        Product product = productService.find(model.getProduct_id(), request_user_id);

        product.removeUnfindable();

        renderSuccessJson(product);
    }

    @ActionKey(Url.PRODUCT_ADMIN_FIND)
    public void adminFind() {
        Product model = getParameter(Product.class);

        model.validate(Product.PRODUCT_ID);

        Product product = productService.adminFind(model.getProduct_id());

        renderSuccessJson(product);
    }

    @ActionKey(Url.PRODUCT_SAVE)
    public void save() {
        Product model = getParameter(Product.class);
        String request_user_id = getRequest_user_id();

        model.validate(Product.PRODUCT_NAME);

        productService.save(model, getAttr(Constant.REQUEST_PARAMETER), request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.PRODUCTL_UPDATE)
    public void update() {
        Product model = getParameter(Product.class);
        String request_user_id = getRequest_user_id();

        model.validate(Product.PRODUCT_ID, Product.PRODUCT_NAME);

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