package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.service.CategoryService;
import com.shanghaichuangshi.shop.dao.ProductDao;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.type.CategoryType;
import com.shanghaichuangshi.type.FileType;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductService extends Service {

    private final ProductDao productDao = new ProductDao();

    private final CategoryService categoryService = new CategoryService();
    private final SkuService skuService = new SkuService();
    private final CommissionService commissionService = new CommissionService();
    private final MemberService memberService = new MemberService();
    private final ProductFileService productFileService = new ProductFileService();

    public int count(Product product) {
        return productDao.count(product.getProduct_name());
    }

    public List<Product> list(Product product, int m, int n) {
        return productDao.list(product.getProduct_name(), m, n);
    }

    public List<Category> categoryList() {
        return categoryService.listByCategory_key(CategoryType.PRODUCT.getKey());
    }

    public List<Product> listAll() {
        return productDao.listAll();
    }

    public List<Product> listHot() {
        return productDao.listAllHot();
    }

    public Product find(String product_id) {
        return productDao.find(product_id);
    }

    public Product findByUser_id(String product_id, String user_id) {
        Product product = find(product_id);

        Member member = memberService.findByUser_id(user_id);

        String member_level_id = "";
        if (member != null) {
            member_level_id = member.getMember_level_id();
        }

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
        Product product = find(product_id);

        List<ProductFile> productFileList = productFileService.list(product_id);
        product.put(Product.PRODUCT_IMAGE_LIST, productFileList);

        List<Sku> skuList = skuService.list(product_id);
        product.put(Sku.SKU_LIST, skuList);

        List<Commission> commissionList = commissionService.list(product.getProduct_id());
        product.put(Commission.COMMISSION_LIST, commissionList);

        return product;
    }

    private List<ProductFile> getProductImageList(String product_id, JSONObject jsonObject) {
        JSONArray productImageListJsonArray = jsonObject.getJSONArray(Product.PRODUCT_IMAGE_LIST);

        List<ProductFile> productFileList = new ArrayList<ProductFile>();

        for (int i = 0; i < productImageListJsonArray.size(); i++) {
            String product_file_path = productImageListJsonArray.getString(i);

            ProductFile productFile = new ProductFile();
            productFile.setProduct_id(product_id);
            productFile.setProduct_file_type(FileType.IMAGE.getKey());
            productFile.setProduct_file_name("");
            productFile.setProduct_file_path(product_file_path);
            productFile.setProduct_file_thumbnail_path(Util.getThumbnail_path(product_file_path));
            productFile.setProduct_file_original_path(Util.getOriginal_path(product_file_path));
            productFile.setProduct_file_remark("");
            productFileList.add(productFile);
        }

        return productFileList;
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

        List<ProductFile> productFileSaveList = getProductImageList(p.getProduct_id(), jsonObject);
        productFileService.save(productFileSaveList, request_user_id);

        List<Sku> skuSaveList = getSkuList(p.getProduct_id(), jsonObject);
        skuService.save(skuSaveList, request_user_id);

        List<Commission> commissionLis = getCommissionList(p.getProduct_id(), jsonObject);
        commissionService.save(commissionLis, request_user_id);

        return p;
    }

    public boolean update(Product product, JSONObject jsonObject, String request_user_id) {
        List<ProductFile> productFileAllList = productFileService.list(product.getProduct_id());
        List<ProductFile> productFileList = getProductImageList(product.getProduct_id(), jsonObject);
        List<ProductFile> productFileSaveList = new ArrayList<ProductFile>();
        List<ProductFile> productFileDeleteList = new ArrayList<ProductFile>();

        for (ProductFile productFile : productFileAllList) {
            boolean isExit = false;

            for (ProductFile p : productFileList) {
                if (productFile.getProduct_file_path().equals(p.getProduct_file_path())) {
                    isExit = true;

                    //dsf

                    break;
                }
            }

            if (!isExit) {
                productFileDeleteList.add(productFile);
            }
        }

        for (ProductFile productFile : productFileList) {
            boolean isNotExit = true;

            for (ProductFile p : productFileAllList) {
                if (productFile.getProduct_file_path().equals(p.getProduct_file_path())) {
                    isNotExit = false;

                    break;
                }
            }

            if (isNotExit) {
                productFileSaveList.add(productFile);
            }
        }

        productFileService.delete(productFileDeleteList, product.getProduct_id(), request_user_id);
        productFileService.save(productFileSaveList, request_user_id);

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


        List<Commission> commissionAllList = commissionService.list(product.getProduct_id());
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

        commissionService.delete(commissionDeleteList, product.getProduct_id(), request_user_id);
        commissionService.save(commissionSaveList, request_user_id);

        return productDao.update(product, request_user_id);
    }

    public boolean delete(Product product, String request_user_id) {
        skuService.deleteByProduct_id(product.getProduct_id(), request_user_id);

        commissionService.deleteByProduct_id(product.getProduct_id(), request_user_id);

        return productDao.delete(product.getProduct_id(), request_user_id);
    }

}