package com.forloop.springboot.init;

import com.forloop.springboot.entity.User;
import com.forloop.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandLineRunner.class);

    @Autowired
    private UserRepository repository;


    @Override
    public void run(String... args) throws Exception {
        //Save a couple of customers

        repository.save(new User("FORLoop", "Admin"));
        repository.save(new User("David", "User"));
        repository.save(new User("Tester", "Admin"));
        repository.save(new User("NOLF", "User"));

        LOGGER.info("---------------------------------------");
        LOGGER.info("Finding all users");
        LOGGER.info("---------------------------------------");
        for (User user : repository.findAll()) {
            LOGGER.info(user.toString());
        }
        LOGGER.info("---------------------------------------");
        LOGGER.info("Finding all admins");
        LOGGER.info("---------------------------------------");
        for (User user : repository.findByRole("Admin")) {
            LOGGER.info(user.toString());
        }
    }
}
