package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.cache.AuthorizationCache;
import com.shanghaichuangshi.cache.UserCache;
import com.shanghaichuangshi.model.Authorization;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.shop.cache.MemberCache;
import com.shanghaichuangshi.shop.cache.MemberLevelCache;
import com.shanghaichuangshi.shop.cache.OrderCache;
import com.shanghaichuangshi.shop.cache.SceneCache;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.shop.type.SceneTypeEnum;
import com.shanghaichuangshi.type.UserType;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.*;

import static com.shanghaichuangshi.util.AesUtil.decrypt;

public class MemberService extends Service {

    private final MemberCache memberCache = new MemberCache();

    private final UserCache userCache = new UserCache();
    private final AuthorizationCache authorizationCache = new AuthorizationCache();
    private final MemberLevelCache memberLevelCache = new MemberLevelCache();
    private final SceneCache sceneCache = new SceneCache();
    private final OrderCache orderCache = new OrderCache();

    public int count(String member_name) {
        return memberCache.count(member_name);
    }

    public List<Member> list(String member_name, int m, int n) {
        List<Member> memberList = memberCache.list(member_name, m, n);

        for (Member item : memberList) {
            Member model = find(item.getMember_id());

            item.put(User.USER_AVATAR, model.get(User.USER_AVATAR));
        }

        return memberList;
    }

    public List<Member> treeList() {
        List<Member> memberList = memberCache.treeList();

        Iterator<Member> iterator = memberList.iterator();
        while (iterator.hasNext()) {
            Member member = iterator.next();

            User user = userCache.find(member.getUser_id());
            member.put(User.USER_AVATAR, user.getUser_avatar());
            member.remove(Member.USER_ID);

            MemberLevel memberLevel = memberLevelCache.find(member.getMember_level_id());
            if (memberLevel == null) {
                member.put(MemberLevel.MEMBER_LEVEL_NAME, "");
            } else {
                member.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
            }

            if (member.getParent_id().equals(Constant.PARENT_ID) && member.getMember_name().equals("Ronaldo")) {
                iterator.remove();
            }
        }

        List<Member> resultList = getChildren(memberList, Constant.PARENT_ID);

        return resultList;
    }

    public List<Member> teamList(String user_id) {
        List<Member> memberList = memberCache.teamList(userCache.find(user_id).getObject_id());

        for (Member item : memberList) {
            User user = userCache.find(item.getUser_id());
            item.put(User.USER_AVATAR, user.getUser_avatar());

            List<Order> orderList = orderCache.listByUser_id(item.getUser_id());
            BigDecimal member_month_order_amount = BigDecimal.ZERO;
            BigDecimal member_all_order_amount = BigDecimal.ZERO;
            for (Order order : orderList) {
                if (order.getOrder_status() && order.getOrder_is_pay()) {
                    member_month_order_amount = member_month_order_amount.add(order.getOrder_amount());
                    member_all_order_amount = member_all_order_amount.add(order.getOrder_amount());
                }
            }
            item.put(Member.MEMBER_TOTAL_AMOUNT, item.getMember_total_amount());
            item.put(Member.MEMBER_MONTH_ORDER_AMOUNT, member_month_order_amount);
            item.put(Member.MEMBER_ALL_ORDER_AMOUNT, member_all_order_amount);

            if (Util.isNullOrEmpty(item.getMember_level_id())) {
                item.put(MemberLevel.MEMBER_LEVEL_NAME, "");
            } else {
                MemberLevel memberLevel = memberLevelCache.find(item.getMember_level_id());
                item.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
            }
        }

        return memberList;
    }

    private List<Member> getChildren(List<Member> memberList, String parent_id) {
        List<Member> resultList = new ArrayList<Member>();

        for (Member member : memberList) {
            if (member.getParent_id().equals(parent_id)) {
                List<Member> childrenList = getChildren(memberList, member.getMember_id());
                if (childrenList.size() > 0) {
                    member.put(Constant.CHILDREN, childrenList);
                }

                resultList.add(member);
            }
        }

        return resultList;
    }

    public Member find(String member_id) {
        Member member = memberCache.find(member_id);

        User user = userCache.find(member.getUser_id());
        member.put(User.USER_AVATAR, user.getUser_avatar());

        return member;
    }

