package com.nowcoder.dao;

import com.nowcoder.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Date: 18-9-3
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Mapper
public interface CommentDAO {

    String TABLE_NAME = "comment";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ,reason";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status},#{reason})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where entity_id=#{entityId} " +
            "and entity_type=#{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);

    @Select({"select count(id) from ", TABLE_NAME," where entity_id = #{entityId} and entity_type = #{entityType} order by created_date desc limit #{offset}, #{limit}"})
    int getCommentCount(@Param("entityId")int entityId,
                        @Param("entityType")int entityType,
                        @Param("offset") int offset,
                        @Param("limit") int limit);

    @Update({"update ", TABLE_NAME," set status = #{status} where entity_id = #{entityId} and entity_type = #{entityType}"})
    void updateStatus(@Param("entityId")int entityId,@Param("entityType")int entityType,@Param("status")int status);

    @Select({"select ",SELECT_FIELDS," from ", TABLE_NAME," where id = #{id}"})
    Comment getCommentById(int id);

    @Select({"select count(id) from ",TABLE_NAME," where user_id = #{userId}"})
    int getUserCommentCount(int userId);

    @Update({"update ",TABLE_NAME," set entity_id = #{entityId} where user_id = #{userId} and create_date = #{createDate}"})
    int updateReviewComment(Comment comment);
}
