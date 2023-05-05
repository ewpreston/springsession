package com.redis.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@EnableRedisHttpSession
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {
    private String host = System.getenv().getOrDefault("REDIS_HOST", "localhost");
    private String port = System.getenv().getOrDefault("REDIS_PORT", "6379");
    private String password = System.getenv().getOrDefault("REDIS_PASSWORD", null);

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(host);
        standaloneConfiguration.setPort(Integer.parseInt(port));
        standaloneConfiguration.setPassword(password);
        LettuceConnectionFactory lettuceConFactory = new LettuceConnectionFactory(standaloneConfiguration);
        return lettuceConFactory;
    }
}
