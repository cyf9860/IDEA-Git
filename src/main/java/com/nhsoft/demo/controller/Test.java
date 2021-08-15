package com.nhsoft.demo.controller;

import com.nhsoft.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private static StringRedisTemplate stringRedisTemplate;

    private ConcurrentHashMap branchs = new ConcurrentHashMap();
    private Employee employee;

    @PostConstruct
    private void init() throws Exception {
        branchs.put("1", "门店1");
        branchs.put("2", "门店2");
        branchs.put("3", "门店3");
        System.out.println("数据初始化完成");
    }

}
