package com.ymhou.util;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

/**
 * Created by ymhou on 2017/4/2.
 */
public class JedisAdapter {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        //get set
        jedis.set("hello","world");
        print(1,jedis.get("hello"));
        jedis.setex("hello2",10,"world");
        print(2,jedis.get("hello2"));

        jedis.set("pv","100");
        jedis.incr("pv");
        jedis.incrBy("pv",5);
        jedis.decr("pv");
        jedis.decrBy("pv",2);
        print(3,jedis.get("pv"));

        print(4,jedis.keys("*"));


        //list
        for(int i=0; i<10; i++){
            jedis.lpush("list","a"+i);
        }
        print(5,jedis.lrange("list",0,10));
        print(5,jedis.lindex("list",3));
        print(6,jedis.llen("list"));
        print(7,jedis.lpop("list"));
        print(8,jedis.linsert("list", BinaryClient.LIST_POSITION.AFTER,"a4","66"));
        print(9,jedis.lrange("list",0,10));

        //hash


    }

    public static void print(int index,Object object){
        System.out.println(String.format("%d, %s",index,object.toString()));
    }
}
