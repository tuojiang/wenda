package com.nowcoder.service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 18-9-3
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public List<Comment> selectByEntity(int entityId, int entityType){
        return commentDAO.selectByEntity(entityId,entityType);
    }

    public void deleteComment(int entityId, int entityType) {
        commentDAO.updateStatus(entityId, entityType, 1);
    }

    public int getCommentCount(int entityId,int entityType,int offset,int limit){
        return commentDAO.getCommentCount(entityId,entityType,offset,limit);
    }

    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }

    public Comment getCommentById(int commentId) {
        return commentDAO.getCommentById(commentId);
    }
}
