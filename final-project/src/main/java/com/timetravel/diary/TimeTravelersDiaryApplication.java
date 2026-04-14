package com.timetravel.diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TimeTravelersDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeTravelersDiaryApplication.class, args);
    }

}
