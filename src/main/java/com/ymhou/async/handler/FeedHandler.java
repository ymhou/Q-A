package com.ymhou.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.ymhou.async.EventHandler;
import com.ymhou.async.EventModel;
import com.ymhou.async.EventType;
import com.ymhou.model.EntityType;
import com.ymhou.model.Feed;
import com.ymhou.model.Question;
import com.ymhou.model.User;
import com.ymhou.service.FeedService;
import com.ymhou.service.FollowService;
import com.ymhou.service.QuestionService;
import com.ymhou.service.UserService;
import com.ymhou.util.JedisAdapter;
import com.ymhou.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.*;

/**
 * Created by ymhou on 2017/4/10.
 */
@Component
public class FeedHandler implements EventHandler {
    @Autowired
    FeedService feedService;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    JedisAdapter jedisAdapter;

    private String buildFeedData(EventModel eventModel) {
        Map<String, String> map = new HashMap<>();
        User actor = userService.getUser(eventModel.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", actor.getId() + "");
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (eventModel.getType() == EventType.COMMENT ||
                (eventModel.getType() == EventType.FOLLOW && eventModel.getEntityType() == EntityType.ENTITY_QUESTION)) {
            Question question = questionService.getById(eventModel.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandler(EventModel eventModel) {
        //为了测试，把model的userId随机一下
//        Random r = new Random();
//        eventModel.setActorId(2 + r.nextInt(10));

        //构造一个新鲜事
        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setUserId(eventModel.getActorId());
        feed.setType(eventModel.getType().getValue());
        feed.setData(buildFeedData(eventModel));
        if (feed.getData() == null) {
            return;
        }
        feedService.addFeed(feed);

        //获取所有粉丝
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER,eventModel.getActorId(),Integer.MAX_VALUE);
        //系统队列
        followers.add(0);
        //给事件的粉丝推
        for(int follower:followers){
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey,String.valueOf(feed.getId()));
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }
}