    public String qrcodeFind(String user_id) {
        User user = userCache.find(user_id);
        Member member = find(user.getObject_id());

        if (!member.getMember_status()) {
            throw new RuntimeException("您还没有通过审核");
        }

        if (Util.isNullOrEmpty(member.getScene_qrcode())) {
            String member_id = member.getMember_id();
            String scene_id = Util.getRandomUUID();

            ApiConfigKit.setThreadLocalApiConfig(WeChat.getApiConfig());
            ApiResult apiResult = QrcodeApi.createPermanent(scene_id);
            Boolean scene_is_expire = false;
            String scene_qrcode = QrcodeApi.getShowQrcodeUrl(apiResult.getStr("ticket"));

            sceneCache.save(scene_id, member_id, SceneTypeEnum.MEMBER.getKey(), scene_is_expire, scene_qrcode, user_id);

            memberCache.updateByMember_idAndScene_idAndScene_qrcode(member_id, scene_id, scene_qrcode, user_id);

            return scene_qrcode;
        } else {
            return member.getScene_qrcode();
        }
    }

    public Map<String, Object> myFind(String request_user_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        User user = userCache.find(request_user_id);
        Member member = find(user.getObject_id());

        resultMap.putAll(getMemberLevel(member.getMember_level_id(), user.getUser_name(), user.getUser_avatar(), member.getMember_status()));

        resultMap.put(Member.MEMBER_TOTAL_AMOUNT, member.getMember_total_amount());
        resultMap.put(OrderFlowEnum.WAIT_PAY.getKey(), 0);
        resultMap.put(OrderFlowEnum.WAIT_SEND.getKey(), 0);
        resultMap.put(OrderFlowEnum.WAIT_RECEIVE.getKey(), 0);

        return resultMap;
    }

    public Member teamFind(String member_id) {
        return memberCache.find(member_id);
    }

    public List<MemberLevel> teamMemberLevelFind(String member_id) {
        Member member = memberCache.find(member_id);

        Member parentMember = memberCache.find(member.getParent_id());
        MemberLevel parentMemberLevel = memberLevelCache.find(parentMember.getMember_level_id());

        List<MemberLevel> resultList = new ArrayList<MemberLevel>();
        List<MemberLevel> memberLevelList = memberLevelCache.listAll();
        for(MemberLevel m : memberLevelList) {
            if (m.getMember_level_value() > parentMemberLevel.getMember_level_value()) {
                resultList.add(m);
            }

            if (m.getMember_level_id().equals(member.getMember_level_id())) {
                m.put(Constant.IS_SELECT, true);
            } else {
                m.put(Constant.IS_SELECT, false);
            }
        }

        return resultList;
    }

//    public Member findByUser_id(String user_id) {
//        if (Util.isNullOrEmpty(user_id)) {
//            return null;
//        }
//
//        User user = userCache.find(user_id);
//
//        if (user == null) {
//            return null;
//        }
//
//        return memberCache.find(user.getObject_id());
//    }

//    public Member save(Member member, User user, String request_user_id) {
//        String user_id = Util.getRandomUUID();
//        String member_phone = "";
//        String member_remark = "";
//
//        member.setUser_id(user_id);
//        member.setMember_phone(member_phone);
//        member.setMember_remark(member_remark);
//
//        memberCache.save(member, request_user_id);
//
//        userService.saveByUser_idAndUser_phoneAndUser_passwordAndObject_idAndUser_type(user_id, user.getUser_phone(), user.getUser_password(), member.getMember_id(), UserType.MEMBER.getKey(), request_user_id);
//
//        return member;
//    }

