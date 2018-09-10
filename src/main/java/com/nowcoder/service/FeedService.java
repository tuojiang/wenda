package com.nowcoder.service;

import com.nowcoder.dao.FeedDAO;
import com.nowcoder.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 18-9-10
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Service
public class FeedService {

    @Autowired
    FeedDAO feedDAO;

    public boolean addFeed(Feed feed){
        feedDAO.addFeed(feed);
        return feed.getId()>0;
    }

    public Feed getFeedById(int id){
        return feedDAO.getFeedById(id);
    }

    public List<Feed> selectUserFeeds(int maxId,List<Integer> userIds,int count){
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }
}
