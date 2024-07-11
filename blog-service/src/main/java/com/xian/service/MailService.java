package com.xian.service;

import com.xian.common.result.Result;
import com.xian.model.email.EmailDTO;


public interface MailService {


    Result sendEmail(EmailDTO emailDTO);
}