    public Member saveByWechat_open_idAndWechat_union_idAndUser_nameAndUser_avatarAndFrom_scene_idAndMember_status(String wechat_open_id, String wechat_union_id, String user_name, String user_avatar, String from_scene_id, Boolean member_status) {
        if (Util.isNullOrEmpty(wechat_open_id)) {
            throw new RuntimeException("wechat open id is null");
        }

        //过滤Emoji表情
        user_name = Util.getEmoji(user_name);

        User user = userCache.findByWechat_open_idAndWechat_union_idAndUser_type(wechat_open_id, wechat_union_id, UserType.MEMBER.getKey());
        if (user == null) {
            String user_id = Util.getRandomUUID();
            String parent_id = "";
            String parent_path = (new JSONArray()).toJSONString();
            String scene_id = "";
            String scene_qrcode = "";
            BigDecimal member_total_amount = BigDecimal.valueOf(0);
            BigDecimal member_withdrawal_amount = BigDecimal.valueOf(0);
            BigDecimal member_month_order_amount = BigDecimal.valueOf(0);
            BigDecimal member_all_order_amount = BigDecimal.valueOf(0);
            String member_level_id = "";
            String request_user_id = "";
            String member_phone = "";
            String member_remark = "";

            Member member = memberCache.save(parent_id, parent_path, user_id, from_scene_id, scene_id, scene_qrcode, member_total_amount, member_withdrawal_amount, member_month_order_amount, member_all_order_amount, member_level_id, user_name, member_phone, member_remark, member_status, request_user_id);

            userCache.saveByUser_idAndUser_nameAndUser_avatarAndWechat_open_idAndWechat_union_idAndObject_idAndUser_type(user_id, user_name, user_avatar, wechat_open_id, wechat_union_id, member.getMember_id(), UserType.MEMBER.getKey(), request_user_id);

            return member;
        } else {
            String user_id = user.getUser_id();
            String member_id = user.getObject_id();

            userCache.updateByUser_idAndUser_nameAndUser_avatar(user_id, user_name, user_avatar, user_id);
            memberCache.updateByMember_idAndMember_name(member_id, user_name, user_id);

            Member member = memberCache.find(user.getObject_id());

            return member;
        }
    }

    public boolean childrenUpdate(String member_id, String member_level_id, String request_user_id) {
        Member member = memberCache.find(member_id);
        User parentUser = userCache.find(request_user_id);
        Member parentMember = find(parentUser.getObject_id());

        if (member.getParent_id().equals(parentMember.getMember_id())) {
            return memberCache.childrenUpdate(member_id, member_level_id, request_user_id);
        } else {
            throw new RuntimeException("您不是上一级");
        }
    }

    public boolean updateByMember_idAndParent_idAndParent_pathAndMember_level_id(String member_id, String parent_id, String parent_path, String member_level_id) {
        return memberCache.updateByMember_idAndParent_idAndParent_pathAndMember_level_id(member_id, parent_id, parent_path, member_level_id);
    }

    public void updateAmount(List<Member> memberList) {
        memberCache.updateAmount(memberList);
    }

    public boolean updateByMember_idAndMember_name(String member_id, String member_name, String request_user_id) {
        return memberCache.updateByMember_idAndMember_name(member_id, member_name, request_user_id);
    }

    public boolean updateByMember_idAndMember_level_id(String member_id, String member_level_id, String request_user_id) {
        return memberCache.updateByMember_idAndMember_level_id(member_id, member_level_id, request_user_id);
    }

    public boolean delete(Member member, String request_user_id) {
        boolean result = memberCache.delete(member.getMember_id(), request_user_id);

        userCache.deleteByObject_idAndUser_type(member.getMember_id(), UserType.MEMBER.getKey(), request_user_id);

        return result;
    }

//    public Map<String, Object> login(String user_phone, String user_password, String platform, String version, String ip_address, String request_user_id) {
//        User user = userService.findByUser_phoneAndUser_passwordAndUser_type(user_phone, user_password, UserType.MEMBER.getKey());
//
//        return getMember(user, platform, version, ip_address, request_user_id);
//    }

    public Map<String, Object> weChatH5Login(String wechat_open_id, String wechat_union_id, String user_name, String user_avatar, String scene_id, Boolean member_status, String platform, String version, String ip_address, String request_user_id) {
//        User user = userService.findByWechat_open_idAndWechat_union_idAndUser_type(wechat_open_id, wechat_union_id, UserType.MEMBER.getKey());
//
//        Member member = find(user.getObject_id());

        Member member = saveByWechat_open_idAndWechat_union_idAndUser_nameAndUser_avatarAndFrom_scene_idAndMember_status(wechat_open_id, wechat_union_id, user_name, user_avatar, scene_id, member_status);

        return getMember(wechat_open_id, member.getUser_id(), user_name, user_avatar, member.getMember_level_id(), member.getMember_status(), platform, version, ip_address, request_user_id);
    }

