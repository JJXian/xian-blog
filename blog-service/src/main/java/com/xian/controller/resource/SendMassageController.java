package com.xian.controller.resource;

import com.xian.common.result.Result;
import com.xian.model.email.EmailDTO;
import com.xian.service.MailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: jjxian
 */
@RestController
@RequestMapping("/send")
public class SendMassageController {

    @Resource
    private MailService mailService;


    @PostMapping("/mail")
    public Result sendMail(@RequestBody EmailDTO emailDTO) {
        return mailService.sendEmail(emailDTO);
    }

}
