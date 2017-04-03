package com.ymhou.controller;

import com.ymhou.model.*;
import com.ymhou.service.CommentService;
import com.ymhou.service.QuestionService;
import com.ymhou.service.UserService;
import com.ymhou.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ymhou on 2017/3/30.
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        try{
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCommentCount(0);
            question.setCreatedDate(new Date());
            if(hostHolder.getUser() == null){
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
            }else {
                question.setUserId(hostHolder.getUser().getId());
            }

            if(questionService.addQuestion(question) >0){
                return WendaUtil.getJSONString(0);
            }

        }catch (Exception e){
            logger.info("增加题目失败"+e.getMessage());
        }
        return WendaUtil.getJSONString(1,"失败");
    }

    @RequestMapping(value = "/question/{qid}",method = RequestMethod.GET)
    public String questionDetail(Model model,@PathVariable("qid") int qid){
        Question question = questionService.getById(qid);
        model.addAttribute("question",question);
        List<Comment> comments = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();
        for(Comment comment:comments){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            vo.set("user", userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments",vos);

        return "detail";
    }
}
