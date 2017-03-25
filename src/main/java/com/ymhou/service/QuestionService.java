package com.ymhou.service;

/**
 * Created by ymhou on 2017/3/25.
 */

import com.ymhou.dao.QuestionDao;
import com.ymhou.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<Question> getLatestQuestions(int userId, int offset, int limit){
        return questionDao.selectLatestQuestions(userId,offset,limit);
    }
}
