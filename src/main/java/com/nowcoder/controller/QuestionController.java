package com.nowcoder.controller;

import com.nowcoder.model.*;
import com.nowcoder.service.*;
import com.nowcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date: 18-9-3
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Controller
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionReviewService questionReviewService;
    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowService followService;

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getQuestionById(qid);
        model.addAttribute("question", question);
        List<Comment> commentList = commentService.selectByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            if (hostHolder.getUser() == null) {
                vo.set("liked", 0);
            } else {
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }
            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));
            vo.set("user", userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments", vos);

        List<ViewObject> followUsers = new ArrayList<>();
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION, qid, 20);
        for (Integer userId : users) {
            ViewObject vo = new ViewObject();
            User user = userService.getUser(userId);
            if (user == null) {
                continue;
            }
            vo.set("name", user.getName());
            vo.set("headUrl", user.getHeadUrl());
            vo.set("id", user.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers", followUsers);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, qid));
        } else {
            model.addAttribute("followed", false);
        }

        return "detail";
    }

    @RequestMapping(value = "/question/edit/{qid}", method = {RequestMethod.GET})
    public String editQuestion(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getQuestionById(qid);

        model.addAttribute("question", question);

        return "editquestion";
    }

    @RequestMapping(path = {"/question/review"}, method = {RequestMethod.POST})
    public String reviewQuestion(@RequestParam("questionId") int questionId,
                                 @RequestParam("commentCount") int commentCount,
                                 @RequestParam("title") String title,
                                 @RequestParam("content") String content) {
        try {
            QuestionReview questionReviewold = questionReviewService.getQuestionById(questionId);
            if (questionReviewold == null) {

            }
            QuestionReview questionReview = new QuestionReview();
            questionReview.setQuestionId(questionId);
            questionReview.setTitle(title);
            questionReview.setContent(content);
            questionReview.setCreatedDate(new Date());
            questionReview.setCommentCount(commentCount);
            questionReview.setHasRead(0);
            if (hostHolder.getUser() == null) {
                questionReview.setUserId(WendaUtil.ANONYMOUS_USERID);
            } else {
                questionReview.setUserId(hostHolder.getUser().getId());
            }
            if (questionReviewService.addQuestion(questionReview) > 0) {
                return "redirect:/";
            }
        } catch (Exception e) {
            log.error("更新题目失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");
    }

    @RequestMapping(value = "/question/addpass", method = {RequestMethod.POST})
    public String addPassQuestion(@RequestParam("userId") int userId,
                                  @RequestParam("commentCount") int commentCount,
                                  @RequestParam("questionId") int questionId,
                                  @RequestParam("title") String title,
                                  @RequestParam("content") String content) {
        try {
            Question question = questionService.getQuestionById(questionId);
            if (question != null) {
                question.setTitle(title);
                question.setContent(content);
                question.setCreatedDate(new Date());
                question.setUserId(userId);
                question.setCommentCount(commentCount);
            } else {
                return WendaUtil.getJSONString(1, "问题不存在");
            }

            if (questionService.updateQuestion(question) > 0) {
                //评论后删除review表中旧的数据
                if (questionReviewService.deleteQuestion(questionId) > 0) {
                    return "redirect:/";
                }
            }
        } catch (Exception e) {
            log.error("更新题目失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");
    }

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCreatedDate(new Date());
            if (hostHolder.getUser() == null) {
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
            } else {
                question.setUserId(hostHolder.getUser().getId());

            }
            if (questionService.addQuestion(question) > 0) {
                return WendaUtil.getJSONString(0);
            }
        } catch (Exception e) {
            log.error("增加题目失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");
    }


}
