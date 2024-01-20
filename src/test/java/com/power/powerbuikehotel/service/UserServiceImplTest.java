package com.power.powerbuikehotel.service;

import static org.junit.jupiter.api.Assertions.*;

import com.power.powerbuikehotel.data.model.User;
import com.power.powerbuikehotel.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;
   

    @Autowired
    private UserRepository userRepository;
    

    @BeforeEach
    public void deleteBeforeEveryTest() {
        //userRepository.deleteAll();
    }

    @Test
    public void testThatUserCanRegister(){
        User user = new User();
        user.setFirstName("ikechukwu");
        user.setLastName("ike");
        user.setEmail("email@gmail");
        user.setPassword("password");
       userService.registerUser(user);
        assertThat(userRepository.count(), is(1L));
        System.out.println(user);
    }

    @Test
    public void testThatAdminUserCanRegister(){
        User user = new User();
        user.setFirstName("ikechukwu");
        user.setLastName("ike");
        user.setEmail("email@gmail");
        user.setPassword("password");
        userService.registerAdminUser(user);
        assertThat(userRepository.count(), is(1L));
        System.out.println(user);
    }

}