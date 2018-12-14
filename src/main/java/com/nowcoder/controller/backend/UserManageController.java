package com.nowcoder.controller.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Date: 18-12-14
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
@Controller
public class UserManageController {

    private static final Logger logger = LoggerFactory.getLogger(UserManageController.class);

    @RequestMapping(path = {"/manager"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String manager(Model model) {


        return "index";
    }

}
