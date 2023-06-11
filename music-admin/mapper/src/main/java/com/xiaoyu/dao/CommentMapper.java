package com.xiaoyu.dao;

import com.xiaoyu.pojo.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    public int insert(Comment comment);

    public int delete(Integer id);

    public int update(Comment comment);

    public List<Comment> getAllComment();

    public Comment getCommentById(Integer id);

    /*查询对应歌曲下的所有评论*/
    public List<Comment> getSongCommentById(Integer songId);

    /*查询对应歌单下的所有评论*/
    public List<Comment> getSongListCommentById(Integer songListId);
}
