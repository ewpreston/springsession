package com.redis.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SessionApplicationTests {
    private Jedis jedis;
    private TestRestTemplate testRestTemplate;
    private TestRestTemplate testRestTemplateWithAuth;
    private String testUrl = "http://epreston.io:8080/";
    private String redisHost = System.getenv().getOrDefault("REDIS_HOST", "localhost");
    private String redisPort = System.getenv().getOrDefault("REDIS_PORT", "6379");
    private String redisPassword = System.getenv().getOrDefault("REDIS_PASSWORD", null);

    @BeforeEach
    public void clearRedisData() {
        testRestTemplate = new TestRestTemplate();
        testRestTemplateWithAuth = new TestRestTemplate("admin", "password", (TestRestTemplate.HttpClientOption) null);

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .password(redisPassword)
                .build();
        jedis = new Jedis(redisHost, Integer.parseInt(redisPort), config);
        jedis.flushAll();
    }

    @Test
    public void testRedisIsEmpty() {
        Set<String> result = jedis.keys("*");
        assertEquals(0, result.size());
    }

    @Test
    public void testUnauthenticatedCantAccess() {
        ResponseEntity<String> result = testRestTemplate.getForEntity(testUrl, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Disabled
    public void testRedisControlsSession() {
        ResponseEntity<String> result = testRestTemplateWithAuth.getForEntity(testUrl, String.class);
        assertEquals("Hello admin", result.getBody()); //login worked

        Set<String> redisResult = jedis.keys("*");
        assertTrue(redisResult.size() > 0); //redis is populated with session data

        String sessionCookie = result.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        result = testRestTemplate.exchange(testUrl, HttpMethod.GET, httpEntity, String.class);
        assertEquals("hello admin", result.getBody()); //access with session worked

        jedis.flushAll(); //clear all keys in redis

        result = testRestTemplate.exchange(testUrl, HttpMethod.GET, httpEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        //access denied after sessions are removed in redis
    }
}
