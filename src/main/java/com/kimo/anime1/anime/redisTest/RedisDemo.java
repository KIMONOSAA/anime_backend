package com.kimo.anime1.anime.redisTest;

import redis.clients.jedis.Jedis;

public class RedisDemo {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.91.132",6379);

        String password = jedis.auth("123456");
        if(!"OK".equals(password)) {
            jedis.close();
            return;
        }

        String value = jedis.ping();


        jedis.close();
    }

}
