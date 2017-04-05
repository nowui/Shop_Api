package com.shanghaichuangshi.shop.cache;

import com.shanghaichuangshi.cache.Cache;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.model.MemberLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MemberLevelCache extends Cache {

    private final String MEMBER_LEVEL_OBJECT_CACHE = "member_level_object_cache";

    public List<MemberLevel> getMemberLevelList() {
        List<MemberLevel> memberLevelList = new ArrayList<MemberLevel>();

        Set<String> set = getMapByKey(MEMBER_LEVEL_OBJECT_CACHE);

        for (String id : set) {
            id = id.replace(MEMBER_LEVEL_OBJECT_CACHE + "_", "");

            memberLevelList.add(getMemberLevelByMember_level_id(id));
        }

        return memberLevelList;
    }

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
