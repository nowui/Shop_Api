package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.service.AuthorizationService;
import com.shanghaichuangshi.service.UserService;
import com.shanghaichuangshi.shop.dao.MemberDao;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.shop.type.SceneTypeEnum;
import com.shanghaichuangshi.type.UserType;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shanghaichuangshi.util.AesUtil.decrypt;

public class MemberService extends Service {

    private final MemberDao memberDao = new MemberDao();

    private final UserService userService = new UserService();
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final MemberLevelService memberLevelService = new MemberLevelService();
    private final SceneService sceneService = new SceneService();

    public int count(Member member) {
        return memberDao.count(member.getMember_name());
    }

    public List<Member> list(Member member, int m, int n) {
        return memberDao.list(member.getMember_name(), m, n);
    }

    public List<Member> teamList(String user_id, int m, int n) {
        Member member = findByUser_id(user_id);

        List<Member> memberList = memberDao.teamList(member.getMember_id(), m, n);

        for (Member item : memberList) {
            User user = userService.find(item.getUser_id());
            item.put(User.USER_AVATAR, user.getUser_avatar());
            user.remove(User.USER_ID);

            if (Util.isNullOrEmpty(item.getMember_level_id())) {
                item.put(MemberLevel.MEMBER_LEVEL_NAME, "");
            } else {
                MemberLevel memberLevel = memberLevelService.find(item.getMember_level_id());
                item.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
            }
        }

        return memberList;
    }

    public Member find(String member_id) {
        Member member = memberDao.find(member_id);

        User user = userService.find(member.getUser_id());

        member.put(User.USER_AVATAR, user.getUser_avatar());

        return member;
    }

    public Member teamFind(String member_id) {
        Member member = memberDao.find(member_id);

        User user = userService.find(member.getUser_id());

        member.put(User.USER_AVATAR, user.getUser_avatar());

        if (Util.isNullOrEmpty(member.getMember_level_id())) {
            member.put(MemberLevel.MEMBER_LEVEL_NAME, "");

            if (!member.getMember_status()) {
                Member parentMember = memberDao.find(member.getParent_id());
                MemberLevel parentMemberLevel = memberLevelService.find(parentMember.getMember_level_id());
                List<MemberLevel> list = new ArrayList<MemberLevel>();
                List<MemberLevel> memberLevelList = memberLevelService.listAll();
                for(MemberLevel m : memberLevelList) {
                    if (m.getMember_level_value() > parentMemberLevel.getMember_level_value()) {
                        list.add(m);
                    }
                }
                member.put(Member.MEMBER_LEVEL_LIST, list);
            }
        } else {
            MemberLevel memberLevel = memberLevelService.find(member.getMember_level_id());
            member.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
        }

        return member;
    }

    public String qrcodeFind(String user_id) {
        Member member = findByUser_id(user_id);

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

            sceneService.save(scene_id, member_id, SceneTypeEnum.MEMBER.getKey(), scene_is_expire, scene_qrcode, user_id);

            memberDao.updateByMember_idAndScene_idAndScene_qrcode(member_id, scene_id, scene_qrcode, user_id);

            return scene_qrcode;
        } else {
            return member.getScene_qrcode();
        }
    }

    public Member findByUser_id(String user_id) {
        if (Util.isNullOrEmpty(user_id)) {
            return null;
        }

        User user = userService.find(user_id);

        if (user == null) {
            return null;
        }

        return memberDao.find(user.getObject_id());
    }

//    public Member save(Member member, User user, String request_user_id) {
//        String user_id = Util.getRandomUUID();
//        String member_phone = "";
//        String member_remark = "";
//
//        member.setUser_id(user_id);
//        member.setMember_phone(member_phone);
//        member.setMember_remark(member_remark);
//
//        memberDao.save(member, request_user_id);
//
//        userService.saveByUser_idAndUser_phoneAndUser_passwordAndObject_idAndUser_type(user_id, user.getUser_phone(), user.getUser_password(), member.getMember_id(), UserType.MEMBER.getKey(), request_user_id);
//
//        return member;
//    }

    public Member saveByWechat_open_idAndWechat_union_idAndUser_nameAndUser_avatarAndFrom_scene_idAndMember_status(String wechat_open_id, String wechat_union_id, String user_name, String user_avatar, String from_scene_id, Boolean member_status) {
        User user = userService.findByWechat_open_idAndWechat_union_idAndUser_type(wechat_open_id, wechat_union_id, UserType.MEMBER.getKey());
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

            Member member = memberDao.save(parent_id, parent_path, user_id, from_scene_id, scene_id, scene_qrcode, member_total_amount, member_withdrawal_amount, member_month_order_amount, member_all_order_amount, member_level_id, user_name, member_phone, member_remark, member_status, request_user_id);

            userService.saveByUser_idAndUser_nameAndUser_avatarAndWechat_open_idAndWechat_union_idAndObject_idAndUser_type(user_id, user_name, user_avatar, wechat_open_id, wechat_union_id, member.getMember_id(), UserType.MEMBER.getKey(), request_user_id);

            return member;
        } else {
            Member member = memberDao.find(user.getObject_id());

            return member;
        }
    }

    public boolean childrenUpdate(String member_id, String member_level_id, String request_user_id) {
        Member member = memberDao.find(member_id);
        Member parentMember = findByUser_id(request_user_id);

        if (member.getParent_id().equals(parentMember.getMember_id())) {
            return memberDao.childrenUpdate(member_id, member_level_id, request_user_id);
        } else {
            throw new RuntimeException("您不是上一级");
        }
    }

    public boolean updateByMember_idAndParent_idAndParent_pathAndMember_level_id(String member_id, String parent_id, String parent_path, String member_level_id) {
        return memberDao.updateByMember_idAndParent_idAndParent_pathAndMember_level_id(member_id, parent_id, parent_path, member_level_id);
    }

    public void updateAmount(List<Member> memberList) {
        if (memberList.size() > 0) {

        }
        memberDao.updateAmount(memberList);
    }

    public boolean delete(Member member, String request_user_id) {
        boolean result = memberDao.delete(member.getMember_id(), request_user_id);

        userService.deleteByObject_idAndUser_type(member.getMember_id(), UserType.MEMBER.getKey(), request_user_id);

        return result;
    }

