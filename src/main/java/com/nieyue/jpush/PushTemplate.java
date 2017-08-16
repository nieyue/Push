package com.nieyue.jpush;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.nieyue.util.HttpClientUtil;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 
 * @author 聂跃
 * @date 2017年7月26日
 */
@Configuration
public class PushTemplate {
	@Value("${myPugin.jpush.key}")
	String key;
	@Value("${myPugin.jpush.secret}")
	String secret;
	
    
    /**
     * 初始化
     * @return
     * @throws APIRequestException 
     * @throws APIConnectionException 
     */
    public  PushResult init( PushPayload pushPayload) throws APIConnectionException, APIRequestException {
    	JPushClient jpushClient = new JPushClient(secret, key, null, ClientConfig.getInstance());
    	PushResult result = jpushClient.sendPush(pushPayload);
    	return result;
    }
	/**
     * 快捷地构建推送对象：所有平台，所有设备，内容为 content 的通知。
     * @return
     */
    public  PushPayload buildPushObject_all_all_alert( String content) {
        return PushPayload.alertAll(content);
    }
    /**
     * 快捷地构建推送对象：所有平台，所有设备，内容为 content 的消息。
     * @return
     */
    public  PushPayload buildPushObject_all_all_message(String contentType, String content) {
    	return  PushPayload.newBuilder()
    	        .setPlatform(Platform.all())
    	        .setAudience(Audience.all())
    	        .setMessage(Message.newBuilder()
    	                .setMsgContent(content)
    	                .setContentType(contentType)
    	                .build())
    	        .build();
    			
    }
    /**
     * 构建推送对象：所有平台，推送目标是别名为 "id"，通知内容为 ALERT。
     * @return
     */
    public  PushPayload buildPushObject_all_alias_alert(Integer id,String contentType,String content) {
    	return  PushPayload.newBuilder()
        .setPlatform(Platform.all())
        .setAudience(Audience.alias(id.toString()))
        
        //.setMessage(Message.content(content))
        .setMessage(Message.newBuilder()
                .setMsgContent(content)
                .setContentType(contentType)
                .build())
       // .setNotification(Notification.alert("ALERT"))
        .build();
    }
    /**
     * 构建推送对象：平台是 Android，目标是 tag 为 "tag1" 的设备，
     * 内容是 Android 通知 ALERT，并且标题为 TITLE。
     * @return
     */
    public  PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android("ALERT","TITLE" , null))
                .build();
    }
    /**
     * 构建推送对象：平台是 iOS，推送目标是 "tag1", 
     * "tag_all" 的交集，推送内容同时包括通知与消息 - 通知信息是 ALERT，
     * 角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；
     * 消息内容是 MSG_CONTENT。通知是 APNs 推送通道的，
     * 消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
     * @return
     */
    public  PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert("ALERT")
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content("MSG_CONTENT"))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }
    /**
     * 构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）交
     * （"alias1" 与 "alias2" 的并集），推送内容是 - 内容为 MSG_CONTENT 的消息，
     * 并且附加字段 from = JPush。
     * @return
     */
    public  PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent("MSG_CONTENT")
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
    /**
     * 构建推送对象：推送内容包含SMS信息
     */
    public void testSendWithSMS() {
        JPushClient jpushClient = new JPushClient(secret, key);
        try {
            SMS sms = SMS.content("Test SMS", 10);
            PushResult result = jpushClient.sendAndroidMessageWithAlias("Test SMS", "test sms", sms, "alias1");
            System.err.println("Got result - " + result);
        } catch (APIConnectionException e) {
        	System.err.println("Connection error. Should retry later. "+ e);
        } catch (APIRequestException e) {
        	System.err.println("Error response from JPush server. Should review and fix it. "+ e);
        	System.err.println("HTTP Status: " + e.getStatus());
        	System.err.println("Error Code: " + e.getErrorCode());
        	System.err.println("Error Message: " + e.getErrorMessage());
        }
    }
    public static void main(String[] args) throws Exception {
    	JPushClient jpushClient = new JPushClient("e8e6f60d043a1b4cace16cbf", "011d1f544fb77063b9192129", null, ClientConfig.getInstance());
    	String content = HttpClientUtil.doGet("http://www.newzhuan.com/article/list?pageSize=1");
    	   // For push, all you need do is to build PushPayload object.
    	// PushPayload payload =new PushTemplate().buildPushObject_all_all_message("新闻",content);
    	//PushPayload payload =new PushTemplate().buildPushObject_all_alias_alert(1431,"积分", content);
    	PushPayload payload =new PushTemplate().buildPushObject_all_alias_alert(1431,"收徒", content);

    	    try {
    	        PushResult result = jpushClient.sendPush(payload);
    	        System.err.println("Got result - " + result);

    	    } catch (APIConnectionException e1) {
    	        // Connection error, should retry later
    	    	System.err.println( e1);

    	    } catch (APIRequestException e2) {
    	        // Should review the error, and fix the request
    	    	System.err.println("Should review the error, and fix the request"+e2);
    	    	System.err.println("HTTP Status: " + e2.getStatus());
    	    	System.err.println("Error Code: " + e2.getErrorCode());
    	    	System.err.println("Error Message: " + e2.getErrorMessage());
    	    }
	}
    
}
