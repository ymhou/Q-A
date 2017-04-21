package com.ymhou.async.handler;

import com.ymhou.async.EventHandler;
import com.ymhou.async.EventModel;
import com.ymhou.async.EventType;
import com.ymhou.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ymhou on 2017/4/21.
 */
@Component
public class AddQuestionHandler implements EventHandler{
    @Autowired
    SearchService searchService;


    @Override
    public void doHandler(EventModel eventModel) {
        try{
            searchService.indexQuestion(eventModel.getEntityId(),eventModel.getExts("title"),eventModel.getExts("content"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
