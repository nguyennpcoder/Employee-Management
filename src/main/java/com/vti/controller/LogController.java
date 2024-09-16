package com.vti.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logs")
@Log4j2
public class LogController {
    @GetMapping
    public void testLogs(){
       log.info("infor...");
       log.debug("infor...");
       log.warn("infor...");
       log.error("infor...");
       log.trace("infor...");
    }
}
