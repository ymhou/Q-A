package com.ymhou.async.handler;

import com.ymhou.async.EventHandler;
import com.ymhou.async.EventModel;
import com.ymhou.async.EventType;
import com.ymhou.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ymhou on 2017/4/7.
 */
@Component
public class LoginExceptionHandler implements EventHandler{
    @Autowired
    MailSender mailSender;


    @Override
    public void doHandler(EventModel eventModel) {
        Map<String ,Object> map = new HashMap<>();
        map.put("username",eventModel.getExts("username"));
        mailSender.sendWithHTMLTemplate(eventModel.getExts("email"),"登录异常","mails/login_exception.html",map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
