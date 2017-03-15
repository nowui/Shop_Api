package com.shanghaichuangshi.shop.controller;

import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.MsgController;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.shanghaichuangshi.constant.WeChat;

public class WeChatMsgController extends MsgController {

    @Override
    public ApiConfig getApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setAppId(WeChat.app_id);
        apiConfig.setAppSecret(WeChat.app_secret);
        apiConfig.setToken(WeChat.token);
        apiConfig.setEncryptMessage(false);
        apiConfig.setEncodingAesKey(WeChat.rncoding_aes_key);

        return apiConfig;
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
        System.out.println(inFollowEvent.getToUserName());
        System.out.println(inFollowEvent.getFromUserName());
        System.out.println(inFollowEvent.getCreateTime());
        System.out.println(inFollowEvent.getMsgType());
        OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
        outMsg.setContent("感谢关注 JFinal Weixin 极速开发，为您节约更多时间，去陪恋人、家人和朋友 :) \n\n\n ");
        render(outMsg);
    }

    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
        System.out.println(inQrCodeEvent.getToUserName());
        System.out.println(inQrCodeEvent.getFromUserName());
        System.out.println(inQrCodeEvent.getCreateTime());
        System.out.println(inQrCodeEvent.getMsgType());
        System.out.println(inQrCodeEvent.getEventKey());
        OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);



        outMsg.setContent("processInQrCodeEvent() 方法测试成功" + UserApi.getUserInfo(inQrCodeEvent.getToUserName()).getJson());
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
