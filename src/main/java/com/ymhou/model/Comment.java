package com.ymhou.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by ymhou on 2017/4/3.
 */
@Data
public class Comment {
    private int id;
    private String content;
    private int userId;
    private int entityId;
    private int entityType;
    private Date createdDate;
    private int status;
}
