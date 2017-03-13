package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.shop.service.ProductService;

import java.util.*;

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

    @ActionKey(Url.PRODUCT_ALL_LIST)
    public void allList() {
        List<Category> categoryList = productService.categoryList();

        List<Product> productList = productService.listAll();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Category category : categoryList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Category.CATEGORY_ID, category.getCategory_id());
            map.put(Category.CATEGORY_NAME, category.getCategory_name());

            List<Product> pList = new ArrayList<Product>();
            for(Product product : productList) {
                if (product.getCategory_id().equals(category.getCategory_id())) {
                    product.keep(Product.PRODUCT_ID, Product.PRODUCT_NAME, Product.PRODUCT_IMAGE, Product.PRODUCT_PRICE, Product.CATEGORY_ID);

                    pList.add(product);
                }
            }
            map.put(Constant.CHILDREN, pList);

            list.add(map);
        }

        renderSuccessJson(list);
    }

    @ActionKey(Url.PRODUCT_FIND)
    public void find() {
        Product model = getParameter(Product.class);
        String request_user_id = getRequest_user_id();

        model.validate(Product.PRODUCT_ID);

        Product product = productService.findByUser_id(model.getProduct_id(), request_user_id);

        renderSuccessJson(product.format());
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