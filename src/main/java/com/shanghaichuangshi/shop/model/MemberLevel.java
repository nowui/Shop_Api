package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnTypeEnum;

public class MemberLevel extends Model<MemberLevel> {

    @Column(type = ColumnTypeEnum.VARCHAR, length = 32, comment = "会员等级编号")
    public static final String MEMBER_LEVEL_ID = "member_level_id";

    @Column(type = ColumnTypeEnum.VARCHAR, length = 10, comment = "会员等级名称")
    public static final String MEMBER_LEVEL_NAME = "member_level_name";

    @Column(type = ColumnTypeEnum.INT, length = 3, comment = "会员等级数值")
    public static final String MEMBER_LEVEL_VALUE = "member_level_value";

    @Column(type = ColumnTypeEnum.INT, length = 3, comment = "会员等级排序", findable = false)
    public static final String MEMBER_LEVEL_SORT = "member_level_sort";

    public static final String MEMBER_LEVEL_LIST = "member_level_list";
    
    public String getMember_level_id() {
        return getStr(MEMBER_LEVEL_ID);
    }

    public void setMember_level_id(String member_level_id) {
        set(MEMBER_LEVEL_ID, member_level_id);
    }

    public String getMember_level_name() {
        return getStr(MEMBER_LEVEL_NAME);
    }

    public void setMember_level_name(String member_level_name) {
        set(MEMBER_LEVEL_NAME, member_level_name);
    }

    public Integer getMember_level_value() {
        return getInt(MEMBER_LEVEL_VALUE);
    }

    public void setMember_level_value(Integer member_level_value) {
        set(MEMBER_LEVEL_VALUE, member_level_value);
    }

    public Integer getMember_level_sort() {
        return getInt(MEMBER_LEVEL_SORT);
    }

    public void setMember_level_sort(Integer member_level_sort) {
        set(MEMBER_LEVEL_SORT, member_level_sort);
    }
}