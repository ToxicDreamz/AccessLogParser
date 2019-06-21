package com.cmd.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cmd.parser")
public class CmdParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmdParserApplication.class, args);
    }

}
