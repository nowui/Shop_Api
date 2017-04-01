package com.shanghaichuangshi.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PathKit;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Product;
import com.shanghaichuangshi.shop.service.ProductService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ProductController extends Controller {

    private final ProductService productService = new ProductService();

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

        List<String> list = new ArrayList<String>();
        List<JSONObject> provinceList = new ArrayList<JSONObject>();
        String provinceValue = "";
        String provinceLabel = "";
        String cityValue = "";
        String cityLabel = "";
        List<JSONObject> cityList = new ArrayList<JSONObject>();
        String areaValue = "";
        String areaLabel = "";
        List<JSONObject> areaList = new ArrayList<JSONObject>();

        try {
            String encoding = "UTF-8";
            File file = new File(PathKit.getWebRootPath() + "/WEB-INF/classes/china.txt");
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        for (String lineTxt : list) {
            if (lineTxt.contains("province")) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", lineTxt.split("province")[0]);
                jsonObject.put("label", lineTxt.split("province")[1]);
                provinceList.add(jsonObject);
            }

            if (lineTxt.contains("city")) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", lineTxt.split("city")[0]);
                jsonObject.put("label", lineTxt.split("city")[1]);
                cityList.add(jsonObject);
            }

            if (lineTxt.contains("area")) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value", lineTxt.split("area")[0]);
                jsonObject.put("label", lineTxt.split("area")[1]);
                areaList.add(jsonObject);
            }
        }

        for (JSONObject city : cityList) {
            List<JSONObject> children = new ArrayList<JSONObject>();

            for (JSONObject area : areaList) {
                if (city.getString("value").substring(0, 4).equals(area.getString("value").substring(0, 4))) {
                    if (!area.getString("label").equals("市辖区")) {
                        children.add(area);
                    }
                }
            }

            city.put("children", children);
        }

        for (JSONObject province : provinceList) {
            List<JSONObject> children = new ArrayList<JSONObject>();

            for (JSONObject city : cityList) {
                if (province.getString("value").substring(0, 2).equals(city.getString("value").substring(0, 2))) {
                    if (city.getString("label").equals("市辖区")) {
                        city.put("label", province.getString("label"));
                    }

                    children.add(city);
                }
            }

            province.put("children", children);
        }

//        int provinceInt = 0;
//        int cityInt = 0;
//        for (int i = 0; i < list.size(); i++) {
//            String lineTxt = list.get(i);
//
//            if (lineTxt.contains("province")) {
//                provinceValue = lineTxt.split("province")[0];
//                provinceLabel = lineTxt.split("province")[1];
//
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("value", provinceValue);
//                jsonObject.put("label", provinceLabel.replace("市", ""));
//                provinceList.add(jsonObject);
//
//                if (i > 0) {
//                    JSONObject lastJsonObject = provinceList.get(provinceInt);
//                    lastJsonObject.put("children", cityList);
//
//                    cityList = new ArrayList<JSONObject>();
//                    cityInt = 0;
//
//                    provinceInt++;
//                }
//            }
//
//            if (lineTxt.contains("city")) {
//                cityValue = lineTxt.split("city")[0];
//                cityLabel = lineTxt.split("city")[1];
//
//                if (cityLabel.equals("市辖区")) {
//                    cityLabel = provinceLabel;
//                }
//
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("value", cityValue);
//                jsonObject.put("label", cityLabel);
//                cityList.add(jsonObject);
//
//                System.out.println(cityInt + "-" + cityList.size());
//
//                if (i > 0) {
//                    JSONObject lastJsonObject = cityList.get(cityInt);
//                    lastJsonObject.put("children", areaList);
//
//                    areaList = new ArrayList<JSONObject>();
//                }
//
//                cityInt++;
//            }
//
//            if (lineTxt.contains("area")) {
//                areaValue = lineTxt.split("area")[0];
//                areaLabel = lineTxt.split("area")[1];
//
//                if (!areaLabel.equals("市辖区")) {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("value", areaValue);
//                    jsonObject.put("label", areaLabel);
//                    areaList.add(jsonObject);
//                }
//            }
//
//            if (i + 1 == list.size()) {
//                JSONObject lastJsonObject = provinceList.get(provinceInt);
//                lastJsonObject.put("children", cityList);
//            }
//        }

        System.out.println(JSONArray.toJSON(provinceList));
        System.out.println("+++++++++++++++++++++");

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
            for (Product product : productList) {
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

        renderSuccessJson(product.formatToMap());
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