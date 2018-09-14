package com.nowcoder.service;

import com.nowcoder.dao.QuestionReviewDAO;
import com.nowcoder.model.QuestionReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @program: wenda
 * @Date: 2018/8/30
 * @Author: chandler
 * @Description:
 */
@Service
public class QuestionReviewService {
    @Autowired
    QuestionReviewDAO questionReviewDao;

    @Autowired
    SensitiveService sensitiveService;

    public List<QuestionReview> getLatestQuestions(int userId, int offset, int limit) {
        return questionReviewDao.selectLatestReviewQuestions(userId, offset, limit);
    }

    public int deleteQuestion(int questionId){
        return questionReviewDao.deleteQuestionReview(questionId);
    }

    public int addQuestion(QuestionReview questionReview) {
        questionReview.setTitle(HtmlUtils.htmlEscape(questionReview.getTitle()));
        questionReview.setContent(HtmlUtils.htmlEscape(questionReview.getContent()));

        //过滤敏感词
        questionReview.setTitle(sensitiveService.filter(questionReview.getTitle()));
        questionReview.setContent(sensitiveService.filter(questionReview.getContent()));

        return questionReviewDao.addReviewQuestion(questionReview) > 0 ? questionReview.getId() : 0;
    }

    public QuestionReview getQuestionById(int qid) {
        return questionReviewDao.getReviewById(qid);
    }
    public int updateCommentCount(int id, int commentCount){
        return questionReviewDao.updateReviewCommentCount(id,commentCount);
    }
}
