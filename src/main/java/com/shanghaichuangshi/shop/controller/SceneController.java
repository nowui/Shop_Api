package com.shanghaichuangshi.shop.controller;

import com.jfinal.core.ActionKey;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.shop.constant.Url;
import com.shanghaichuangshi.controller.Controller;
import com.shanghaichuangshi.shop.model.Scene;
import com.shanghaichuangshi.shop.service.SceneService;

import java.util.List;

public class SceneController extends Controller {

    private final SceneService sceneService = new SceneService();

    @ActionKey(Url.SCENE_LIST)
    public void list() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Scene model = getParameter(Scene.class);

        model.validate(Scene.SCENE_TYPE);

        List<Scene> sceneListvice = sceneService.list(model, getM(), getN());

        renderSuccessJson(sceneListvice);
    }

    @ActionKey(Url.SCENE_ADMIN_LIST)
    public void adminList() {
        validate(Constant.PAGE_INDEX, Constant.PAGE_SIZE);

        Scene model = getParameter(Scene.class);

        model.validate(Scene.SCENE_TYPE);

        int count = sceneService.count(model);

        List<Scene> sceneListvice = sceneService.list(model, getM(), getN());

        renderSuccessJson(count, sceneListvice);
    }

    @ActionKey(Url.SCENE_FIND)
    public void find() {
        Scene model = getParameter(Scene.class);

        model.validate(Scene.SCENE_ID);

        Scene scene = sceneService.find(model.getScene_id());

        renderSuccessJson(scene.removeUnfindable());
    }

    @ActionKey(Url.SCENE_ADMIN_FIND)
    public void adminFind() {
        Scene model = getParameter(Scene.class);

        model.validate(Scene.SCENE_ID);

        Scene scene = sceneService.find(model.getScene_id());

        renderSuccessJson(scene);
    }

    @ActionKey(Url.SCENE_SAVE)
    public void save() {
        String request_user_id = getRequest_user_id();

        sceneService.companySave(request_user_id);

        renderSuccessJson();
    }

//    @ActionKey(Url.SCENEL_UPDATE)
//    public void update() {
//        Scene model = getParameter(Scene.class);
//        String request_user_id = getRequest_user_id();
//
//        model.validate(Scene.SCENE_ID, Scene.SCENE_TYPE);
//
//        sceneService.update(model, request_user_id);
//
//        renderSuccessJson();
//    }

    @ActionKey(Url.SCENE_DELETE)
    public void delete() {
        Scene model = getParameter(Scene.class);
        String request_user_id = getRequest_user_id();

        model.validate(Scene.SCENE_ID);

        sceneService.delete(model, request_user_id);

        renderSuccessJson();
    }

}