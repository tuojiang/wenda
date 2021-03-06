package com.nowcoder.dao;

import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: wenda
 * @Date: 2018/8/30
 * @Author: chandler
 * @Description:
 */
@Mapper
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Question getById(int id);

    @Update({"update ", TABLE_NAME," set comment_count = #{commentCount} where id = #{id}"})
    int updateCommentCount(@Param("id")int id,@Param("commentCount")int commentCount);

    @Update({"update ", TABLE_NAME," set title=#{title},content=#{content}," +
            "created_date=#{createdDate},user_id=#{userId},comment_count=#{commentCount} where id = #{id}"})
    int updateQuestion(Question question);
}
