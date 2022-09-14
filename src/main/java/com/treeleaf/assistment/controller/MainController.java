package com.treeleaf.assistment.controller;

import com.treeleaf.assistment.dto.GeneralResponseDto;
import com.treeleaf.assistment.dto.UserDetailsDto;
import com.treeleaf.assistment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @PostMapping(value = "register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GeneralResponseDto> registerUser(@RequestBody UserDetailsDto userDetailsDto) {

        GeneralResponseDto resp = userService.checkAndAddUser(userDetailsDto);
        if (resp.isResponseStatus()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(resp);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(resp);
        }
    }

}
