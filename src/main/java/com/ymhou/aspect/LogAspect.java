package com.ymhou.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Created by ymhou on 2017/3/24.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.ymhou.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuffer sb = new StringBuffer();
        for(Object arg:joinPoint.getArgs()){
            sb.append("arg: "+arg.toString() +"|");
        }
        logger.info("before method:"+sb.toString());
    }

    @After("execution(* com.ymhou.controller.IndexController.*(..))")
    public void afterMethod(){
        logger.info("after method:"+new Date());
    }
}
