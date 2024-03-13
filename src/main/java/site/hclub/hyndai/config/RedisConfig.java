package site.hclub.hyndai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
/**
 * @author 이혜연
 * @description: Redis 설정 파일
 * ===========================
        AUTHOR      NOTE
 * ---------------------------
 *    이혜연        최초생성
 * ===========================
 */
@Configuration
public class
RedisConfig {

    @Value("${SPRING-DATA-REDIS-HOST}")
    private String address;

    @Value("${SPRING-DATA-REDIS-PASSWORD}")
    private String password;


    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(30);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxWaitMillis(3000);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(address);
        connectionFactory.setPort(6379);
        connectionFactory.getPoolConfig().setMaxTotal(20000);
        connectionFactory.getPoolConfig().setMaxIdle(20000);

        connectionFactory.setPassword(password);
        connectionFactory.setUsePool(true);
        connectionFactory.setPoolConfig(jedisPoolConfig);
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