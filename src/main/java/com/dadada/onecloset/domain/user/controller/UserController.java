package com.dadada.onecloset.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public ResponseEntity<?> getUserInfo() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @DeleteMapping
//    public ResponseEntity<?> leaveService() {
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
