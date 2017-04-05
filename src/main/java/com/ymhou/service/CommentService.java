package com.ymhou.service;

import com.ymhou.dao.CommentDao;
import com.ymhou.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ymhou on 2017/4/3.
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDao.selectByEntity(entityId, entityType);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDao.getCommentCount(entityId, entityType);
    }

    public void deleteComment(int entityId, int entityType) {
        commentDao.updateStatus(entityId, entityType, 1);
    }

    public Comment getCommentById(int id) {
        return commentDao.selectById(id);
    }
}
