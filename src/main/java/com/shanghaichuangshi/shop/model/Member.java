package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Member extends Model<Member> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "会员编号")
    public static final String MEMBER_ID = "member_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "上一级会员编号")
    public static final String PARENT_ID = "parent_id";

    @Column(type = ColumnType.VARCHAR, length = 1000, comment = "上一级会员编号路径")
    public static final String PARENT_PATH = "parent_path";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "用户编号")
    public static final String USER_ID = "user_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "来源场景编号")
    public static final String FROM_SCENE_ID = "from_scene_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "场景编号")
    public static final String SCENE_ID = "scene_id";

    @Column(type = ColumnType.VARCHAR, length = 250, comment = "二维码")
    public static final String SCENE_QRCODE = "scene_qrcode";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "会员等级")
    public static final String MEMBER_LEVEL_ID = "member_level_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "会员名称")
    public static final String MEMBER_NAME = "member_name";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "会员电话")
    public static final String MEMBER_PHONE = "member_phone";

    @Column(type = ColumnType.VARCHAR, length = 250, comment = "会员备注")
    public static final String MEMBER_REMARK = "member_remark";

    public String getMember_id() {
        return getStr(MEMBER_ID);
    }

    public void setMember_id(String member_id) {
        set(MEMBER_ID, member_id);
    }

    public String getParent_id() {
        return getStr(PARENT_ID);
    }

    public void setParent_id(String parent_id) {
        set(PARENT_ID, parent_id);
    }

    public String getParent_path() {
        return getStr(PARENT_PATH);
    }

    public void setParent_path(String parent_path) {
        set(PARENT_PATH, parent_path);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }

    public String getFrom_scene_id() {
        return getStr(FROM_SCENE_ID);
    }

    public void setFrom_scene_id(String from_scene_id) {
        set(FROM_SCENE_ID, from_scene_id);
    }

    public String getScene_id() {
        return getStr(SCENE_ID);
    }

    public void setScene_id(String scene_id) {
        set(SCENE_ID, scene_id);
    }

    public String getScene_qrcode() {
        return getStr(SCENE_QRCODE);
    }

    public void setScene_qrcode(String scene_qrcode) {
        set(SCENE_QRCODE, scene_qrcode);
    }

    public String getMember_level_id() {
        return getStr(MEMBER_LEVEL_ID);
    }

    public void setMember_level_id(String member_level_id) {
        set(MEMBER_LEVEL_ID, member_level_id);
    }

    public String getMember_name() {
        return getStr(MEMBER_NAME);
    }

    public void setMember_name(String member_name) {
        set(MEMBER_NAME, member_name);
    }

    public String getMember_phone() {
        return getStr(MEMBER_PHONE);
    }

    public void setMember_phone(String member_phone) {
        set(MEMBER_PHONE, member_phone);
    }

    public String getMember_remark() {
        return getStr(MEMBER_REMARK);
    }

    public void setMember_remark(String member_remark) {
        set(MEMBER_REMARK, member_remark);
    }

}