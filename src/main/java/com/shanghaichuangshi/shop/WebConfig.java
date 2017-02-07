package com.shanghaichuangshi.shop;

import com.shanghaichuangshi.config.Certificate;
import com.shanghaichuangshi.config.Config;

import com.shanghaichuangshi.constant.Url;
import com.shanghaichuangshi.route.RouteMatcher;
import com.shanghaichuangshi.shop.controller.BrandController;
import com.shanghaichuangshi.shop.controller.ProductController;
import com.shanghaichuangshi.shop.controller.ShopController;

import java.util.List;

public class WebConfig extends Config {

    public void configCertificate(Certificate certificate) {
        certificate.setPublic_key("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRPXhY/P7lcADQHeKNj81FuHE78o+YuowCc90MmtjUMQfpCTeXVlWBUZjLOVKMAPXPzcolRzmMebZvwbVyxAqI54we0sq6Ki7wCweUdJFiDJvr6H/o2fA5rR74p8HsmJJl8ljZYMsN35lHKH3glfcIiRdTQC/24nxVNbPv+UhmSwIDAQAB");
        certificate.setPrivate_key("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANE9eFj8/uVwANAd4o2PzUW4cTvyj5i6jAJz3Qya2NQxB+kJN5dWVYFRmMs5UowA9c/NyiVHOYx5tm/BtXLECojnjB7SyroqLvALB5R0kWIMm+vof+jZ8DmtHvinweyYkmXyWNlgyw3fmUcofeCV9wiJF1NAL/bifFU1s+/5SGZLAgMBAAECgYEAlCuMcq/NrRnwaXAQQ6DGgw3GmeX9y/CmPwJfUZLB4xlJebt+M1v+ttHaemcAToZLi7k14cobNZ/nEiLBZCDxN5O+dKdZyaWshRnWecACSRK7Wlp7WjgCLjieIn3+w3QXWq2IZFdYQSOIeuXT8m6FU72GvuOgziv8rgJcUi7kBHkCQQDnjmepwzLNUA5Ukbg5BFWROHPg42Uf25RjpeUstSV1HVgYHYf0KOUdIlS0+TFzZTcijeAeIfHaeM+kwiUA75hFAkEA51P94O+mNlEzN7v04E7K9I3uj+gVDc7z/MHxsYJ5VW3mohOiqwBiQBYlnK5KiiBCuFVixR633HhMkqiAgvvVTwJAUl6s7425d6Gfx2Oixd2N1r/fMMOTSHbi3WO5F2NE9NlAaiuvHiKiBfAdc9clSShbKZaQgAeRMidBrhlF8oRIhQJBAMHaWP6O1bLfCRTDpcnzPZEC/9AIpNwVedFulaQzuooktwlLex3iDHO4G3zZcg2eS0s+Aq89tsZC6ahdHJSnhXECQQDF64mfFVEx2XcruKCwDQ8K0ReEQN4r5kC/wnGIxkY4k6htrg6Im1b8iytTzV8BRxs8aukqgp1ggfBuxweAeiPC");
    }

    public void configRouteMatcher(RouteMatcher routeMatcher) {
        routeMatcher.add("/shop", ShopController.class);
        routeMatcher.add("/brand", BrandController.class);
        routeMatcher.add("/product", ProductController.class);
    }

    public void configUncheckTokenUrl(List<String> uncheckTokenUrlList) {

    }

    public void configUncheckRequestUrl(List<String> uncheckRequestUrlList) {

    }

    public void configUncheckLogUrl(List<String> uncheckLogUrlList) {

    }

}