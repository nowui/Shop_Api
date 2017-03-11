package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.MemberLevel;

public class MemberLevelCache extends Cache {

    private final String MEMBER_LEVEL_CACHE_MODEL = "member_level_cache_model";

    public MemberLevel getMemberLevelByMember_level_id(String member_level_id) {
        return (MemberLevel) ehcacheObject.get(MEMBER_LEVEL_CACHE_MODEL + "_" + member_level_id);
    }

    public void setMemberLevelByMember_level_id(MemberLevel memberLevel, String member_level_id) {
        ehcacheObject.put(MEMBER_LEVEL_CACHE_MODEL + "_" + member_level_id, memberLevel);

        setMapByKeyAndId(MEMBER_LEVEL_CACHE_MODEL, member_level_id);
    }

    public void removeMemberLevelByMember_level_id(String member_level_id) {
        ehcacheObject.remove(MEMBER_LEVEL_CACHE_MODEL + "_" + member_level_id);

        removeMapByKeyAndId(MEMBER_LEVEL_CACHE_MODEL, member_level_id);
    }

    public void removeMemberLevel() {
        ehcacheObject.removeAll(getMapByKey(MEMBER_LEVEL_CACHE_MODEL));

        removeMapByKey(MEMBER_LEVEL_CACHE_MODEL);
    }

}
