package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Member;

public class MemberCache extends Cache {

    private final String MEMBER_OBJECT_CACHE = "member_object_cache";

    public Member getMemberByMember_id(String member_id) {
        return (Member) getObjectBykeyAndId(MEMBER_OBJECT_CACHE, member_id);
    }

    public void setMemberByMember_id(Member member, String member_id) {
        setObjectBykeyAndId(member, MEMBER_OBJECT_CACHE, member_id);
    }

    public void removeMemberByMember_id(String member_id) {
        removeObjectBykeyAndId(MEMBER_OBJECT_CACHE, member_id);
    }

    public void removeMember() {
        removeObjectByKey(MEMBER_OBJECT_CACHE);
    }

}