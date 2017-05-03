package com.shanghaichuangshi.shop.dao;

import com.jfinal.kit.JMap;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.dao.Dao;
import com.shanghaichuangshi.shop.model.ProductFile;
import com.shanghaichuangshi.util.CacheUtil;
import com.shanghaichuangshi.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductFileDao extends Dao {

    private final String PRODUCT_FILE_LIST_CACHE = "product_file_list_cache";

    public List<ProductFile> list(String product_id) {
        List<ProductFile> productFileList = CacheUtil.get(PRODUCT_FILE_LIST_CACHE, product_id);

        if (productFileList == null) {
            JMap map = JMap.create();
            map.put(ProductFile.PRODUCT_ID, product_id);
            SqlPara sqlPara = Db.getSqlPara("product_file.list", map);

            productFileList = new ProductFile().find(sqlPara.getSql(), sqlPara.getPara());

            if (productFileList.size() > 0) {
                CacheUtil.put(PRODUCT_FILE_LIST_CACHE, product_id, productFileList);
            }
        }

        return productFileList;
    }

    public ProductFile find(String product_file_id) {
        JMap map = JMap.create();
        map.put(ProductFile.PRODUCT_FILE_ID, product_file_id);
        SqlPara sqlPara = Db.getSqlPara("product_file.find", map);

        List<ProductFile> product_fileList = new ProductFile().find(sqlPara.getSql(), sqlPara.getPara());
        if (product_fileList.size() == 0) {
            return null;
        } else {
            return product_fileList.get(0);
        }
    }

    public void save(List<ProductFile> productFileList, String request_user_id) {
        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("product_file.save", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(ProductFile productFile : productFileList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(Util.getRandomUUID());
            objectList.add(productFile.getProduct_id());
            objectList.add(productFile.getProduct_file_type());
            objectList.add(productFile.getProduct_file_name());
            objectList.add(productFile.getProduct_file_path());
            objectList.add(productFile.getProduct_file_thumbnail_path());
            objectList.add(productFile.getProduct_file_original_path());
            objectList.add(productFile.getProduct_file_remark());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(true);
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("save unsuccessful");
            }
        }
    }

    public void delete(List<ProductFile> productFileList, String product_id, String request_user_id) {
        CacheUtil.remove(PRODUCT_FILE_LIST_CACHE, product_id);

        JMap map = JMap.create();
        SqlPara sqlPara = Db.getSqlPara("product_file.delete", map);

        List<Object[]> parameterList = new ArrayList<Object[]>();
        for(ProductFile productFile : productFileList) {
            List<Object> objectList = new ArrayList<Object>();
            objectList.add(request_user_id);
            objectList.add(new Date());
            objectList.add(productFile.getProduct_file_id());
            parameterList.add(objectList.toArray());
        }

        int[] result = Db.batch(sqlPara.getSql(), Util.getObjectArray(parameterList), Constant.BATCH_SIZE);

        for (int i : result) {
            if (i == 0) {
                throw new RuntimeException("delete unsuccessful");
            }
        }
    }

}