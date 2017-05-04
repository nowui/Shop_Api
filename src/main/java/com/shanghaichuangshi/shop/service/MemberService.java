package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.jfinal.weixin.sdk.api.UserApi;
import com.shanghaichuangshi.constant.Constant;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.model.User;
import com.shanghaichuangshi.service.AuthorizationService;
import com.shanghaichuangshi.service.UserService;
import com.shanghaichuangshi.shop.dao.MemberDao;
import com.shanghaichuangshi.shop.model.Delivery;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.type.SceneTypeEnum;
import com.shanghaichuangshi.type.UserType;
import com.shanghaichuangshi.util.Util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberService extends Service {

    private final MemberDao memberDao = new MemberDao();

    private final UserService userService = new UserService();
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final DeliveryService deliveryService = new DeliveryService();
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

        return memberDao.teamList(member.getMember_id(), m, n);
    }

    public Member find(String member_id) {
        Member member = memberDao.find(member_id);

        User user = userService.find(member.getUser_id());

        member.put(User.USER_AVATAR, user.getUser_avatar());

        return member;
    }

    public String qrcodeFind(String user_id) {
        Member member = findByUser_id(user_id);

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

    public Member saveByWechat_open_idAndFrom_scene_idAndMember_status(String wechat_open_id, String from_scene_id, Boolean member_status) {
        User user = userService.findByWechat_open_idAndUser_type(wechat_open_id, UserType.MEMBER.getKey());
        if (user == null) {
            ApiResult apiResult = UserApi.getUserInfo(wechat_open_id);
            String user_name = apiResult.getStr("nickname");
            String user_avatar = apiResult.getStr("headimgurl");

            String user_id = Util.getRandomUUID();
            String parent_id = "";
            String parent_path = (new JSONArray()).toJSONString();
            String scene_id = "";
            String scene_qrcode = "";
            BigDecimal member_total_amount = BigDecimal.valueOf(0);
            BigDecimal member_withdrawal_amount = BigDecimal.valueOf(0);
            String member_level_id = "";
            String request_user_id = "";
            String member_phone = "";
            String member_remark = "";

            Member member = new Member();
            member.setParent_id(parent_id);
            member.setParent_path(parent_path);
            member.setParent_id(parent_id);
            member.setUser_id(user_id);
            member.setFrom_scene_id(from_scene_id);
            member.setScene_id(scene_id);
            member.setScene_qrcode(scene_qrcode);
            member.setMember_total_amount(member_total_amount);
            member.setMember_withdrawal_amount(member_withdrawal_amount);
            member.setMember_level_id(member_level_id);
            member.setMember_name(user_name);
            member.setMember_phone(member_phone);
            member.setMember_remark(member_remark);
            member.setMember_status(member_status);

            memberDao.save(member, request_user_id);

            userService.saveByUser_idAndUser_nameAndUser_avatarAndWechat_open_idAndObject_idAndUser_type(user_id, user_name, user_avatar, wechat_open_id, member.getMember_id(), UserType.MEMBER.getKey(), request_user_id);

            return member;
        } else {
            Member member = memberDao.find(user.getObject_id());

            return member;
        }
    }

    public boolean updateByMember_idAndParent_idAndParent_pathAndMember_level_id(String member_id, String parent_id, String parent_path, String member_level_id) {
        return memberDao.updateByMember_idAndParent_idAndParent_pathAndMember_level_id(member_id, parent_id, parent_path, member_level_id);
    }

    public boolean delete(Member member, String request_user_id) {
        boolean result = memberDao.delete(member.getMember_id(), request_user_id);

        userService.deleteByObject_idAndUser_type(member.getMember_id(), UserType.MEMBER.getKey(), request_user_id);

        return result;
    }

    public Map<String, Object> login(User user, String platform, String version, String ip_address, String request_user_id) {
        User u = userService.findByUser_phoneAndUser_passwordAndUser_type(user.getUser_phone(), user.getUser_password(), UserType.MEMBER.getKey());

        return getMember(u, platform, version, ip_address, request_user_id);
    }

    public Map<String, Object> weChatLogin(String wechat_open_id) {
        User user = userService.findByWechat_open_idAndUser_type(wechat_open_id, UserType.MEMBER.getKey());

        String request_user_id = "";
        String platform = "";
        String version = "";
        String ip_address = "";

        return getMember(user, platform, version, ip_address, request_user_id);
    }

    private Map<String, Object> getMember(User user, String platform, String version, String ip_address, String request_user_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Member member = memberDao.find(user.getObject_id());

        Delivery delivery = deliveryService.findDefaultByUser_id(user.getUser_id());

        String token = authorizationService.saveByUser_id(user.getUser_id(), platform, version, ip_address, request_user_id);

        resultMap.put(Constant.TOKEN.toLowerCase(), token);
        resultMap.put(User.USER_NAME, user.getUser_name());
        resultMap.put(User.USER_AVATAR, user.getUser_avatar());
        resultMap.put(Member.SCENE_QRCODE, member.getScene_qrcode());

        if (Util.isNullOrEmpty(member.getMember_level_id())) {
            MemberLevel memberLevel = new MemberLevel();
            memberLevel.setMember_level_id("");
            memberLevel.setMember_level_value(0);

            resultMap.put("member_level", memberLevel);
        } else {
            MemberLevel memberLevel = memberLevelService.find(member.getMember_level_id());

            resultMap.put("member_level", memberLevel.removeUnfindable());
        }

        if (delivery == null) {
            resultMap.put("delivery", new JSONObject());
        } else {
            resultMap.put("delivery", delivery.removeUnfindable());
        }

        return resultMap;
    }

}