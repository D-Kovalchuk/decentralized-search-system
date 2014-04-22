package com.fly.house.web.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by dimon on 4/22/14.
 */
@Controller
public class DashboardController {

    @RequestMapping(value = "/", method = GET)
    public String showDashboard() {
        return "dashboard";
    }

}
