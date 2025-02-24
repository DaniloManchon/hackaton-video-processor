package com.fiap.video_processor.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> healthChecker(){
        return new ResponseEntity<>("I'm alive!", HttpStatus.OK);
    }
}
