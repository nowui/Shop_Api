package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Order;

import java.util.List;

public class OrderCache extends Cache {

    private final String ORDER_CACHE_LIST = "order_cache_list";

    public List<String> getOrderNumberListByDay(String day) {
        return ehcacheList.get(ORDER_CACHE_LIST + "_" + day);
    }

    public void setOrderNumberListByDay(List<String> list, String day) {
        ehcacheList.put(ORDER_CACHE_LIST + "_" + day, list);

        setMapByKeyAndId(ORDER_CACHE_LIST, day);
    }

    public void removeOrderNumberListByDay(String day) {
        ehcacheList.remove(ORDER_CACHE_LIST + "_" + day);

        removeMapByKeyAndId(ORDER_CACHE_LIST, day);
    }

    public void removeOrderNumberList() {
        ehcacheList.removeAll(getMapByKey(ORDER_CACHE_LIST));

        removeMapByKey(ORDER_CACHE_LIST);
    }

}
