package com.nieyue.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.jpush.PushTemplate;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResultList;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;


/**
 * 推送控制类
 * @author yy
 *
 */
@RestController
@RequestMapping(value={"/push"})
public class PushController {
	/*@Autowired
	StringRedisTemplate stringRedisTemplate;*/
	@Autowired
	PushTemplate pushTemplate;
	/**
	 * getsession
	 * @return
	 */
	@RequestMapping(value={"/test/getSession"})
	public String getSession(
			HttpSession session,
			HttpServletResponse response
			){
		return session.getId();
		
	}
	/**
	 * buildPushObject_all_alias_message
	 * 所有消息
	 * @return
	 * @throws APIRequestException 
	 * @throws APIConnectionException 
	 */
	@RequestMapping(value={"/sendAllMessage"})
	public StateResultList sendAllMessage(
			@RequestParam("businessType") String businessType ,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("businessId") Integer businessId,
			HttpSession session
			) throws APIConnectionException, APIRequestException{
			List<PushResult> l=new ArrayList<PushResult>();
			PushPayload pp= pushTemplate.buildPushObject_all_alias_message(businessId,businessType,title,content);
			PushResult r = pushTemplate.init(pp);
			l.add(r);
		return ResultUtil.getSlefSRSuccessList(l);
		
	}
	/**
	 * buildPushObject_id_alias_message
	 * 单人消息
	 * @return
	 * @throws APIRequestException 
	 * @throws APIConnectionException 
	 */
	@RequestMapping(value={"/sendMessage"})
	public StateResultList sendMessage(
			@RequestParam("acountId") Integer acountId ,
			@RequestParam("businessId") Integer businessId ,
			@RequestParam("title") String title ,
			@RequestParam("businessType") String businessType ,
			@RequestParam("content") String content,
			HttpSession session
			) throws APIConnectionException, APIRequestException{
			List<PushResult> l=new ArrayList<PushResult>();
			PushPayload pp= pushTemplate.buildPushObject_id_alias_message(acountId,businessId,businessType,title, content);
			PushResult r = pushTemplate.init(pp);
			l.add(r);
			return ResultUtil.getSlefSRSuccessList(l);
		
	}
	/**
     * 构建推送对象：所有，通知内容为 content。
     * @param businessId 业务Id
     * @param businessType 业务类型
     * @param content 内容
     * @return
     */
	@RequestMapping(value={"/sendAllAlert"})
	public StateResultList sendAllAlert(
			@RequestParam("businessId") Integer businessId,
			@RequestParam("businessType") String businessType ,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			HttpSession session
			) throws APIConnectionException, APIRequestException{
		List<PushResult> l=new ArrayList<PushResult>();
		PushPayload pp= pushTemplate.buildPushObject_all_alias_alert(businessId,businessType,title,content);
		PushResult r = pushTemplate.init(pp);
		l.add(r);
		return ResultUtil.getSlefSRSuccessList(l);
		
	}
	/**
     * 构建推送对象：acountId，推送目标是别名为 "acountId"，通知内容为 content。
     * @param acountId 业务接受者
     * @param businessId 业务Id
     * @param businessType 业务类型
     * @param content 内容
     * @return
     */
	@RequestMapping(value={"/sendAlert"})
	public StateResultList sendAlert(
			@RequestParam("acountId") Integer acountId ,
			@RequestParam("businessId") Integer businessId ,
			@RequestParam("title") String title ,
			@RequestParam("businessType") String businessType ,
			@RequestParam("content") String content,
			HttpSession session
			) throws APIConnectionException, APIRequestException{
		List<PushResult> l=new ArrayList<PushResult>();
		PushPayload pp= pushTemplate.buildPushObject_id_alias_alert(acountId,businessId,businessType,title, content);
		PushResult r = pushTemplate.init(pp);
		l.add(r);
		return ResultUtil.getSlefSRSuccessList(l);
		
	}
	/**
	 * buildPushObject_all_all_message
	 * 所有消息
	 * @return
	 * @throws APIRequestException 
	 * @throws APIConnectionException 
	 */
	@RequestMapping(value={"/test/sendAllMessage"})
	public StateResultList testSendMessage(
			@RequestParam("businessId") Integer businessId ,
			@RequestParam("title") String title ,
			@RequestParam("businessType") String businessType ,
			@RequestParam("content") String content,
			HttpSession session
			) throws APIConnectionException, APIRequestException{
		List<PushResult> l=new ArrayList<PushResult>();
			PushPayload pp= pushTemplate.buildPushObject_all_alias_message(businessId,businessType,title,content);
			PushResult r = pushTemplate.init(pp);
			l.add(r);
			return ResultUtil.getSlefSRSuccessList(l);
		
	}
	/*public static void main(String[] args) throws Exception {
		String content = HttpClientUtil.doGet("http://www.newzhuan.com/article/list?pageSize=1");
		String url="http://192.168.7.111/push/sendMessage?auth=f57bf16dbc05fe4f6a2e29ddf6bdc0a4&content="+content;
		//http://192.168.7.111/push/sendMessage?auth=f57bf16dbc05fe4f6a2e29ddf6bdc0a4&content={"code":200,"msg":"成功","list":[{"articleId":1314,"type":"生活","title":"泰国人的排队方式太绝了！看一次笑一次哈哈哈哈哈","isRecommend":0,"fixedRecommend":0,"redirectUrl":null,"content":null,"model":"CPC","userUnitPrice":0.02,"unitPrice":0.2,"totalPrice":200000.0,"turnNumber":0,"readingNumber":0,"userNowTotalPrice":0.0,"nowTotalPrice":0.0,"scale":0.0,"pvs":0,"uvs":0,"ips":0,"status":"正常","createDate":"2017-07-27 11:22:44","updateDate":"2017-07-27 11:22:44","acountId":1000,"imgList":[{"imgId":522383,"imgAddress":"http://img.newzhuan.com/uploaderPath/img/20170727/1501124967QQ截图20170727110720_meitu_17.jpg","number":1,"articleId":1314}]}]}
		String url2="http://192.168.7.111/push/test/sendMessage?content="+content;
		//http://192.168.7.111/push/test/sendMessage?content={"code":200,"msg":"成功","list":[{"articleId":1314,"type":"生活","title":"泰国人的排队方式太绝了！看一次笑一次哈哈哈哈哈","isRecommend":0,"fixedRecommend":0,"redirectUrl":null,"content":null,"model":"CPC","userUnitPrice":0.02,"unitPrice":0.2,"totalPrice":200000.0,"turnNumber":0,"readingNumber":0,"userNowTotalPrice":0.0,"nowTotalPrice":0.0,"scale":0.0,"pvs":0,"uvs":0,"ips":0,"status":"正常","createDate":"2017-07-27 11:22:44","updateDate":"2017-07-27 11:22:44","acountId":1000,"imgList":[{"imgId":522383,"imgAddress":"http://img.newzhuan.com/uploaderPath/img/20170727/1501124967QQ截图20170727110720_meitu_17.jpg","number":1,"articleId":1314}]}]}
		System.out.println(url2);
	}*/
}
