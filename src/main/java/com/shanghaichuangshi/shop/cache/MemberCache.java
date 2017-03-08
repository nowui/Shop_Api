package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Member;

public class MemberCache extends Cache {

    private final String MEMBER_CACHE_MODEL = "member_cache_model";

    public Member getMemberByMember_id(String member_id) {
        return (Member) ehcacheObject.get(MEMBER_CACHE_MODEL + "_" + member_id);
    }

    public void setMemberByMember_id(Member member, String member_id) {
        ehcacheObject.put(MEMBER_CACHE_MODEL + "_" + member_id, member);

        setMapByKeyAndId(MEMBER_CACHE_MODEL, member_id);
    }

    public void removeMemberByMember_id(String member_id) {
        ehcacheObject.remove(MEMBER_CACHE_MODEL + "_" + member_id);

        removeMapByKeyAndId(MEMBER_CACHE_MODEL, member_id);
    }

    public void removeMember() {
        ehcacheObject.removeAll(getMapByKey(MEMBER_CACHE_MODEL));

        removeMapByKey(MEMBER_CACHE_MODEL);
    }

}
