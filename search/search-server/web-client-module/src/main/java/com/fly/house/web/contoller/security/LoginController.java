package com.fly.house.web.contoller.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by dimon on 4/19/14.
 */
@RestController
@RequestMapping("/rest")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", params = {"user", "password"}, method = POST)
    public ResponseEntity<String> login(HttpServletRequest request,
                                        @RequestParam String user,
                                        @RequestParam String password) throws ServletException {
        try {
            request.login(user, password);
            logger.debug("User {} logged in successfully", user);
            return new ResponseEntity<>(OK);
        } catch (ServletException e) {
            logger.warn("Exception occurred: ", e);
            return new ResponseEntity<>(UNAUTHORIZED);
        }
    }

    @ResponseStatus(OK)
    @RequestMapping(value = "/logout", method = GET)
    public void logout(HttpServletRequest request, Principal principal) throws ServletException {
        try {
            if (Objects.nonNull(principal)) {
                String userName = principal.getName();
                logger.debug("User {} logged out successfully", userName);
                request.logout();
            }
        } catch (ServletException e) {
            logger.warn("Exception occurred: ", e);
        }
    }


}
