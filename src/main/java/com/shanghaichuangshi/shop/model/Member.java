package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Member extends Model<Member> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "会员编号")
    public static final String MEMBER_ID = "member_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "用户编号")
    public static final String USER_ID = "user_id";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "会员等级")
    public static final String MEMBER_LEVEL_ID = "member_level_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "会员名称")
    public static final String MEMBER_NAME = "member_name";

    @Column(type = ColumnType.VARCHAR, length = 100, comment = "会员头像")
    public static final String MEMBER_AVATAR = "member_avatar";

    
    public String getMember_id() {
        return getStr(MEMBER_ID);
    }

    public void setMember_id(String member_id) {
        set(MEMBER_ID, member_id);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
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

    public String getMember_avatar() {
        return getStr(MEMBER_AVATAR);
    }

    public void setMember_avatar(String member_avatar) {
        set(MEMBER_AVATAR, member_avatar);
    }
}