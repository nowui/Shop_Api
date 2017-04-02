package com.shanghaichuangshi.shop.model;

import com.shanghaichuangshi.annotation.Column;
import com.shanghaichuangshi.model.Model;
import com.shanghaichuangshi.type.ColumnType;

public class Distributor extends Model<Distributor> {

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "经销商编号")
    public static final String DISTRIBUTOR_ID = "distributor_id";

    @Column(type = ColumnType.VARCHAR, length = 20, comment = "经销商名称")
    public static final String DISTRIBUTOR_NAME = "distributor_name";

    @Column(type = ColumnType.VARCHAR, length = 32, comment = "用户编号")
    public static final String USER_ID = "user_id";

    
    public String getDistributor_id() {
        return getStr(DISTRIBUTOR_ID);
    }

    public void setDistributor_id(String distributor_id) {
        set(DISTRIBUTOR_ID, distributor_id);
    }

    public String getDistributor_name() {
        return getStr(DISTRIBUTOR_NAME);
    }

    public void setDistributor_name(String distributor_name) {
        set(DISTRIBUTOR_NAME, distributor_name);
    }

    public String getUser_id() {
        return getStr(USER_ID);
    }

    public void setUser_id(String user_id) {
        set(USER_ID, user_id);
    }
}