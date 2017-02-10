package com.shanghaichuangshi.shop.controller;

import com.shanghaichuangshi.annotation.Path;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.service.CategoryService;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.shop.service.ProductService;

import java.util.List;

public class ProductController extends Controller {

    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();

    @Path(Url.PRODUCT_LIST)
    public void list() {
        Product productModel = getModel(Product.class);

        productModel.validate(Product.PRODUCT_NAME, Product.PAGE_INDEX, Product.PAGE_SIZE);

        List<Product> productList = productService.list(productModel);

        renderJson(productList);
    }

    @Path(Url.PRODUCT_ADMIN_LIST)
    public void adminList() {
        Product productModel = getModel(Product.class);

        productModel.validate(Product.PAGE_INDEX, Product.PAGE_SIZE);

        int count = productService.count(productModel);

        List<Product> productList = productService.list(productModel);

        renderJson(count, productList);
    }

    @Path(Url.PRODUCT_CATEGORY_LIST)
    public void categoryList() {
        List<Category> categoryList = categoryService.listByCategory_key("PRODUCT");

        renderJson(categoryList);
    }

    @Path(Url.PRODUCT_FIND)
    public void find() {
        Product productModel = getModel(Product.class);

        productModel.validate(Product.PRODUCT_ID);

        Product product = productService.find(productModel);

        product.removeUnfindable();

        renderJson(product);
    }

    @Path(Url.PRODUCT_ADMIN_FIND)
    public void adminFind() {
        Product productModel = getModel(Product.class);

        productModel.validate(Product.PRODUCT_ID);

        Product product = productService.find(productModel);

        renderJson(product);
    }

    @Path(Url.PRODUCT_SAVE)
    public void save() {
        Product productModel = getModel(Product.class);

        productModel.validate(Product.PRODUCT_NAME);

        productService.save(productModel);

        renderJson("");
    }

    @Path(Url.PRODUCTL_UPDATE)
    public void update() {
        Product productModel = getModel(Product.class);

        productModel.validate(Product.PRODUCT_ID, Product.PRODUCT_NAME);

        productService.update(productModel);

        renderJson("");
    }

    @Path(Url.PRODUCT_DELETE)
    public void delete() {
        Product productModel = getModel(Product.class);

        productModel.validate(Product.PRODUCT_ID);

        productService.delete(productModel);

        renderJson("");
    }

}