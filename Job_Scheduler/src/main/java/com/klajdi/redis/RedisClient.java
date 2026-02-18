package com.klajdi.redis;

import redis.clients.jedis.Jedis;

public class RedisClient {

    private Jedis jedis;

    public RedisClient() {
        this.jedis = new Jedis("localhost", 6379);
        System.out.println("Connected to Redis!");
    }

    public Jedis getConnection() {
        return jedis;
    }
}
