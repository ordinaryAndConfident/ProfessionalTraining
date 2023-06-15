package com.xiaoyu.controller;

import com.xiaoyu.pojo.Comment;
import com.xiaoyu.service.CommentService;
import com.xiaoyu.vo.ResStatus;
import com.xiaoyu.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/new")
    public ResultVO insertComment(Comment comment){
        boolean insert = commentService.insert(comment);
        if (insert){
            return new ResultVO(ResStatus.OK,"评论成功",comment.getConsumerId());
        }else {
            return new ResultVO(ResStatus.NO,"评论失败",null);
        }
    }

    @PostMapping("/delete")
    public ResultVO deleteComment(Integer id){
        boolean delete = commentService.delete(id);
        if (delete){
            return new ResultVO(ResStatus.OK,"删除成功",delete);
        }else {
            return new ResultVO(ResStatus.NO,"删除失败",null);
        }
    }

    @PutMapping("/update")
    public ResultVO updateComment(Comment comment){
        boolean update = commentService.update(comment);
        if (update){
            return new ResultVO(ResStatus.OK,"修改成功",update);
        }else {
            return new ResultVO(ResStatus.NO,"修改失败",null);
        }
    }

    @GetMapping("/all")
    public ResultVO allComment(){
        List<Comment> comments = commentService.getAllComment();
        if (comments.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功",comments);
        }else {
            return new ResultVO(ResStatus.NO,"查询失败",null);
        }
    }

    @GetMapping("/byId")
    public ResultVO getCommentById(Integer id){
        Comment comment = commentService.getCommentById(id);
        if (comment != null){
            return new ResultVO(ResStatus.OK,"查询成功",comment);
        }else {
            return new ResultVO(ResStatus.NO,"查询失败",null);
        }
    }

    @GetMapping("/bySongId")
    public ResultVO getCommentBySongId(Integer songId){
        List<Comment> commentList = commentService.getSongCommentById(songId);
        if (commentList.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功",commentList);
        }else {
            return new ResultVO(ResStatus.NO,"查询失败",null);
        }
    }

    @GetMapping("/bySongListId")
    public ResultVO getCommentBySongListId(Integer songListId){
        List<Comment> commentList = commentService.getSongListCommentById(songListId);
        if (commentList.size() > 0){
            return new ResultVO(ResStatus.OK,"查询成功",commentList);
        }else {
            return new ResultVO(ResStatus.NO,"查询失败",null);
        }
    }
}
