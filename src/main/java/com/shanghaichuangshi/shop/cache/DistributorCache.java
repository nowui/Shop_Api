package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Distributor;

public class DistributorCache extends Cache {

    private final String DISTRIBUTOR_OBJECT_CACHE = "distributor_object_cache";

    public Distributor getDistributorByDistributor_id(String distributor_id) {
        return (Distributor) getObjectBykeyAndId(DISTRIBUTOR_OBJECT_CACHE, distributor_id);
    }

    public void setDistributorByDistributor_id(Distributor distributor, String distributor_id) {
        setObjectBykeyAndId(distributor, DISTRIBUTOR_OBJECT_CACHE, distributor_id);
    }

    public void removeDistributorByDistributor_id(String distributor_id) {
        removeObjectBykeyAndId(DISTRIBUTOR_OBJECT_CACHE, distributor_id);
    }

    public void removeDistributor() {
        removeObjectByKey(DISTRIBUTOR_OBJECT_CACHE);
    }

}
