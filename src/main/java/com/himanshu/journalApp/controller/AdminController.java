package com.himanshu.journalApp.controller;


import com.himanshu.journalApp.Entity.User;
import com.himanshu.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userservice;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> all = userservice.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user) {
        userservice.saveAdmin(user);
    }


}
