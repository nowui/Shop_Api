package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Brand;
import com.shanghaichuangshi.shop.service.BrandService;

import java.util.List;

public class BrandController extends Controller {

    private final BrandService brandService = new BrandService();

    @ActionKey(Url.BRAND_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Brand model = getParameter(Brand.class);

        model.validate(Brand.BRAND_NAME);

        List<Brand> brandList = brandService.list(model, getM(), getN());

        renderSuccessJson(brandList);
    }

    @ActionKey(Url.BRAND_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Brand model = getParameter(Brand.class);

        model.validate(Brand.BRAND_NAME);

        int count = brandService.count(model);

        List<Brand> brandList = brandService.list(model, getM(), getN());

        renderSuccessJson(count, brandList);
    }

    @ActionKey(Url.BRAND_CATEGORY_LIST)
    public void categoryList() {
        List<Brand> brandList = brandService.list(new Brand(), 0, 0);

        renderSuccessJson(brandList);
    }

    @ActionKey(Url.BRAND_FIND)
    public void find() {
        Brand model = getParameter(Brand.class);

        model.validate(Brand.BRAND_ID);

        Brand brand = brandService.find(model.getBrand_id());

        renderSuccessJson(brand.format());
    }

    @ActionKey(Url.BRAND_ADMIN_FIND)
    public void adminFind() {
        Brand model = getParameter(Brand.class);

        model.validate(Brand.BRAND_ID);

        Brand brand = brandService.find(model.getBrand_id());

        renderSuccessJson(brand);
    }

    @ActionKey(Url.BRAND_SAVE)
    public void save() {
        Brand model = getParameter(Brand.class);
        String request_user_id = getRequest_user_id();

        model.validate(Brand.BRAND_NAME);

        brandService.save(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.BRANDL_UPDATE)
    public void update() {
        Brand model = getParameter(Brand.class);
        String request_user_id = getRequest_user_id();

        model.validate(Brand.BRAND_ID, Brand.BRAND_NAME);

        brandService.update(model, request_user_id);

        renderSuccessJson();
    }

    @ActionKey(Url.BRAND_DELETE)
    public void delete() {
        Brand model = getParameter(Brand.class);
        String request_user_id = getRequest_user_id();

        model.validate(Brand.BRAND_ID);

        brandService.delete(model, request_user_id);

        renderSuccessJson();
    }

}