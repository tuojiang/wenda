package com.nowcoder.controller;

import com.nowcoder.model.*;
import com.nowcoder.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: wenda
 * @Date: 2018/8/30
 * @Author: chandler
 * @Description:
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionReviewService questionReviewService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    private static int base = 20;

    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    private List<ViewObject> getReviewQuestions(int userId, int offset, int limit) {
        List<QuestionReview> questionList = questionReviewService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (QuestionReview questionReview : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", questionReview);
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionReview.getId()));
            vo.set("user", userService.getUser(questionReview.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 审核模块
     * @param model
     * @param pop
     * @return
     */
    @RequestMapping(path = {"/reviewquestion"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String reviewQuestionIndex(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getReviewQuestions(0, 0, 10));
        if (hostHolder.getUser() != null) {
            model.addAttribute("knownid", hostHolder.getUser().getId());
        }
        return "reviewquestionindex";
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        if (hostHolder.getUser() != null) {
                model.addAttribute("knownid", hostHolder.getUser().getId());
        }
        return "index";
    }

    /**
     * 加载更多
     * @param model
     * @param pop
     * @return
     */
    @RequestMapping(path = {"/reload"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String reloadMore(Model model,
                        @RequestParam(value = "pop", defaultValue = "10") int pop) {
        base +=pop;
        model.addAttribute("vos", getQuestions(0, 0, base));
        if (hostHolder.getUser() != null) {
            model.addAttribute("knownid", hostHolder.getUser().getId());
        }
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        User user = userService.getUser(userId);
        ViewObject vo = new ViewObject();
        vo.set("user",user);
        vo.set("commentCount",commentService.getUserCommentCount(userId));
        vo.set("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,userId));
        vo.set("followeeCount",followService.getFolloweeCount(userId,EntityType.ENTITY_USER));
        if (hostHolder.getUser()!=null){
            vo.set("followed",followService.isFollower(hostHolder.getUser().getId(),EntityType.ENTITY_USER,userId));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser",vo);
        return "profile";
    }
}
