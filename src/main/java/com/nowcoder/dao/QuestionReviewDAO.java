package com.nowcoder.dao;

import com.nowcoder.model.QuestionReview;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: wenda
 * @Date: 2018/8/30
 * @Author: chandler
 * @Description:
 */
@Mapper
public interface QuestionReviewDAO {
    String TABLE_NAME = " question_review ";
    String INSERT_FIELDS = " title, content, created_date, user_id, has_read, questionid, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{hasRead},#{questionId},#{commentCount})"})
    int addReviewQuestion(QuestionReview questionReview);

    List<QuestionReview> selectLatestReviewQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where questionid=#{questionId}"})
    QuestionReview getReviewById(int questionid);

    @Update({"update ", TABLE_NAME," set comment_count = #{commentCount} where id = #{id}"})
    int updateReviewCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    @Delete({"delete from",TABLE_NAME," where questionid = #{questionId}"})
    int deleteQuestionReview(@Param("questionId")int questionId);
}
