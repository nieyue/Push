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
import com.nieyue.util.MyDESutil;
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
	@RequestMapping(value={"/getSession"})
	public String getSession(
			HttpSession session,
			HttpServletResponse response
			){
		return session.getId();
		
	}
	/**
	 * buildPushObject_all_all_message
	 * 所有推送
	 * @return
	 * @throws APIRequestException 
	 * @throws APIConnectionException 
	 */
	@RequestMapping(value={"/sendMessage"})
	public StateResultList sendMessage(
			@RequestParam("auth") String auth,
			@RequestParam("content") String content,
			HttpSession session
			) throws APIConnectionException, APIRequestException{
		List<PushResult> l=new ArrayList<PushResult>();
		if(auth.equals(MyDESutil.getMD5(1000))){
			PushPayload pp= pushTemplate.buildPushObject_all_all_message(content);
			PushResult r = pushTemplate.init(pp);
			l.add(r);
		return ResultUtil.getSlefSRSuccessList(l);
		}
		return ResultUtil.getSlefSRFailList(l);
		
	}
	/**
	 * buildPushObject_all_all_message
	 * 所有推送
	 * @return
	 * @throws APIRequestException 
	 * @throws APIConnectionException 
	 */
	@RequestMapping(value={"/test/sendMessage"})
	public StateResultList testSendMessage(
			@RequestParam("content") String content,
			HttpSession session
			) throws APIConnectionException, APIRequestException{
		List<PushResult> l=new ArrayList<PushResult>();
			PushPayload pp= pushTemplate.buildPushObject_all_all_message(content);
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
