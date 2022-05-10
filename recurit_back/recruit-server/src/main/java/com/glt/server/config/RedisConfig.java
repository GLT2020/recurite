package com.glt.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        为string类型的   key    设置序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        为String类型的   value  设置序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());;
//        为hash类型的  key  设置序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 为hash类型的  value  设置序列化
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    //哨兵的配置
//    @Bean
//    public RedisSentinelConfiguration redisSentinelConfiguration(){
//        RedisSentinelConfiguration redisSentinelConfiguration =
//                new RedisSentinelConfiguration()
//                        //主节点名称
//                        .master("mymaster")
//                        //哨兵
//                        .sentinel("127.0.0.1",26379)
//                        .sentinel("127.0.0.1",26380)
//                        .sentinel("127.0.0.1",26381);
//        redisSentinelConfiguration.setPassword("123456");
//        return redisSentinelConfiguration;
//
//    }
}
