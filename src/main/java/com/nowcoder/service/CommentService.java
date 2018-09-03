package com.nowcoder.service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.model.Comment;
import org.apache.ibatis.annotations.Param;
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
}