//    public Map<String, Object> login(String user_phone, String user_password, String platform, String version, String ip_address, String request_user_id) {
//        User user = userService.findByUser_phoneAndUser_passwordAndUser_type(user_phone, user_password, UserType.MEMBER.getKey());
//
//        return getMember(user, platform, version, ip_address, request_user_id);
//    }

    public Map<String, Object> weChatH5Login(String wechat_open_id, String wechat_union_id, String platform, String version, String ip_address, String request_user_id) {
        User user = userService.findByWechat_open_idAndWechat_union_idAndUser_type(wechat_open_id, wechat_union_id, UserType.MEMBER.getKey());

        Member member = find(user.getObject_id());

        return getMember(wechat_open_id, user.getUser_id(), user.getUser_name(), user.getUser_avatar(), member.getMember_level_id(), member.getMember_status(), platform, version, ip_address, request_user_id);
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

        String wechat_open_id = "";
        String wechat_union_id = "";
        String user_name = "";
        String user_avatar = "";
        String scene_id = "";
        Boolean member_status = false;
        String request_user_id = "";

        try {
            result = decrypt(encrypted_data, session_key, iv, "UTF-8");

            if (result != null && result.length() > 0) {
                resultJSONObject = JSONObject.parseObject(result);

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

    public Map<String, Object> myFind(String request_user_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Member member = findByUser_id(request_user_id);

        String member_level_id = member.getMember_level_id();

        if (Util.isNullOrEmpty(member_level_id)) {
            resultMap.put(MemberLevel.MEMBER_LEVEL_NAME, "");
        } else {
            MemberLevel memberLevel = memberLevelService.find(member_level_id);

            resultMap.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
        }

        resultMap.put(Member.MEMBER_STATUS, member.getMember_status());
        resultMap.put(Member.MEMBER_TOTAL_AMOUNT, member.getMember_total_amount());
        resultMap.put(OrderFlowEnum.WAIT_PAY.getKey(), 0);
        resultMap.put(OrderFlowEnum.WAIT_SEND.getKey(), 0);
        resultMap.put(OrderFlowEnum.WAIT_RECEIVE.getKey(), 0);

        return resultMap;
    }

    private Map<String, Object> getMember(String wechat_open_id, String user_id, String user_name, String user_avatar, String member_level_id, Boolean member_status, String platform, String version, String ip_address, String request_user_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String token = authorizationService.saveByUser_id(user_id, platform, version, ip_address, request_user_id);

        resultMap.put("open_id", wechat_open_id);
        resultMap.put(Constant.TOKEN.toLowerCase(), token);
        resultMap.put(User.USER_NAME, user_name);
        resultMap.put(User.USER_AVATAR, user_avatar);
        resultMap.put(Member.MEMBER_STATUS, member_status);
//        resultMap.put(Member.SCENE_QRCODE, member.getScene_qrcode());

        if (Util.isNullOrEmpty(member_level_id)) {
            resultMap.put(MemberLevel.MEMBER_LEVEL_ID, "");
            resultMap.put(MemberLevel.MEMBER_LEVEL_NAME, "");
            resultMap.put(MemberLevel.MEMBER_LEVEL_VALUE, 999);
        } else {
            MemberLevel memberLevel = memberLevelService.find(member_level_id);

            resultMap.put(MemberLevel.MEMBER_LEVEL_ID, memberLevel.getMember_level_id());
            resultMap.put(MemberLevel.MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
            resultMap.put(MemberLevel.MEMBER_LEVEL_VALUE, memberLevel.getMember_level_value());
        }

        return resultMap;
    }

}