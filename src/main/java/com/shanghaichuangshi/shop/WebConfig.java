package com.shanghaichuangshi.shop;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.jfinal.config.*;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.shanghaichuangshi.constant.Jdbc;
import com.shanghaichuangshi.controller.*;
import com.shanghaichuangshi.interceptor.GlobalActionInterceptor;
import com.shanghaichuangshi.model.*;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.shop.controller.*;
import com.shanghaichuangshi.shop.model.*;

import java.util.ArrayList;
import java.util.List;

public class WebConfig extends JFinalConfig {

    public void configConstant(Constants constants) {
        constants.setDevMode(false);
        constants.setViewType(ViewType.JSP);
        constants.setError404View("/error.jsp");
    }

    public void configRoute(Routes routes) {
        routes.add("/admin", AdminController.class);
        routes.add("/authorization", AuthorizationController.class);
        routes.add("/log", LogController.class);
        routes.add("/category", CategoryController.class);
        routes.add("/code", CodeController.class);
        routes.add("/attribute", AttributeController.class);
        routes.add("/resource", ResourceController.class);
        routes.add("/role", RoleController.class);
        routes.add("/upload", UploadController.class);
        routes.add("/file", FileController.class);

        routes.add("/member", MemberController.class);
        routes.add("/brand", BrandController.class);
        routes.add("/product", ProductController.class);
        routes.add("/delivery", DeliveryController.class);
        routes.add("/order", OrderController.class);
        routes.add("/wechat/message", WeChatMsgController.class);
        routes.add("/wechat/api", WeChatApiController.class);

    }

    public void configEngine(Engine engine) {

    }

    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin = new DruidPlugin(Jdbc.jdbc_url, Jdbc.user, Jdbc.password);
        druidPlugin.set(Jdbc.initial_size, Jdbc.min_idle, Jdbc.max_activee);
        druidPlugin.setFilters("stat,wall");
        plugins.add(druidPlugin);

        Slf4jLogFilter sql_log_filter = new Slf4jLogFilter();
        sql_log_filter.setConnectionLogEnabled(false);
        sql_log_filter.setStatementLogEnabled(false);
        sql_log_filter.setStatementExecutableSqlLogEnable(true);
        sql_log_filter.setResultSetLogEnabled(false);
        druidPlugin.addFilter(sql_log_filter);

        String baseSqlTemplatePath = PathKit.getWebRootPath() + "/WEB-INF/sql/";

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
        activeRecordPlugin.setBaseSqlTemplatePath(baseSqlTemplatePath);

        java.io.File[] files = new java.io.File(baseSqlTemplatePath).listFiles();
        for (java.io.File file : files) {
            if (file.isFile() || file.getName().endsWith(".sql")) {
                activeRecordPlugin.addSqlTemplate(file.getName());
            }
        }

        activeRecordPlugin.addMapping("table_admin", "admin_id", Admin.class);
        activeRecordPlugin.addMapping("table_authorization", "authorization_id", Authorization.class);
        activeRecordPlugin.addMapping("table_category", "category_id", Category.class);
        activeRecordPlugin.addMapping("table_log", "log_id", Log.class);
        activeRecordPlugin.addMapping("table_user", "user_id", User.class);
        activeRecordPlugin.addMapping("table_file", "file_id", File.class);
        activeRecordPlugin.addMapping("table_attribute", "attribute_id", Attribute.class);
        activeRecordPlugin.addMapping("table_resource", "resource_id", Resource.class);
        activeRecordPlugin.addMapping("table_role", "role_id", Role.class);

        activeRecordPlugin.addMapping("table_member", "member_id", Member.class);
        activeRecordPlugin.addMapping("table_member_level", "member_level_id", MemberLevel.class);
        activeRecordPlugin.addMapping("table_brand", "brand_id", Brand.class);
        activeRecordPlugin.addMapping("table_product", "product_id", Product.class);
        activeRecordPlugin.addMapping("table_delivery", "delivery_id", Delivery.class);
        activeRecordPlugin.addMapping("table_sku", "sku_id", Sku.class);
        activeRecordPlugin.addMapping("table_order", "order_id", Order.class);
        activeRecordPlugin.addMapping("table_order_product", "order_product_id", OrderProduct.class);

        plugins.add(activeRecordPlugin);
    }

    public void configInterceptor(Interceptors interceptors) {
        List<String> uncheckUrlList = new ArrayList<String>();
        uncheckUrlList.add(Url.WECHAT_API_MENU);
        uncheckUrlList.add(Url.WECHAT_API_AUTH);
        uncheckUrlList.add(Url.WECHAT_API_ORCODE);


        List<String> uncheckTokenUrlList = new ArrayList<String>();
        uncheckTokenUrlList.add(Url.PRODUCT_LIST);
        uncheckTokenUrlList.add(Url.PRODUCT_ALL_LIST);

        List<String> uncheckRequestUserIdUrlList = new ArrayList<String>();
        uncheckRequestUserIdUrlList.add(Url.PRODUCT_FIND);
        uncheckRequestUserIdUrlList.add(Url.MEMBER_LOGIN);

        List<String> uncheckParameterUrlList = new ArrayList<String>();

        List<String> uncheckHeaderUrlList = new ArrayList<String>();

        interceptors.addGlobalActionInterceptor(new GlobalActionInterceptor(uncheckUrlList, uncheckTokenUrlList, uncheckRequestUserIdUrlList, uncheckParameterUrlList, uncheckHeaderUrlList));
    }

    public void configHandler(Handlers handlers) {

    }

}