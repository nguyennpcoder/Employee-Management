package com.vti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    @Autowired
    private MessageSource messageSource;

    // lay theo ngon ngu mac dinh
    @GetMapping
    public String findAll(@RequestParam("code") String code){
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    // lay theo locale truyen vao
    @GetMapping("/vi")
    public String findAllVi(@RequestParam("code") String code){
        return messageSource.getMessage(code, null, new Locale("vi"));
    }
}
