package com.lvdou.application.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lvdou.application.service.FabricService;

/**
 * @author lwb3372
 * 
 * 
 */
@Controller
public class AccountController {
	Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	RedisTemplate redisTemplate;
	
	FabricService fabricService=FabricService.getInstance();

	@RequestMapping("/account")
	@ResponseBody
	public Map account(HttpServletRequest request) {
		Object phone = request.getSession().getAttribute("_user");
		log.info("phone=" + phone);
		try {
			// 查询余额from fabric
			int bal=fabricService.QueryBal(phone.toString());

			// 查询交易 from redis
			List<String> trs = (List) redisTemplate.opsForList().range("trs" + phone, 0, -1);

			Map retMap = new HashMap();
			retMap.put("bal", bal);
			retMap.put("trs", trs);

			log.info(retMap.toString());
			return retMap;
		} catch (Exception e) {
			log.error("exception", e);
			return null;
		}
	}
	

	@RequestMapping("/transerInit")
	@ResponseBody
	public String transerInit(HttpServletRequest request) {
		Object phone = request.getSession().getAttribute("_user");
		log.info("phone=" + phone);
		return phone.toString();
	}

	@RequestMapping("/transer")
	@ResponseBody
	public synchronized String transer(@RequestParam("rec") String rec, @RequestParam("amt") Integer amt,
			HttpServletRequest request) {
		try {
			Object phone = request.getSession().getAttribute("_user");
			log.info("phone=" + phone);
			
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String dateStr = format.format(date);

			// 1 付款方交易记录
			StringBuffer sbf = new StringBuffer();
			sbf.append(dateStr).append(" Transer bean to ").append(rec).append(",lost ").append(amt).append(" bean");
			redisTemplate.opsForList().leftPush("trs" + phone, sbf.toString());

			// 2  收款方交易记录
			sbf = new StringBuffer();
			sbf.append(dateStr).append(" Receive bean from ").append(phone).append(",get").append(amt).append(" bean");
			redisTemplate.opsForList().leftPush("trs" + rec, sbf.toString());
			
			// 3  余额上链,调用智能合约
			try{
				fabricService.Transer(phone.toString(), rec, amt.intValue());
			}catch(Exception e){
				log.error("exception",e);
			}
			
			return "ok";
		} catch (Exception e) {
			log.error("exception", e);
			return "fail";
		}

	}
	
	
	@RequestMapping("/paybean")
	@ResponseBody
	public synchronized String paybean(@RequestParam("amt") Integer amt,
			HttpServletRequest request) {
		try {
			Object phone = request.getSession().getAttribute("_user");
			log.info("phone=" + phone);
			
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String dateStr = format.format(date);

			// 1 付款方交易记录
			StringBuffer sbf = new StringBuffer();
			sbf.append(dateStr).append(" Buy BOC fund in BOC,lost ").append(amt).append(" bean");
			redisTemplate.opsForList().leftPush("trs" + phone, sbf.toString());

			// 3  余额上链,调用智能合约
			try{
				fabricService.Decrease(phone.toString(), amt.intValue());
			}catch(Exception e){
				log.error("exception",e);
			}
			
			return "ok";
		} catch (Exception e) {
			log.error("exception", e);
			return "fail";
		}

	}
}
