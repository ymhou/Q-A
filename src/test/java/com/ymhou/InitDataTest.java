package com.ymhou;

import com.ymhou.dao.QuestionDao;
import com.ymhou.dao.UserDao;
import com.ymhou.model.Question;
import com.ymhou.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)

public class InitDataTest {
    @Autowired
    UserDao userDao;

    @Autowired
    QuestionDao questionDao;

    @Test
    public void initDatabase() {
//        Random random = new Random();
//        for (int i = 0; i < 11; i++) {
////            User user = new User();
////            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
////            user.setName(String.format("USER%d", i));
////            user.setPassword("");
////            user.setSalt("");
////
////            userDao.addUser(user);
////
////            user.setPassword("newpassword");
////            userDao.updatePassword(user);
//
//            Question question = new Question();
//            question.setCommentCount(i);
//            Date date = new Date();
//            date.setTime(date.getTime()+1000*3600*5*i);
//            question.setCreatedDate(date);
//            question.setUserId(i + 1);
//            question.setTitle(String.format("TITLE{%d}", i));
//            question.setContent(String.format("Balaababalalalal Content %d", i));
//            questionDao.addQuestion(question);
//
//
//        }
        List<Question> list = questionDao.selectLatestQuestions(0,0,10);
        System.out.println(list);

//        Assert.assertEquals("newpassword",userDao.selectById(1).getPassword());
//        userDao.deleteById(2);
//        Assert.assertNull(userDao.selectById(2));
    }

}
