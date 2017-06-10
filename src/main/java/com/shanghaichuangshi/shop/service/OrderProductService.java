package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.cache.CategoryCache;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.model.Category;
import com.shanghaichuangshi.shop.cache.*;
import com.shanghaichuangshi.shop.model.*;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.type.IncomeTypeEnum;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderProductService extends Service {

    private final OrderProductCache orderProductCache = new OrderProductCache();
    private final SkuCache skuCache = new SkuCache();
    private final ProductCache productCache = new ProductCache();
    private final CategoryCache categoryCache = new CategoryCache();
    private final BrandCache brandCache = new BrandCache();
    private final CommissionCache commissionCache = new CommissionCache();
    private final MemberCache memberCache = new MemberCache();
    private final MemberLevelCache memberLevelCache = new MemberLevelCache();

    public List<OrderProduct> listByOder_id(String order_id) {
        return orderProductCache.listByOder_id(order_id);
    }

    public List<OrderProduct> listByUser_id(String user_id) {
        return orderProductCache.listByUser_id(user_id);
    }

    public OrderProduct find(String order_product_id) {
        return orderProductCache.find(order_product_id);
    }

    public void save(JSONArray productListJSONArray, String order_id, String member_id, String parent_path, String member_level_id, String request_user_id) {
        List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

        JSONArray fatherMemberJSONArray = new JSONArray();
        JSONArray parentPathArray = new JSONArray();
        BigDecimal order_product_amount = BigDecimal.valueOf(0);

        if (!Util.isNullOrEmpty(parent_path)) {
            parentPathArray = JSONArray.parseArray(parent_path);
        }

        //上级信息
        for (int i = 0; i < parentPathArray.size(); i++) {
            String m_id = parentPathArray.getString(i);

            if (!m_id.equals(Constant.PARENT_ID)) {
                Member m = memberCache.find(m_id);
                MemberLevel mLevel = memberLevelCache.find(m.getMember_level_id());

                JSONObject mObject = new JSONObject();
                mObject.put(Member.MEMBER_ID, m_id);
                mObject.put(Member.MEMBER_NAME, m.getMember_name());
                mObject.put(MemberLevel.MEMBER_LEVEL_ID, mLevel.getMember_level_id());
                mObject.put(MemberLevel.MEMBER_LEVEL_NAME, mLevel.getMember_level_name());

                if (WeChat.income == IncomeTypeEnum.COMMISSION.getKey() || (WeChat.income == IncomeTypeEnum.SALE.getKey() && i == parentPathArray.size() - 1)) {
                    fatherMemberJSONArray.add(mObject);
                }
            }
        }

        for (int i = 0; i < productListJSONArray.size(); i++) {
            JSONObject productJSONObject = productListJSONArray.getJSONObject(i);

            String order_product_id = Util.getRandomUUID();
            String sku_id = productJSONObject.getString(Sku.SKU_ID);
            Integer product_quantity = productJSONObject.getInteger(Product.PRODUCT_QUANTITY);

            Sku sku = skuCache.find(sku_id);

            if (sku == null) {
                throw new RuntimeException("SKU不存在:" + sku_id);
            }

            Product product = productCache.find(sku.getProduct_id());

            //更新商品应付价格
            JSONObject productPriceJSONObject = skuCache.findProduct_price(sku, member_level_id);
            BigDecimal product_price = productPriceJSONObject.getBigDecimal(Product.PRODUCT_PRICE);
            order_product_amount = order_product_amount.add(product_price.multiply(BigDecimal.valueOf(product_quantity)));

            //订单的商品
            Category category = categoryCache.find(product.getCategory_id());
            Brand brand = brandCache.find(product.getBrand_id());

            //佣金
            String commission_id = "";
            if (WeChat.income == IncomeTypeEnum.COMMISSION.getKey()) {
                Commission commission = null;
                List<Commission> commissionList = commissionCache.list(product.getProduct_id());
                for (Commission c : commissionList) {
                    if (c.getProduct_attribute().equals(sku.getProduct_attribute())) {
                        commission_id = c.getCommission_id();

                        commission = c;
                    }
                }
                if (commission_id != "") {
                    JSONArray productCommissionJsonArray = JSONArray.parseArray(commission.getProduct_commission());

                    for (int j = 0; j < fatherMemberJSONArray.size(); j++) {
                        JSONObject fatherMemberJSONObject = fatherMemberJSONArray.getJSONObject(j);
                        String father_member_level_id = fatherMemberJSONObject.getString(MemberLevel.MEMBER_LEVEL_ID);

                        for (int k = 0; k < productCommissionJsonArray.size(); k++) {
                            JSONObject productCommissionJsonObject = productCommissionJsonArray.getJSONObject(k);

                            if (father_member_level_id.equals(productCommissionJsonObject.getString(MemberLevel.MEMBER_LEVEL_ID))) {
                                BigDecimal product_commission = productCommissionJsonObject.getBigDecimal(Commission.PRODUCT_COMMISSION);
                                BigDecimal commission_amount = order_product_amount.multiply(product_commission).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);

                                fatherMemberJSONObject.put(Commission.COMMISSION_AMOUNT, commission_amount);
                                fatherMemberJSONObject.put(Commission.PRODUCT_COMMISSION, product_commission);

                                break;
                            }
                        }
                    }
                }
            } else {
                for (int j = 0; j < fatherMemberJSONArray.size(); j++) {
                    JSONObject fatherMemberJSONObject = fatherMemberJSONArray.getJSONObject(j);
                    BigDecimal commission_amount = order_product_amount;

                    fatherMemberJSONObject.put(Commission.COMMISSION_AMOUNT, commission_amount);
                    fatherMemberJSONObject.put(Commission.PRODUCT_COMMISSION, "100");
                }
            }


            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder_id(order_id);
            orderProduct.setOrder_product_id(order_product_id);
            orderProduct.setOrder_status(false);
            orderProduct.setProduct_id(product.getProduct_id());
            orderProduct.setCategory_id(category.getCategory_id());
            orderProduct.setCategory_name(category.getCategory_name());
            orderProduct.setBrand_id(brand.getBrand_id());
            orderProduct.setBrand_name(brand.getBrand_name());
            orderProduct.setProduct_name(product.getProduct_name());
            orderProduct.setProduct_image(product.getProduct_image());
            orderProduct.setProduct_image_list(product.getProduct_image_list());
            orderProduct.setProduct_is_new(product.getProduct_is_new());
            orderProduct.setProduct_is_recommend(product.getProduct_is_recommend());
            orderProduct.setProduct_is_bargain(product.getProduct_is_bargain());
            orderProduct.setProduct_is_hot(product.getProduct_is_hot());
            orderProduct.setProduct_is_sale(product.getProduct_is_sale());
            orderProduct.setProduct_content(product.getProduct_content());
            orderProduct.setSku_id(sku_id);
            orderProduct.setUser_id(request_user_id);
            orderProduct.setMember_id(member_id);
            orderProduct.setCommission_id(commission_id);
            orderProduct.setOrder_product_commission(fatherMemberJSONArray.toJSONString());
            orderProduct.setProduct_attribute(sku.getProduct_attribute());
            orderProduct.setProduct_market_price(product.getProduct_market_price());
            orderProduct.setProduct_price(product.getProduct_price());
            orderProduct.setProduct_stock(product.getProduct_stock());
            orderProduct.setOrder_product_price(product_price);
            orderProduct.setOrder_product_quantity(product_quantity);
            orderProductList.add(orderProduct);
        }

        orderProductCache.save(orderProductList, request_user_id);
    }

    public boolean updateByOrder_idAndOrder_statusAndUser_id(String order_id, Boolean order_status, String user_id) {
        return orderProductCache.updateByOrder_idAndOrder_statusAndUser_id(order_id, order_status, user_id);
    }

}