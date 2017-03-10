package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.service.CategoryService;
import com.shanghaichuangshi.shop.dao.ProductDao;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.type.CategoryType;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ProductService extends Service {

    private static final ProductDao productDao = new ProductDao();

    private static final CategoryService categoryService = new CategoryService();
    private static final SkuService skuService = new SkuService();
    private static final MemberService memberService = new MemberService();

    public int count(Product product) {
        return productDao.count(product.getProduct_name());
    }

    public List<Product> list(Product product, int m, int n) {
        return productDao.list(product.getProduct_name(), m, n);
    }

    public List<Category> categoryList() {
        return categoryService.listByCategory_key(CategoryType.PRODUCT.getKey());
    }

    public Product find(String product_id, String request_user_id) {
        Product product = productDao.find(product_id);

        String member_level_id = memberService.findMember_lever_idByUser_id(request_user_id);

        List<Sku> skuList = skuService.list(product.getProduct_id());

        for (Sku sku : skuList) {
            JSONArray priceArray = new JSONArray();

            JSONObject jsonObject = skuService.getProduct_price(sku, member_level_id);

            priceArray.add(jsonObject);

            sku.setProduct_price(priceArray.toJSONString());
        }

        product.put(Sku.SKU_LIST, skuList);

        return product;
    }

    public Product adminFind(String product_id) {
        Product product = productDao.find(product_id);

        List<Sku> skuList = skuService.list(product.getProduct_id());
        product.put(Sku.SKU_LIST, skuList);

        return product;
    }

    private List<Sku> getSkuList(String product_id, JSONObject jsonObject) {
        JSONArray skuJsonArray = jsonObject.getJSONArray(Sku.SKU_LIST);

        List<Sku> skuList = new ArrayList<Sku>();

        for (int i = 0; i < skuJsonArray.size(); i++) {
            JSONObject skuJsonObject = skuJsonArray.getJSONObject(i);

            Sku sku = new Sku();
            sku.setProduct_id(product_id);
            sku.setProduct_attribute(skuJsonObject.getString(Sku.PRODUCT_ATTRIBUTE));
            sku.setProduct_price(skuJsonObject.getString(Sku.PRODUCT_PRICE));
            sku.setProduct_stock(skuJsonObject.getInteger(Sku.PRODUCT_STOCK));
            skuList.add(sku);
        }

        return skuList;
    }

    public Product save(Product product, JSONObject jsonObject, String request_user_id) {
        List<Sku> skuSaveList = getSkuList(product.getProduct_id(), jsonObject);

        skuService.save(skuSaveList, request_user_id);

        return productDao.save(product, request_user_id);
    }

    public boolean update(Product product, JSONObject jsonObject, String request_user_id) {
        JSONArray skuJsonArray = jsonObject.getJSONArray(Sku.SKU_LIST);

        List<Sku> skuAllList = skuService.list(product.getProduct_id());
        List<Sku> skuList = getSkuList(product.getProduct_id(), jsonObject);
        List<Sku> skuSaveList = new ArrayList<Sku>();
        List<Sku> skuUpdateList = new ArrayList<Sku>();
        List<Sku> skuDeleteList = new ArrayList<Sku>();

        for (Sku sku : skuAllList) {
            boolean isExit = false;

            for (Sku s : skuList) {
                if (sku.getProduct_attribute().equals(s.getProduct_attribute()) && sku.getProduct_price().equals(s.getProduct_price())) {
                    isExit = true;

                    //判断商品库存是否修改
                    if (!sku.getProduct_stock().equals(s.getProduct_stock())) {
                        skuUpdateList.add(s);
                    }

                    break;
                }
            }

            //判断商品属性和商品价格是否修改
            if (!isExit) {
                skuDeleteList.add(sku);
            }
        }

        for (Sku s : skuList) {
            Boolean isExit = false;

            for (Sku sku : skuAllList) {
                if (sku.getProduct_attribute().equals(s.getProduct_attribute()) && sku.getProduct_price().equals(s.getProduct_price())) {
                    isExit = true;

                    break;
                }
            }

            //判断是否保存
            if (!isExit) {
                skuSaveList.add(s);
            }
        }

        skuService.delete(skuDeleteList, product.getProduct_id(), request_user_id);
        skuService.save(skuSaveList, request_user_id);
        skuService.updateProduct_stock(skuUpdateList, product.getProduct_id(), request_user_id);

        return productDao.update(product, request_user_id);
    }

    public boolean delete(Product product, String request_user_id) {
        return productDao.delete(product.getProduct_id(), request_user_id);
    }

}