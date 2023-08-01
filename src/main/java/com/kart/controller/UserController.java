package com.kart.controller;

import com.kart.model.User;
import com.kart.model.dto.SignInInput;
import com.kart.model.dto.SignUpOutput;
import com.kart.service.AuthenticationService;
import com.kart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("register")
    public SignUpOutput userRegister(@RequestBody User user) {
        return userService.userRegister(user);
    }

    @PostMapping("login")
    public String userLogin(@RequestBody SignInInput signInInput) {
        return userService.userLogin(signInInput);
    }

    @DeleteMapping("signout")
    public String userSignOut(@RequestParam String email,@RequestParam String token) {
        if(authenticationService.authenticate(email,token)) {
            return userService.userSignOut(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }
    }


}
