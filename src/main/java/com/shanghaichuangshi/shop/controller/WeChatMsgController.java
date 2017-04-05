package com.shanghaichuangshi.shop.controller;

import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.MsgController;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.shanghaichuangshi.constant.WeChat;
import com.shanghaichuangshi.shop.model.Distributor;
import com.shanghaichuangshi.shop.model.Member;
import com.shanghaichuangshi.shop.model.MemberLevel;
import com.shanghaichuangshi.shop.model.Scene;
import com.shanghaichuangshi.shop.service.DistributorService;
import com.shanghaichuangshi.shop.service.MemberLevelService;
import com.shanghaichuangshi.shop.service.MemberService;
import com.shanghaichuangshi.shop.service.SceneService;
import com.shanghaichuangshi.shop.type.SceneTypeEnum;
import com.shanghaichuangshi.util.Util;

public class WeChatMsgController extends MsgController {

    private final MemberService memberService = new MemberService();
    private final DistributorService distributorService = new DistributorService();
    private final SceneService sceneService = new SceneService();
    private final MemberLevelService memberLevelService = new MemberLevelService();

    @Override
    public ApiConfig getApiConfig() {
        return WeChat.getApiConfig();
    }

    @Override
    protected void processInTextMsg(InTextMsg inTextMsg) {

    }

    @Override
    protected void processInImageMsg(InImageMsg inImageMsg) {

    }

    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {

    }

    @Override
    protected void processInVideoMsg(InVideoMsg inVideoMsg) {

    }

    @Override
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {

    }

    @Override
    protected void processInLocationMsg(InLocationMsg inLocationMsg) {

    }

    @Override
    protected void processInLinkMsg(InLinkMsg inLinkMsg) {

    }

    @Override
    protected void processInCustomEvent(InCustomEvent inCustomEvent) {

    }

    @Override
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        String wechat_open_id = inFollowEvent.getFromUserName();
        String scene_id = "";
        String event = inFollowEvent.getEvent();
        String request_user_id = "";

        Member member = memberService.saveByWechat_open_idAndFrom_scene_id(wechat_open_id, scene_id);

        if (event.equals("unsubscribe")) {
            sceneService.updateScene_cancelByScene_id(member.getFrom_scene_id(), request_user_id);
        }

        OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
        outMsg.setContent("恭喜您，成为我们的会员！");
        render(outMsg);
    }

    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
        String wechat_open_id = inQrCodeEvent.getFromUserName();
        String scene_id = inQrCodeEvent.getEventKey().replace("qrscene_", "");
        String distributor_id = "";
        String parent_id = "";
        String member_level_id = "";
        String content = "";
        String request_user_id = "";

        Scene scene = sceneService.find(scene_id);

        Member member = memberService.saveByWechat_open_idAndFrom_scene_id(wechat_open_id, scene_id);

        if (scene.getScene_type().equals(SceneTypeEnum.DISTRIBUTOR.getKey())) {
            distributor_id = scene.getObject_id();
            parent_id = "";

            if (Util.isNullOrEmpty(member.getDistributor_id())) {
                MemberLevel memberLevel = memberLevelService.findTopMember_level();
                member_level_id = memberLevel.getMember_level_id();

                memberService.updateByMember_idAndDistributor_idAndParent_idAndMember_level_id(member.getMember_id(), distributor_id, parent_id, member_level_id);

                sceneService.updateScene_addByScene_id(scene_id, request_user_id);

                Distributor distributor = distributorService.find(distributor_id);

                content += "恭喜您，成为我们的会员！您现在属于" + distributor.getDistributor_name() + "的销售团队";
            } else {
                Distributor distributor = distributorService.find(member.getDistributor_id());

                if (distributor_id.equals(member.getDistributor_id())) {
                    content += "您以前就属于" + distributor.getDistributor_name() + "的销售团队";
                } else {
                    content += "不能绑定，您属于" + distributor.getDistributor_name() + "的销售团队";
                }
            }
        } else if (scene.getScene_type().equals(SceneTypeEnum.MEMBER.getKey())) {
            Member parentMember = memberService.find(scene.getObject_id());

            distributor_id = parentMember.getDistributor_id();
            parent_id = parentMember.getMember_id();

            MemberLevel memberLevel = memberLevelService.findNextMember_levelByMember_level_id(parentMember.getMember_level_id());
            member_level_id = memberLevel.getMember_level_id();

        }



        OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
        outMsg.setContent(content);
        render(outMsg);
    }

    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {

    }

    @Override
    protected void processInMassEvent(InMassEvent inMassEvent) {

    }

    @Override
    protected void processInMenuEvent(InMenuEvent inMenuEvent) {

    }

    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {

    }

    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {

    }

    @Override
    protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {

    }

    @Override
    protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {

    }

    @Override
    protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {

    }

    @Override
    protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {

    }

    @Override
    protected void processInWifiEvent(InWifiEvent inWifiEvent) {

    }

    @Override
    protected void processInUserViewCardEvent(InUserViewCardEvent inUserViewCardEvent) {

    }

    @Override
    protected void processInSubmitMemberCardEvent(InSubmitMemberCardEvent inSubmitMemberCardEvent) {

    }

    @Override
    protected void processInUpdateMemberCardEvent(InUpdateMemberCardEvent inUpdateMemberCardEvent) {

    }

    @Override
    protected void processInUserPayFromCardEvent(InUserPayFromCardEvent inUserPayFromCardEvent) {

    }

    @Override
    protected void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent) {

    }

    @Override
    protected void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent) {

    }

    @Override
    protected void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg) {

    }
}
