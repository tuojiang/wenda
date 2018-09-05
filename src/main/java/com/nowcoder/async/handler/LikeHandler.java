package com.nowcoder.async.handler;

import com.nowcoder.async.EventHandler;
import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 18-9-5
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Component
public class LikeHandler implements EventHandler {



    @Override
    public void doHandle(EventModel eventModel) {

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return null;
    }
}
