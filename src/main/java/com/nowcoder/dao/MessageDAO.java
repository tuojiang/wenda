package com.nowcoder.dao;

import com.nowcoder.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Date: 18-9-4
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Mapper
public interface MessageDAO {

    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id = #{userId} and conversation_id= #{conversationId}"})
    int getConvesationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id  order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS," FROM ", TABLE_NAME," where conversation_id = #{conversationId}  order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ",INSERT_FIELDS," from ",TABLE_NAME," where conversation_id=#{conversationId}"})
    Message getMessageByConversationId(@Param("conversationId")String conversationId);

    @Update({"update ", TABLE_NAME," set has_read=#{hasRead} where conversation_id=#{conversationId}"})
    void updateMessageStatus(@Param("hasRead")int hasRead,@Param("conversationId")String conversationId);
}
