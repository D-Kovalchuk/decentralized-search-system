package com.fly.house.web.controller.security;

import com.fly.house.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by dimon on 4/21/14.
 */
@Controller
public class RegistrationsController {

    private static Logger logger = LoggerFactory.getLogger(RegistrationsController.class);

    @InitBinder
    public void setAllowedFields(WebDataBinder fields) {
        fields.setDisallowedFields("id", "artifacts");
    }

    @ModelAttribute
    public Account account() {
        return new Account();
    }

    @RequestMapping(value = "/registration", method = GET)
    private String showRegistrationPage() {
        return "registration";
    }

    //todo fix validation
    @RequestMapping(value = "/registration", method = POST)
    private String saveUser(@ModelAttribute @Valid Account account,
                            HttpServletRequest request,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }


        //todo save account to db
        String name = account.getName();
        String password = account.getPassword();
        try {
            request.login(name, password);
        } catch (ServletException e) {
            logger.warn("Authorization failed", e);
        }
        return "redirect:/";
    }
}
