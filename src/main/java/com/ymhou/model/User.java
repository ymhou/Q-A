package com.ymhou.model;

import lombok.Data;

/**
 * Created by ymhou on 2017/3/24.
 */
@Data
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;
}
