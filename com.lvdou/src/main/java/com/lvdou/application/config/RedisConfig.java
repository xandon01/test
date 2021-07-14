package com.lvdou.application.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class RedisConfig {
	@Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> template(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);
        
        StringRedisSerializer keySerial = new StringRedisSerializer();
        
        Jackson2JsonRedisSerializer<Object> valSeial = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        valSeial.setObjectMapper(om);
        
        template.setKeySerializer(keySerial);
        template.setValueSerializer(valSeial);
        template.setHashKeySerializer(keySerial);
        template.setHashValueSerializer(valSeial);
        template.afterPropertiesSet();
        return template;
    }
}
