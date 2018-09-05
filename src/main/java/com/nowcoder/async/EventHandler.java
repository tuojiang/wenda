package com.nowcoder.async;

import java.util.List;

/**
 * @Date: 18-9-5
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
public interface EventHandler {
    void doHandle(EventModel eventModel);
    List<EventType> getSupportEventTypes();
}
