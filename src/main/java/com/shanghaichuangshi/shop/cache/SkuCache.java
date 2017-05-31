package com.shanghaichuangshi.shop.cache;

import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.dao.SkuDao;
import com.shanghaichuangshi.shop.model.Sku;
import com.shanghaichuangshi.util.CacheUtil;

import java.util.List;

public class SkuCache extends Cache {

    private final String SKU_LIST_BY_PRODUCT_ID_CACHE = "sku_list_by_product_id_cache";
    private final String SKU_BY_SKU_ID_CACHE = "sku_by_sku_id_cache";

    private SkuDao skuDao = new SkuDao();

    public List<Sku> list(String product_id) {
        List<Sku> skuList = CacheUtil.get(SKU_LIST_BY_PRODUCT_ID_CACHE, product_id);

        if (skuList == null) {
            skuList = skuDao.list(product_id);

            CacheUtil.put(SKU_LIST_BY_PRODUCT_ID_CACHE, product_id, skuList);
        }
        return skuList;
    }

    public List<Sku> listByProduct_idAndMember_level_id(String product_id, String member_level_id) {
        return skuDao.listByProduct_idAndMember_level_id(product_id, member_level_id);
    }

    public Sku find(String sku_id) {
        Sku sku = CacheUtil.get(SKU_BY_SKU_ID_CACHE, sku_id);

        if (sku == null) {
            sku = skuDao.find(sku_id);

            CacheUtil.put(SKU_BY_SKU_ID_CACHE, sku_id, sku);
        }
        return sku;
    }

    public JSONObject findProduct_price(Sku sku, String member_level_id) {
        return skuDao.findProduct_price(sku, member_level_id);
    }

    public void save(List<Sku> skuList, String request_user_id) {
        skuDao.save(skuList, request_user_id);
    }

    public void updateProduct_stock(List<Sku> skuList, String product_id, String request_user_id) {
        List<Sku> skuListCache = CacheUtil.get(SKU_LIST_BY_PRODUCT_ID_CACHE, product_id);
        for(Sku sku : skuListCache) {
            CacheUtil.remove(SKU_BY_SKU_ID_CACHE, sku.getSku_id());
        }

        CacheUtil.remove(SKU_LIST_BY_PRODUCT_ID_CACHE, product_id);

        skuDao.updateProduct_stock(skuList, product_id, request_user_id);
    }

    public void delete(List<Sku> skuList, String product_id, String request_user_id) {
        List<Sku> skuListCache = CacheUtil.get(SKU_LIST_BY_PRODUCT_ID_CACHE, product_id);
        for(Sku sku : skuListCache) {
            CacheUtil.remove(SKU_BY_SKU_ID_CACHE, sku.getSku_id());
        }

        CacheUtil.remove(SKU_LIST_BY_PRODUCT_ID_CACHE, product_id);

        skuDao.delete(skuList, product_id, request_user_id);
    }

    public boolean deleteByProduct_id(String product_id, String request_user_id) {
        List<Sku> skuListCache = CacheUtil.get(SKU_LIST_BY_PRODUCT_ID_CACHE, product_id);

        if (skuListCache != null) {
            for(Sku sku : skuListCache) {
                CacheUtil.remove(SKU_BY_SKU_ID_CACHE, sku.getSku_id());
            }
        }

        CacheUtil.remove(SKU_LIST_BY_PRODUCT_ID_CACHE, product_id);

        return skuDao.deleteByProduct_id(product_id, request_user_id);
    }

}