    public Map<String, Object> weChatWXLogin(JSONObject jsonObject, String platform, String version, String ip_address) {
        String wx_app_id = WeChat.wx_app_id;
        String wx_app_secret = WeChat.wx_app_secret;
        String js_code = jsonObject.getString("js_code");
        String encrypted_data = jsonObject.getString("encrypted_data");
        String iv = jsonObject.getString("iv");

        String result = HttpKit.get("https://api.weixin.qq.com/sns/jscode2session?appid=" + wx_app_id + "&secret=" + wx_app_secret + "&js_code=" + js_code + "&grant_type=authorization_code");
        JSONObject resultJSONObject = JSONObject.parseObject(result);
        String session_key = resultJSONObject.getString("session_key");

//        System.out.println("---------------");
//        System.out.println("wx_app_id:" + wx_app_id);
//        System.out.println("wx_app_secret:" + wx_app_secret);
//        System.out.println(resultJSONObject.toJSONString());
//        System.out.println("---------------");

        String wechat_open_id = "";
        String wechat_union_id = "";
        String user_name = "";
        String user_avatar = "";
        String scene_id = "";
        Boolean member_status = false;
        String request_user_id = "";

//        System.out.println("---------------");
//        System.out.println("encrypted_data:" + encrypted_data);
//        System.out.println("session_key:" + session_key);
//        System.out.println("iv:" + iv);
//        System.out.println("---------------");

        try {
            result = decrypt(encrypted_data, session_key, iv, "UTF-8");

            if (result != null && result.length() > 0) {
                resultJSONObject = JSONObject.parseObject(result);

//                System.out.println("---------------");
//                System.out.println(resultJSONObject.toJSONString());
//                System.out.println("---------------");

                wechat_open_id = resultJSONObject.getString("openId");
                wechat_union_id = resultJSONObject.getString("unionId");
                user_name = resultJSONObject.getString("nickName");
                user_avatar = resultJSONObject.getString("avatarUrl");
            }

            if (Util.isNullOrEmpty(wechat_union_id)) {
                wechat_union_id = "";
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception:" + e.toString());
        }

        Member member = saveByWechat_open_idAndWechat_union_idAndUser_nameAndUser_avatarAndFrom_scene_idAndMember_status(wechat_open_id, wechat_union_id, user_name, user_avatar, scene_id, member_status);

        return getMember(wechat_open_id, member.getUser_id(), user_name, user_avatar, member.getMember_level_id(), member.getMember_status(), platform, version, ip_address, request_user_id);
    }

    private Map<String, Object> getMember(String wechat_open_id, String user_id, String user_name, String user_avatar, String member_level_id, Boolean member_status, String platform, String version, String ip_address, String request_user_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Authorization authorization = authorizationCache.save(user_id, platform, version, ip_address, request_user_id);

        resultMap.put("open_id", wechat_open_id);
        resultMap.put(Constant.TOKEN.toLowerCase(), authorization.getAuthorization_token());

        resultMap.putAll(getMemberLevel(member_level_id, user_name, user_avatar, member_status));

        return resultMap;
    }

    private Map<String, Object> getMemberLevel(String member_level_id, String user_name, String user_avatar, Boolean member_status) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put(User.USER_NAME, user_name);
        resultMap.put(User.USER_AVATAR, user_avatar);
        resultMap.put(Member.MEMBER_STATUS, member_status);

        if (Util.isNullOrEmpty(member_level_id)) {
            resultMap.put(MemberLevel.MEMBER_LEVEL_ID, "");
            resultMap.put(MemberLevel.MEMBER_LEVEL_NAME, "会员");
            resultMap.put(MemberLevel.MEMBER_LEVEL_VALUE, 999);
        } else {
            MemberLevel memberLevel = memberLevelCache.find(member_level_id);

            resultMap.put(MemberLevel.MEMBER_LEVEL_ID, memberLevel.getMember_level_id());
            resultMap.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
            resultMap.put(MemberLevel.MEMBER_LEVEL_VALUE, memberLevel.getMember_level_value());
        }

        return resultMap;
    }

}