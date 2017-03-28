package com.ymhou.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by ymhou on 2017/3/26.
 */
@Data
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private int status;   //0有效，1无效
    private String ticket;
}
