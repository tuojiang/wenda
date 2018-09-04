package com.nowcoder.controller;

import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.SensitiveService;
import com.nowcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.swing.text.html.HTML;
import java.util.Date;

/**
 * @Date: 18-9-4
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Controller
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    SensitiveService sensitiveService;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);
            Comment comment = new Comment();
            if (hostHolder.getUser() == null) {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            } else {
                comment.setUserId(hostHolder.getUser().getId());
            }
            comment.setCreatedDate(new Date());
            comment.setContent(content);
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setStatus(0);
            commentService.addComment(comment);
            // 更新题目里的评论数量
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType(),0,10);
            questionService.updateCommentCount(comment.getEntityId(), count);

        } catch (Exception e) {
            log.error("增加评论失败", e.getMessage());
        }
        return "redirect:/question/" + String.valueOf(questionId);
    }
}
