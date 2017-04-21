package com.ymhou.async.handler;

import com.ymhou.async.EventHandler;
import com.ymhou.async.EventModel;
import com.ymhou.async.EventType;
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
 * Created by ymhou on 2017/4/6.
 */
@Component
public class LikeHandler implements EventHandler{
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
        message.setContent("用户"+user.getName()+"赞了你的评论,http://localhost:8080/question/"+eventModel.getExts("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
