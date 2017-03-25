package com.ymhou.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by ymhou on 2017/3/24.
 */
@Data
public class Question {
    private int id;
    private String title;
    private String content;
    private Date createdDate;
    private int userId;
    private int commentCount;
}
