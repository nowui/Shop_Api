package com.shanghaichuangshi.shop;

import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.shop.controller.*;
import com.shanghaichuangshi.shop.model.*;

import java.util.List;

public class WebConfig {

    public static Routes configRoute(Routes routes) {
        routes = com.shanghaichuangshi.WebConfig.configRoute(routes);

        routes.add("/member", MemberController.class);
        routes.add("/member/level", MemberLevelController.class);
        routes.add("/brand", BrandController.class);
        routes.add("/product", ProductController.class);
        routes.add("/delivery", DeliveryController.class);
        routes.add("/order", OrderController.class);
        routes.add("/wechat/api", WeChatApiController.class);
        routes.add("/supplier", SupplierController.class);
        routes.add("/scene", SceneController.class);
        routes.add("/bill", BillController.class);
        routes.add("/cart", CartController.class);
        routes.add("/express", ExpressController.class);

        return routes;
    }

    public static ActiveRecordPlugin configActiveRecordPlugin(ActiveRecordPlugin activeRecordPlugin) {
        activeRecordPlugin = com.shanghaichuangshi.WebConfig.configActiveRecordPlugin(activeRecordPlugin);

        activeRecordPlugin.addMapping("table_member", "member_id", Member.class);
        activeRecordPlugin.addMapping("table_member_level", "member_level_id", MemberLevel.class);
        activeRecordPlugin.addMapping("table_brand", "brand_id", Brand.class);
        activeRecordPlugin.addMapping("table_product", "product_id", Product.class);
        activeRecordPlugin.addMapping("table_delivery", "delivery_id", Delivery.class);
        activeRecordPlugin.addMapping("table_sku", "sku_id", Sku.class);
        activeRecordPlugin.addMapping("table_order", "order_id", Order.class);
        activeRecordPlugin.addMapping("table_order_product", "order_product_id", OrderProduct.class);
        activeRecordPlugin.addMapping("table_supplier", "supplier_id", Supplier.class);
        activeRecordPlugin.addMapping("table_scene", "scene_id", Scene.class);
        activeRecordPlugin.addMapping("table_commission", "commission_id", Commission.class);
        activeRecordPlugin.addMapping("table_bill", "bill_id", Bill.class);
        activeRecordPlugin.addMapping("table_cart", "cart_id", Cart.class);
        activeRecordPlugin.addMapping("table_express", "express_id", Express.class);

        return activeRecordPlugin;
    }

    public static List<String> configUncheckUrlList(List<String> uncheckUrlList) {
        uncheckUrlList.add(Url.WECHAT_API_MENU);
        uncheckUrlList.add(Url.WECHAT_API_AUTH);
        uncheckUrlList.add(Url.WECHAT_API_LOGIN);
        uncheckUrlList.add(Url.WECHAT_API_ORCODE);
        uncheckUrlList.add(Url.WECHAT_API_NOTIFY);
        uncheckUrlList.add(Url.WECHAT_SHARE);
        uncheckUrlList.add(Url.WECHAT_MESSAGE);

        return uncheckUrlList;
    }

    public static List<String> configUncheckTokenUrlList(List<String> uncheckTokenUrlList) {
        uncheckTokenUrlList.add(Url.MEMBER_WECHAT_WX_LOGIN);
        uncheckTokenUrlList.add(Url.MEMBER_LOGIN);

        return uncheckTokenUrlList;
    }

    public static List<String> configUncheckRequestUserIdUrlList(List<String> uncheckRequestUserIdUrlList) {
        uncheckRequestUserIdUrlList.add(Url.PRODUCT_LIST);
        uncheckRequestUserIdUrlList.add(Url.PRODUCT_ALL_LIST);
        uncheckRequestUserIdUrlList.add(Url.PRODUCT_HOT_LIST);
        uncheckRequestUserIdUrlList.add(Url.PRODUCT_FIND);

        return uncheckRequestUserIdUrlList;
    }

    public static List<String> configUncheckParameterUrlList(List<String> uncheckParameterUrlList) {

        return uncheckParameterUrlList;
    }

    public static List<String> configUncheckHeaderUrlList(List<String> uncheckHeaderUrlList) {

        return uncheckHeaderUrlList;
    }

}
