package com.ymhou.async;

import java.util.List;

/**
 * Created by ymhou on 2017/4/6.
 */
public interface EventHandler {
    void doHandler(EventModel eventModel);

    List<EventType> getSupportEventTypes();
}
