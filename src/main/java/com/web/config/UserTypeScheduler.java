package com.web.config;

import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class UserTypeScheduler {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 0 * * ?") // Chạy mỗi ngày lúc 00:00
    public void scheduleUserTypeUpdate() {
        userService.updateUserType();
    }
}

