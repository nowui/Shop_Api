package com.shanghaichuangshi.shop.controller;

import com.shanghaichuangshi.annotation.Path;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Brand;
import com.shanghaichuangshi.shop.service.BrandService;

import java.util.List;

public class BrandController extends Controller {

    private final BrandService brandService = new BrandService();

    @Path(Url.BRAND_LIST)
    public void list() {
        Brand brandModel = getModel(Brand.class);

        brandModel.validate(Brand.BRAND_NAME, Brand.PAGE_INDEX, Brand.PAGE_SIZE);

        List<Brand> brandList = brandService.list(brandModel);

        renderJson(brandList);
    }

    @Path(Url.BRAND_ADMIN_LIST)
    public void adminList() {
        Brand brandModel = getModel(Brand.class);

        brandModel.validate(Brand.PAGE_INDEX, Brand.PAGE_SIZE);

        int count = brandService.count(brandModel);

        List<Brand> brandList = brandService.list(brandModel);

        renderJson(count, brandList);
    }

    @Path(Url.BRAND_FIND)
    public void find() {
        Brand brandModel = getModel(Brand.class);

        brandModel.validate(Brand.BRAND_ID);

        Brand brand = brandService.find(brandModel);

        brand.removeUnfindable();

        renderJson(brand);
    }

    @Path(Url.BRAND_ADMIN_FIND)
    public void adminFind() {
        Brand brandModel = getModel(Brand.class);

        brandModel.validate(Brand.BRAND_ID);

        Brand brand = brandService.find(brandModel);

        renderJson(brand);
    }

    @Path(Url.BRAND_SAVE)
    public void save() {
        Brand brandModel = getModel(Brand.class);

        brandModel.validate(Brand.BRAND_NAME);

        brandService.save(brandModel);

        renderJson("");
    }

    @Path(Url.BRANDL_UPDATE)
    public void update() {
        Brand brandModel = getModel(Brand.class);

        brandModel.validate(Brand.BRAND_ID, Brand.BRAND_NAME);

        brandService.update(brandModel);

        renderJson("");
    }

    @Path(Url.BRAND_DELETE)
    public void delete() {
        Brand brandModel = getModel(Brand.class);

        brandModel.validate(Brand.BRAND_ID);

        brandService.delete(brandModel);

        renderJson("");
    }

}