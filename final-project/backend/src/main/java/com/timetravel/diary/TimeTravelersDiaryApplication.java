package com.timetravel.diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TimeTravelersDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeTravelersDiaryApplication.class, args);
    }

}
