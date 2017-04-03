package com.ymhou.controller;

import com.ymhou.model.Comment;
import com.ymhou.model.EntityType;
import com.ymhou.model.HostHolder;
import com.ymhou.service.CommentService;
import com.ymhou.service.QuestionService;
import com.ymhou.service.SensitiveService;
import com.ymhou.service.UserService;
import com.ymhou.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * Created by ymhou on 2017/4/3.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    UserService userService;

    @RequestMapping(path = "/addComment", method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);

            Comment comment = new Comment();
            if (hostHolder != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
//                return "redirect:/reglogin";
            }
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setStatus(0);

            commentService.addComment(comment);

            int  count = commentService.getCommentCount(questionId,EntityType.ENTITY_QUESTION);
            questionService.updateCommentCount(questionId,count);


        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }

        return "redirect:/question/" + String.valueOf(questionId);
    }
}
