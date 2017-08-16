package com.nieyue.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nieyue.exception.TestApiException;
import com.nieyue.util.MyDESutil;

/**
 * 用户session控制拦截器
 * @author yy
 *
 */
@Configuration
public class TestApiControllerInterceptor implements HandlerInterceptor {

	
	@Value("${myPugin.testApi}")
	boolean testApi;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	
		
		//如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
       //如果是测试接口
       if(request.getRequestURL().indexOf("test")>-1){
    	   if(testApi){
    		   return true;
    	   }
    	   
       }
       //如果是内部授权
       if(request.getParameter("auth").equals(MyDESutil.getMD5(1000))){
    	   return true;
       }
       throw new TestApiException();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
