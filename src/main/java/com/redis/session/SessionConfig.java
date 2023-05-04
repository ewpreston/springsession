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
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName("epreston.eastus2.redisenterprise.cache.azure.net");
        standaloneConfiguration.setPort(10000);
        standaloneConfiguration.setPassword("U9V+4VvY6Rq93m5xWGDdyqEjIz1Shslvx3yIVjRKUIg=");
        LettuceConnectionFactory lettuceConFactory = new LettuceConnectionFactory(standaloneConfiguration);
        return lettuceConFactory;
    }
}
