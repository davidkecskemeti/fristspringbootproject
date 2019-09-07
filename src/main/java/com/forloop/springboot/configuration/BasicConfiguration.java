package com.forloop.springboot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("basic")
public class BasicConfiguration {

    private boolean value;
    private String message;
    private int number;
}
