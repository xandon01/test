package com.lvdou.application.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lwb3372
 * 
 *   登录
 */
@Controller
public class LoginController {
	Logger log=LoggerFactory.getLogger(LoginController.class);

	@Autowired
	RedisTemplate redisTemplate;
	
	/**
	 * 登录
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/loginSubmit")
	@ResponseBody
	public String loginSubmit(@RequestParam("phone")String phone,HttpServletRequest request) {
		log.info("phone="+phone);
		Object val=redisTemplate.opsForValue().get("user"+phone);
		if(val!=null){
			HttpSession session=request.getSession();
			session.setAttribute("_user",phone);
			return "ok";
		}else{
			return "fail";
		}
	}

}
