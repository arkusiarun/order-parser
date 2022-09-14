package com.parser.orderparser;

import com.parser.orderparser.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class OrderParserApplication implements CommandLineRunner {

    @Autowired
    private ParserService parserService;

    public static void main(String[] args) {
        SpringApplication.run(OrderParserApplication.class, args).close();
    }

    @Override
    public void run(String... args) {
        this.parserService.parse(args);
    }
}