package com.ymhou;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymhou.model.User;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * Created by ymhou on 2017/4/4.
 */
public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        //get set
        jedis.set("hello", "world");
        print(1, jedis.get("hello"));
        jedis.setex("hello2", 10, "world");
        print(2, jedis.get("hello2"));

        jedis.set("pv", "100");
        jedis.incr("pv");
        jedis.incrBy("pv", 5);
        jedis.decr("pv");
        jedis.decrBy("pv", 2);
        print(3, jedis.get("pv"));

        print(4, jedis.keys("*"));


        //list
        for (int i = 0; i < 10; i++) {
            jedis.lpush("list", "a" + i);
        }
        print(5, jedis.lrange("list", 0, 10));
        print(5, jedis.lindex("list", 3));
        print(6, jedis.llen("list"));
        print(7, jedis.lpop("list"));
        print(8, jedis.linsert("list", BinaryClient.LIST_POSITION.AFTER, "a4", "66"));
        print(9, jedis.lrange("list", 0, 10));

        //hash
        String userKey = "userXX";
        jedis.hset(userKey, "name", "hym");
        jedis.hset(userKey, "sex", "man");
        jedis.hset(userKey, "phone", "123456");
        print(10, jedis.hget(userKey, "name"));
        print(11, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(12, jedis.hgetAll(userKey));
        print(13, jedis.hexists(userKey, "email"));
        print(14, jedis.hexists(userKey, "name"));
        jedis.hsetnx(userKey, "schoool", "neu");
        jedis.hsetnx(userKey, "name", "my");
        print(15, jedis.hgetAll(userKey));

        //set
        String likeKey1 = "like1";
        String likeKey2 = "like2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(likeKey1, i + "");
            jedis.sadd(likeKey2, i * i + "");
        }
        print(16, jedis.smembers(likeKey1));
        print(17, jedis.smembers(likeKey2));
        print(18, jedis.sunion(likeKey1, likeKey2));
        print(19, jedis.sdiff(likeKey1, likeKey2));
        print(20, jedis.sinter(likeKey1, likeKey2));
        print(21, jedis.sismember(likeKey1, "22"));
        print(22, jedis.sismember(likeKey2, "64"));
        print(23, jedis.scard(likeKey1));
        print(24, jedis.srem(likeKey1, "4"));
        print(25, jedis.srandmember(likeKey1));
        print(26, jedis.smembers(likeKey1));

        //sorted sets
        String rankKey = "key";
        jedis.zadd(rankKey, 15, "tom");
        jedis.zadd(rankKey, 30, "jim");
        jedis.zadd(rankKey, 61, "mary");
        jedis.zadd(rankKey, 80, "gogo");
        jedis.zadd(rankKey, 90, "bob");
        print(27, jedis.zcard(rankKey));
        print(28, jedis.zcount(rankKey, 60, 100));
        print(29, jedis.zscore(rankKey, "bob"));
        jedis.zincrby(rankKey, 2, "bob");
        print(30, jedis.zscore(rankKey, "bob"));
        jedis.zincrby(rankKey, 20, "jjj");
        print(31, jedis.zscore(rankKey, "jjj"));
        print(32, jedis.zrange(rankKey, 60, 100));
        print(33, jedis.zrange(rankKey, 0, 30));
        print(34, jedis.zrange(rankKey, 1, 3));
        print(35, jedis.zrevrange(rankKey, 0, 3));
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "60", "100")) {
            print(37, tuple.getElement() + ":" + tuple.getScore());
        }
        print(38, jedis.zrank(rankKey, "bob"));
        print(39, jedis.zrevrank(rankKey, "bob"));

        String setKry = "setKey";
        jedis.zadd(setKry, 1, "a");
        jedis.zadd(setKry, 1, "b");
        jedis.zadd(setKry, 1, "c");
        jedis.zadd(setKry, 1, "d");
        jedis.zadd(setKry, 1, "e");
        print(40, jedis.zlexcount(setKry, "-", "+"));
        print(41, jedis.zlexcount(setKry, "(b", "[d"));
        print(42, jedis.zlexcount(setKry, "[b", "[d"));
        print(43, jedis.zrange(setKry, 0, 10));
        print(44, jedis.zremrangeByLex(setKry, "(c", "+"));
        print(45, jedis.zrange(setKry, 0, 10));

        //连接池
//        JedisPool pool = new JedisPool();
//        for(int i=0; i<100; i++){
//            Jedis j = pool.getResource();
//            print(46,j.get("pv"));
//            j.close();
//        }

        //做缓存
        User user = new User();
        user.setName("hym");
        user.setHeadUrl("aaa");
        user.setPassword("ppp");
        user.setSalt("111");
        user.setId(2);
        print(47, JSONObject.toJSONString(user));
        jedis.set("user1", JSONObject.toJSONString(user));

        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value,User.class);
        print(48,user2);


    }

    public static void print(int index, Object object) {
        System.out.println(String.format("%d, %s", index, object.toString()));
    }
}
