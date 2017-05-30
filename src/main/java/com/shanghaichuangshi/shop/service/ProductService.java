package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.dao.CategoryDao;
import com.shanghaichuangshi.dao.FileDao;
import com.shanghaichuangshi.dao.UserDao;
import com.shanghaichuangshi.model.File;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.shop.dao.CommissionDao;
import com.shanghaichuangshi.shop.dao.OrderProductDao;
import com.shanghaichuangshi.shop.dao.ProductDao;
import com.shanghaichuangshi.shop.dao.SkuDao;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.type.CategoryType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductService extends Service {

    private final ProductDao productDao = new ProductDao();

    private final UserDao userDao = new UserDao();
    private final CategoryDao categoryDao = new CategoryDao();
    private final SkuDao skuDao = new SkuDao();
    private final CommissionDao commissionDao = new CommissionDao();
    private final MemberService memberService = new MemberService();
    private final FileDao fileDao = new FileDao();
    private final OrderProductDao orderProductDao = new OrderProductDao();

    public int count(Product product) {
        return productDao.count(product.getProduct_name());
    }

    public List<Product> list(Product product, int m, int n) {
        return productDao.list(product.getProduct_name(), m, n);
    }

    public List<Product> hotList() {
        List<Product> productList = productDao.listAllHot();

        for (Product product : productList) {
            File file = fileDao.find(product.getProduct_image());
            product.put(Product.PRODUCT_IMAGE_FILE, file.getFile_original_path());
            product.remove(Product.PRODUCT_IMAGE);
        }

        return productList;
    }

    public List<Map<String, Object>> categoryList() {
        return categoryDao.treeListByCategory_key(CategoryType.PRODUCT.getKey());
    }

    public List<Product> allList() {
        List<Product> productList = productDao.listAll();

        for (Product product : productList) {
            File file = fileDao.find(product.getProduct_image());
            product.put(Product.PRODUCT_IMAGE_FILE, file.getFile_original_path());
            product.remove(Product.PRODUCT_IMAGE);
        }

        return productList;
    }

    public List<Product> myList(String request_user_id) {
        List<OrderProduct> orderProductList = orderProductDao.listByUser_id(request_user_id);

        List<Product> productList = new ArrayList<Product>();

        for (OrderProduct orderProduct : orderProductList) {
            Product product = productDao.find(orderProduct.getProduct_id());

            File file = fileDao.find(product.getProduct_image());
            product.put(Product.PRODUCT_IMAGE_FILE, file.getFile_original_path());
            product.remove(Product.PRODUCT_IMAGE);

            product.keep(Product.PRODUCT_ID, Product.PRODUCT_NAME, Product.PRODUCT_PRICE, Product.PRODUCT_IMAGE_FILE, Product.SYSTEM_CREATE_TIME);

            productList.add(product);
        }

        return productList;
    }

    public Product find(String product_id) {
        return productDao.find(product_id);
    }

    public Product findByUser_id(String product_id, String user_id) {
        Product product = productDao.find(product_id);

        File productImageFile = fileDao.find(product.getProduct_image());
        product.put(Product.PRODUCT_IMAGE_FILE, productImageFile.getFile_original_path());

        List<String> productImageFileList = new ArrayList<String>();
        JSONArray productImageList = JSONArray.parseArray(product.getProduct_image_list().toString());
        for (int i = 0; i < productImageList.size(); i++) {
            File file = fileDao.find(productImageList.getString(i));
            productImageFileList.add(file.getFile_original_path());
        }
        product.put(Product.PRODUCT_IMAGE_FILE_LIST, productImageFileList);

        User user = userDao.find(user_id);
        Member member = memberService.find(user.getObject_id());

        String member_level_id = "";
        if (member != null) {
            member_level_id = member.getMember_level_id();
        }

        List<Sku> skuList = skuDao.listByProduct_idAndMember_level_id(product.getProduct_id(), member_level_id);

        product.put(Sku.SKU_LIST, skuList);

        return product;
    }

    public Product adminFind(String product_id) {
        Product product = productDao.find(product_id);

        File productImageFile = fileDao.find(product.getProduct_image());
        product.put(Product.PRODUCT_IMAGE_FILE, productImageFile.keep(File.FILE_ID, File.FILE_NAME, File.FILE_PATH));

        List<File> productImageFileList = new ArrayList<File>();
        JSONArray productImageList = JSONArray.parseArray(product.getProduct_image_list().toString());
        for (int i = 0; i < productImageList.size(); i++) {
            File file = fileDao.find(productImageList.getString(i));
            file.keep(File.FILE_ID, File.FILE_NAME, File.FILE_PATH);
            productImageFileList.add(file);
        }
        product.put(Product.PRODUCT_IMAGE_FILE_LIST, productImageFileList);

        List<Sku> skuList = skuDao.list(product_id);
        product.put(Sku.SKU_LIST, skuList);

        List<Commission> commissionList = commissionDao.list(product.getProduct_id());
        product.put(Commission.COMMISSION_LIST, commissionList);

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

    private List<Commission> getCommissionList(String product_id, JSONObject jsonObject) {
        JSONArray commissionArray = jsonObject.getJSONArray(Commission.COMMISSION_LIST);

        List<Commission> commissionList = new ArrayList<Commission>();

        for (int i = 0; i < commissionArray.size(); i++) {
            JSONObject commissionJsonObject = commissionArray.getJSONObject(i);

            Commission commission = new Commission();
            commission.setProduct_id(product_id);
            commission.setProduct_attribute(commissionJsonObject.getString(Commission.PRODUCT_ATTRIBUTE));
            commission.setProduct_commission(commissionJsonObject.getString(Commission.PRODUCT_COMMISSION));
            commissionList.add(commission);
        }

        return commissionList;
    }

    public Product save(Product product, JSONObject jsonObject, String request_user_id) {
        Product p = productDao.save(product, request_user_id);

        List<Sku> skuSaveList = getSkuList(p.getProduct_id(), jsonObject);
        skuDao.save(skuSaveList, request_user_id);

        List<Commission> commissionLis = getCommissionList(p.getProduct_id(), jsonObject);
        commissionDao.save(commissionLis, request_user_id);

        return p;
    }

    public boolean update(Product product, JSONObject jsonObject, String request_user_id) {
        List<Sku> skuAllList = skuDao.list(product.getProduct_id());
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
                        s.setSku_id(sku.getSku_id());
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

        skuDao.delete(skuDeleteList, product.getProduct_id(), request_user_id);
        skuDao.save(skuSaveList, request_user_id);
        skuDao.updateProduct_stock(skuUpdateList, product.getProduct_id(), request_user_id);


        List<Commission> commissionAllList = commissionDao.list(product.getProduct_id());
        List<Commission> commissionList = getCommissionList(product.getProduct_id(), jsonObject);
        List<Commission> commissionSaveList = new ArrayList<Commission>();
        List<Commission> commissionDeleteList = new ArrayList<Commission>();

        for (Commission commission : commissionAllList) {
            boolean isExit = false;

            for (Commission c : commissionList) {
                if (commission.getProduct_attribute().equals(c.getProduct_attribute()) && commission.getProduct_commission().equals(c.getProduct_commission())) {
                    isExit = true;

                    break;
                }
            }

            //判断商品属性和商品价格是否修改
            if (!isExit) {
                commissionDeleteList.add(commission);
            }
        }

        for (Commission c : commissionList) {
            Boolean isExit = false;

            for (Commission commission : commissionAllList) {
                if (commission.getProduct_attribute().equals(c.getProduct_attribute()) && commission.getProduct_commission().equals(c.getProduct_commission())) {
                    isExit = true;

                    break;
                }
            }

            //判断是否保存
            if (!isExit) {
                commissionSaveList.add(c);
            }
        }

        commissionDao.delete(commissionDeleteList, product.getProduct_id(), request_user_id);
        commissionDao.save(commissionSaveList, request_user_id);

        return productDao.update(product, request_user_id);
    }

    public boolean delete(Product product, String request_user_id) {
        skuDao.deleteByProduct_id(product.getProduct_id(), request_user_id);

        commissionDao.deleteByProduct_id(product.getProduct_id(), request_user_id);

        return productDao.delete(product.getProduct_id(), request_user_id);
    }

}