package com.shanghaichuangshi.shop.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shanghaichuangshi.constant.Kdniao;
import com.shanghaichuangshi.shop.cache.ExpressCache;
import com.shanghaichuangshi.shop.cache.OrderCache;
import com.shanghaichuangshi.shop.model.Express;
import com.shanghaichuangshi.service.Service;
import com.shanghaichuangshi.shop.model.Order;
import com.shanghaichuangshi.shop.type.OrderFlowEnum;
import com.shanghaichuangshi.util.DateUtil;
import com.shanghaichuangshi.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

public class ExpressService extends Service {

    private final ExpressCache expressCache = new ExpressCache();
    private final OrderCache orderCache = new OrderCache();

    public int count(String express_number) {
        return expressCache.count(express_number);
    }

    public List<Express> list(String express_number, int m, int n) {
        return expressCache.list(express_number, m, n);
    }

    public Express find(String express_id) {
        return expressCache.find(express_id);
    }

    public Express save(Express express, String request_user_id) {
        String express_id = Util.getRandomUUID();

        subscription(express_id, express.getExpress_type(), express.getExpress_number());

        Order order = orderCache.find(express.getOrder_id());
        if (order.getOrder_flow().equals(OrderFlowEnum.WAIT_SEND.getKey())) {
            orderCache.updateReceive(express.getOrder_id(), request_user_id);
        }
        orderCache.updateReceive(express.getOrder_id(), request_user_id);

        return expressCache.save(express_id, express, request_user_id);
    }

    public boolean update(Express express, String request_user_id) {
        Express e = find(express.getExpress_id());
        if (e.getExpress_type().equals(express.getExpress_type()) && e.getExpress_number().equals(express.getExpress_number())) {

        } else {
            subscription(express.getExpress_id(), express.getExpress_type(), express.getExpress_number());
        }

        return expressCache.update(express, request_user_id);
    }

    public boolean delete(Express express, String request_user_id) {
        return expressCache.delete(express.getExpress_id(), request_user_id);
    }

    private void subscription(String express_id, String expCode, String expNo) {
        String eBusinessID = Kdniao.EBusinessID;
        String appKey = Kdniao.AppKey;
        String reqURL = Kdniao.ReqURL;

        try {
            String requestData = "{'CallBack':'" + express_id + "','OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";

            Map<String, String> params = new HashMap<String, String>();
            params.put("RequestData", urlEncoder(requestData, "UTF-8"));
            params.put("EBusinessID", eBusinessID);
            params.put("RequestType", "1008");
            String dataSign = encrypt(requestData, appKey, "UTF-8");
            params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
            params.put("DataType", "2");

            String result = sendPost(reqURL, params);

            JSONObject jsonObject = JSON.parseObject(result);

            Boolean success = jsonObject.getBoolean("Success");

            if (!success) {
                throw new RuntimeException("快递单订阅物流信息不成功");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.toString());
        }
    }

    public Map<String, Object> push(String requestData) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("EBusinessID", Kdniao.EBusinessID);
        resultMap.put("UpdateTime", DateUtil.getDateTimeString(new Date()));
        resultMap.put("Success", true);
        resultMap.put("Reason", "");

        JSONObject requestDataObject = JSONObject.parseObject(requestData);
        String eBusinessID = requestDataObject.getString("EBusinessID");
        if (!eBusinessID.equals(Kdniao.EBusinessID)) {
            resultMap.put("Success", false);
            resultMap.put("Reason", "EBusinessID is error");

            return resultMap;
        }

        JSONArray jsonArray = JSONArray.parseArray(requestDataObject.getString("Data"));

        List<Express> expressList = new ArrayList<Express>();

        List<String> orderIdList = new ArrayList<String>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String express_id = jsonObject.getString("CallBack");
            String express_type = jsonObject.getString("ShipperCode");
            String express_number = jsonObject.getString("LogisticCode");

            Boolean success = jsonObject.getBoolean("Success");

            String express_flow = "无轨迹";
            Boolean express_status = false;
            String express_trace = jsonObject.getString("Traces");
            if (success) {
                String state = jsonObject.getString("State");
                if (state.equals("1")) {
                    express_flow = "已揽收";
                } else if (state.equals("2")) {
                    express_flow = "在途中";
                } else if (state.equals("201")) {
                    express_flow = "到达派件城市";
                } else if (state.equals("3")) {
                    express_flow = "签收";

                    express_status = true;
                } else if (state.equals("4")) {
                    express_flow = "问题件";
                }

                express_trace = jsonObject.getString("Traces");
            }

            Express express = expressCache.find(express_id);

            if (!orderIdList.contains(express.getOrder_id())) {
                orderIdList.add(express.getOrder_id());
            }

//            if (express.getExpress_type().equals(express_type) && express.getExpress_number().equals(express_number) && express.getSystem_status()) {
                express.setExpress_flow(express_flow);
                express.setExpress_status(express_status);
                express.setExpress_trace(express_trace);
                express.setExpress_id(express_id);

                expressList.add(express);
//            }
        }

        expressCache.updateBusiness(expressList);

        List<String> updateOrderIdList = new ArrayList<String>();
        for (String order_id : orderIdList) {
            List<Express> expressByOrderIdList = expressCache.listByOrder_id(order_id);

            Boolean isStatus = true;
            for (Express express : expressByOrderIdList) {
                if (!express.getExpress_status()) {
                    isStatus = false;
                }
            }

            if (isStatus) {
                updateOrderIdList.add(order_id);
            }
        }
        orderCache.updateFinish(updateOrderIdList);

        return resultMap;
    }

    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     *
     * @param content  内容
     * @param keyValue Appkey
     * @param charset  编码方式
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    @SuppressWarnings("unused")
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    //System.out.println(entry.getKey()+":"+entry.getValue());
                }
                //System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    private static char[] base64EncodeChars = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

}