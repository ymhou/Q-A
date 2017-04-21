package com.ymhou.service;

import com.ymhou.dao.FeedDao;
import com.ymhou.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ymhou on 2017/4/10.
 */
@Service
public class FeedService {
    @Autowired
    FeedDao feedDao;

    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int offset, int limit) {
        return feedDao.selectUserFeeds(maxId, userIds, offset, limit);
    }

    public boolean addFeed(Feed feed) {
        return feedDao.addFeed(feed) > 0;
    }

    public Feed getById(int id) {
        return feedDao.getFeedById(id);
    }

}
