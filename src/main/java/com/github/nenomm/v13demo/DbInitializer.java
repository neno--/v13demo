package com.github.nenomm.v13demo;


import com.github.nenomm.v13demo.domain.user.Password;
import com.github.nenomm.v13demo.domain.user.User;
import com.github.nenomm.v13demo.domain.user.UserPrivilege;
import com.github.nenomm.v13demo.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
//@Profile("test")
public class DbInitializer {

    private Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    private UserRepository userRepository;

    @Autowired
    public DbInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void initialize() {
        logger.info("Starting DB init...");


        final User user = new User("test", Password.getNew("test"));
        user.addPrivilege(UserPrivilege.USER);

        userRepository.save(user);


        logger.info("DB init finished.");
    }
}
