package com.shanghaichuangshi.shop.controller;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Commission;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.shop.model.ProductFile;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.shop.service.ProductFileService;
import com.shanghaichuangshi.shop.service.ProductService;
import com.shanghaichuangshi.type.FileType;

import java.util.*;

public class ProductController extends Controller {

    private final ProductService productService = new ProductService();
    private final ProductFileService productFileService = new ProductFileService();

    @ActionKey(Url.PRODUCT_LIST)
    public void list() {
        List<Product> productList = productService.listHot();

        renderSuccessJson(productList);
    }

    @ActionKey(Url.PRODUCT_HOT_LIST)
    public void hotList() {
        List<Product> productList = productService.listHot();

        renderSuccessJson(productList);
    }

    @ActionKey(Url.PRODUCT_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Product model = getParameter(Product.class);

        model.validate(Product.PRODUCT_NAME);

        int count = productService.count(model);

        List<Product> productList = productService.list(model, getM(), getN());

//        List<Product> productList2 = productService.listAll();
//
//        List<ProductFile> productFileList = new ArrayList<ProductFile>();
//
//        for (Product product : productList2) {
//            JSONArray jsonArray = JSONArray.parseArray(product.getProduct_image_list());
//
//            for (int i = 0; i < jsonArray.size(); i++) {
//                String path = jsonArray.getString(i);
//
//                ProductFile productFile = new ProductFile();
//                productFile.setProduct_id(product.getProduct_id());
//                productFile.setProduct_file_type(FileType.IMAGE.getKey());
//                productFile.setProduct_file_name("");
//                productFile.setProduct_file_path(path);
//                productFile.setProduct_file_thumbnail_path(path.substring(0, path.lastIndexOf("/")) + "/" + Constant.THUMBNAIL + "/" + path.substring(path.lastIndexOf("/") + 1, path.length()));
//                productFile.setProduct_file_original_path(path.substring(0, path.lastIndexOf("/")) + "/" + Constant.ORIGINAL + "/" + path.substring(path.lastIndexOf("/") + 1, path.length()));
//                productFile.setProduct_file_remark("");
//                productFileList.add(productFile);
//            }
//        }
//
//        productFileService.save(productFileList, "6a4dbae2ac824d2fb170638d55139666");

        renderSuccessJson(count, productList);
    }

    @ActionKey(Url.PRODUCT_CATEGORY_LIST)
    public void categoryList() {
        List<Category> categoryList = productService.categoryList();

        renderSuccessJson(categoryList);
    }

    @ActionKey(Url.PRODUCT_ALL_LIST)
    public void allList() {
//        List<Category> categoryList = productService.categoryList();
//
//        List<Product> productList = productService.listAll();
//
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<Product> productList = productService.listAll();

        if (productList == null) {
            productList = new ArrayList<Product>();
        }

//        Map<String, Object> hotMap = new HashMap<String, Object>();
//        hotMap.put(Category.CATEGORY_ID, "");
//        hotMap.put(Category.CATEGORY_NAME, "所有商品");
//        hotMap.put(Constant.CHILDREN, allProductList);
//        list.add(hotMap);
//
//        for (Category category : categoryList) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put(Category.CATEGORY_ID, category.getCategory_id());
//            map.put(Category.CATEGORY_NAME, category.getCategory_name());
//
//            List<Product> pList = new ArrayList<Product>();
//            for (Product product : productList) {
//                if (product.getCategory_id().equals(category.getCategory_id())) {
//                    product.keep(Product.PRODUCT_ID, Product.PRODUCT_NAME, Product.PRODUCT_IMAGE, Product.PRODUCT_PRICE, Product.CATEGORY_ID);
//
//                    pList.add(product);
//                }
//            }
//            map.put(Constant.CHILDREN, pList);
//
//            list.add(map);
//        }

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

        renderSuccessJson(product);
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

        model.validate(Product.PRODUCT_ID, Product.PRODUCT_NAME);

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