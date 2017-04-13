package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Scene extends Model<Scene> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "场景编号")
    public static final String SCENE_ID = "scene_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "外键编号")
    public static final String OBJECT_ID = "object_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "场景类型")
    public static final String SCENE_TYPE = "scene_type";

    @Column(type = ColumnType.TINYINT, length = 1, comment = "是否临时")
    public static final String SCENE_IS_TEMPORARY = "scene_is_temporary";

    @Column(type = ColumnType.INT, length = 11, comment = "新增关注")
    public static final String SCENE_ADD = "scene_add";

    @Column(type = ColumnType.INT, length = 11, comment = "取消关注")
    public static final String SCENE_CANCEL = "scene_cancel";

    @Column(type = ColumnType.VARCHAR, length = 250, comment = "二维码")
    public static final String SCENE_QRCODE = "scene_qrcode";
    
    public String getScene_id() {
        return getStr(SCENE_ID);
    }

    public void setScene_id(String scene_id) {
        set(SCENE_ID, scene_id);
    }

    public String getObject_id() {
        return getStr(OBJECT_ID);
    }

    public void setObject_id(String object_id) {
        set(OBJECT_ID, object_id);
    }

    public String getScene_type() {
        return getStr(SCENE_TYPE);
    }

    public void setScene_type(String scene_type) {
        set(SCENE_TYPE, scene_type);
    }

    public Boolean getScene_is_temporary() {
        return getBoolean(SCENE_IS_TEMPORARY);
    }

    public void setScene_is_temporary(Boolean scene_is_temporary) {
        set(SCENE_IS_TEMPORARY, scene_is_temporary);
    }

    public Integer getScene_add() {
        return getInt(SCENE_ADD);
    }

    public void setScene_add(Integer scene_add) {
        set(SCENE_ADD, scene_add);
    }

    public Integer getScene_cancel() {
        return getInt(SCENE_CANCEL);
    }

    public void setScene_cancel(Integer scene_cancel) {
        set(SCENE_CANCEL, scene_cancel);
    }

    public String getScene_qrcode() {
        return getStr(SCENE_QRCODE);
    }

    public void setScene_qrcode(String scene_qrcode) {
        set(SCENE_QRCODE, scene_qrcode);
    }
}