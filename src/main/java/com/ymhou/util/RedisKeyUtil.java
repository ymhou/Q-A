package com.ymhou.util;

/**
 * Created by ymhou on 2017/4/5.
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";

    public static String getLikeKey(int entityType,int entityId){
        return BIZ_LIKE+SPLIT+entityType+SPLIT+entityId;
    }

    public static String getDisLikeKey(int entityType,int entityId){
        return BIZ_DISLIKE+SPLIT+entityType+SPLIT+entityId;
    }
}
