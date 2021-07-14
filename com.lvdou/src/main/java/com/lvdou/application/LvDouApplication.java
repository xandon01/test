package com.lvdou.application;


import java.util.concurrent.TimeoutException;

import org.hyperledger.fabric.gateway.ContractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lvdou.application.service.FabricService;

/**
 * @author lwb3372
 * 
 *         绿豆App启动类 (账户，交易数据存入redis， 绿豆余额上链)
 */
@SpringBootApplication
@Controller
public class LvDouApplication {
	Logger log = LoggerFactory.getLogger(LvDouApplication.class);

	@Autowired
	RedisTemplate redisTemplate;
	
	FabricService fabricService=FabricService.getInstance();


	@RequestMapping("/admin/init")
	@ResponseBody
	public String initRedis() {
		log.info("initRedis");
		try {
			insertUser();
			initLedger();
			insertTrs();
			return "init success!!!";
		} catch (Exception e) {
			log.error("发生异常",e);
			return e.getMessage();
		}

	}

	@RequestMapping("/admin/clear")
	@ResponseBody
	public String clearRedis() {
		log.info("clearRedis");
		try {
			clearAll();
			return "clear success!!!";
		} catch (Exception e) {
			log.error("发生异常",e);
			return e.getMessage();
		}

	}

	// 启动
	public static void main(String[] args) {
		SpringApplication.run(LvDouApplication.class, args);
	}

	// 初始化数据
	private void insertUser() {
		redisTemplate.opsForValue().set("user123", 123);
		redisTemplate.opsForValue().set("user456", 456);
	}

	private void initLedger() {
		try {
			fabricService.InitLedger();
		} catch (ContractException e) {
		} catch (TimeoutException e) {
		} catch (InterruptedException e) {
		}
	}
	
	private void insertTrs() {
		redisTemplate.opsForList().leftPush("trs123", "20210627 Buy air conditioning in Jingdong,get 700 bean");
		redisTemplate.opsForList().leftPush("trs123", "20210627 Buy Vegetables in meituan,get 200 bean");
		redisTemplate.opsForList().leftPush("trs123", "20210627 Buy clothes in tianmao,get 100 bean");
		
		redisTemplate.opsForList().leftPush("trs456", "20210627 Buy air conditioning in Jingdong,get 700 bean");
		redisTemplate.opsForList().leftPush("trs456", "20210627 Buy Vegetables in meituan,get 200 bean");
		redisTemplate.opsForList().leftPush("trs456", "20210627 Buy clothes in tianmao,get 100 bean");
	}
	
	//清空
	private void clearAll() {
		 redisTemplate.getConnectionFactory().getConnection().flushAll();
	}
}
