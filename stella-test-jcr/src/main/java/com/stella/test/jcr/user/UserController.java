package com.stella.test.jcr.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user rest controller
 *
 * @author sail
 * @date 14:13 2019-11-11.
 * @since 1.0
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public User addUser(User user){

        userService.addUser(user);

        return user;
    }
}
