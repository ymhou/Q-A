package com.ymhou.async.handler;

import com.ymhou.async.EventHandler;
import com.ymhou.async.EventModel;
import com.ymhou.async.EventType;
import com.ymhou.model.EntityType;
import com.ymhou.model.Message;
import com.ymhou.model.User;
import com.ymhou.service.MessageService;
import com.ymhou.service.UserService;
import com.ymhou.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ymhou on 2017/4/9.
 */
@Component
public class FollowHandler implements EventHandler{
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(eventModel.getActorId());

        if(eventModel.getEntityType() == EntityType.ENTITY_QUESTION){
            message.setContent("用户"+user.getName()+"关注了你的问题:http://localhost:8080/question/"+eventModel.getEntityId());
        }else if(eventModel.getEntityType() == EntityType.ENTITY_USER){
            message.setContent("用户"+user.getName()+"关注了你，http://localhost:8080/user/"+eventModel.getEntityId());
        }

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
