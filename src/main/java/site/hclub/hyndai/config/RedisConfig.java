package site.hclub.hyndai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${SPRING-DATA-REDIS-HOST}")
    private String address;

    @Value("${SPRING-DATA-REDIS-PASSWORD}")
    private String password;


    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(address);
        connectionFactory.setPort(6379);
        connectionFactory.setPassword(password);
        connectionFactory.getPoolConfig().setMaxTotal(1000);
        connectionFactory.getPoolConfig().setMaxIdle(1000);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}