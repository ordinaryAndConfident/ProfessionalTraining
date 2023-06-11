package com.xiaoyu.service;

import com.xiaoyu.pojo.Comment;

import java.util.List;

public interface CommentService {
    public boolean insert(Comment comment);

    public boolean delete(Integer id);

    public boolean update(Comment comment);

    public List<Comment> getAllComment();

    public Comment getCommentById(Integer id);

    /*查询对应歌曲下的所有评论*/
    public List<Comment> getSongCommentById(Integer songId);

    /*查询对应歌单下的所有评论*/
    public List<Comment> getSongListCommentById(Integer songListId);
}
