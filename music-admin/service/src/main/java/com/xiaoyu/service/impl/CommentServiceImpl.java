package com.xiaoyu.service.impl;

import com.xiaoyu.dao.CommentMapper;
import com.xiaoyu.pojo.Comment;
import com.xiaoyu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/11/15 16:16
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean insert(Comment comment) {
        return commentMapper.insert(comment) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return commentMapper.delete(id) > 0;
    }

    @Override
    public boolean update(Comment comment) {
        return commentMapper.update(comment) > 0;
    }

    @Override
    public List<Comment> getAllComment() {
        return commentMapper.getAllComment();
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentMapper.getCommentById(id);
    }

    @Override
    public List<Comment> getSongCommentById(Integer songId) {
        return commentMapper.getSongCommentById(songId);
    }

    @Override
    public List<Comment> getSongListCommentById(Integer songListId) {
        return commentMapper.getSongListCommentById(songListId);
    }
}
