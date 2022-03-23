package com.metelski.prefixtree;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point class.
 */
@Slf4j
@SpringBootApplication
public class PrefixTreeApplication {

    /**
     * Application main method.
     */
    public static void main(String[] args) {
        SpringApplication.run(PrefixTreeApplication.class, args);
    }
}
