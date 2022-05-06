package com.project.octwallet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class OctwalletApplication {

    public static void main(String[] args) {
        log.info(" 프로젝트 - 옥트월렛");
        SpringApplication.run(OctwalletApplication.class, args);
    }

}
