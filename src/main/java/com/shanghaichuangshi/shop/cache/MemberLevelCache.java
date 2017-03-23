package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.MemberLevel;

public class MemberLevelCache extends Cache {

    private final String MEMBER_LEVEL_OBJECT_CACHE = "member_level_object_cache";

    public MemberLevel getMemberLevelByMember_level_id(String member_level_id) {
        return (MemberLevel) getObjectBykeyAndId(MEMBER_LEVEL_OBJECT_CACHE, member_level_id);
    }

    public void setMemberLevelByMember_level_id(MemberLevel memberLevel, String member_level_id) {
        setObjectBykeyAndId(memberLevel, MEMBER_LEVEL_OBJECT_CACHE, member_level_id);
    }

    public void removeMemberLevelByMember_level_id(String member_level_id) {
        removeObjectBykeyAndId(MEMBER_LEVEL_OBJECT_CACHE, member_level_id);
    }

    public void removeMemberLevel() {
        removeObjectByKey(MEMBER_LEVEL_OBJECT_CACHE);
    }

}
