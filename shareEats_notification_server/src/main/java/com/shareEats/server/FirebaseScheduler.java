package com.shareEats.server;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FirebaseScheduler {

    // 크론 표현식 - 매 1분마다 실행하는 스케줄러
    @Scheduled(cron = "0 0/1 * * * ?")
    public void sampleScheduler() {
        System.out.println("Hello, This is Scheduler speaking.");
    }
}
