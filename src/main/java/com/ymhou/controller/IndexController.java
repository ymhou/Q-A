package com.ymhou.controller;

import com.ymhou.aspect.LogAspect;
import com.ymhou.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ymhou on 2017/3/24.
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @RequestMapping(path = {"/"})
    @ResponseBody
    public String index() {
        return "hello";
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", required = false) String key) {
        logger.info("in the method");
        return String.format("profile Page of %s %d, type:%d key:%s", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/vm"}, method = RequestMethod.GET)
    public String template(Model model) {
        model.addAttribute("value1", "hym");

        List<String> colors = Arrays.asList(new String[]{"red", "green", "blue"});
        model.addAttribute("colors", colors);

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }

        model.addAttribute("map", map);


        return "home";
    }

    @RequestMapping(path = {"/redirect/{code}"}, method = RequestMethod.GET)
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg", "redirect");
        RedirectView red = new RedirectView("/", true);
        if (code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path = {"/admin"}, method = RequestMethod.GET)
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error: "+e.getMessage();
    }
}
