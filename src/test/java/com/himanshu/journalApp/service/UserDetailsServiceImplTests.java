package com.himanshu.journalApp.service;

import com.himanshu.journalApp.Entity.User;
import com.himanshu.journalApp.Repository.UserRepository;
import com.himanshu.journalApp.Services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;//initialized mocks are injected iin userdetailesServiceImpl

    @Mock
    private UserRepository userRepository;//it was not initailized.so we added before eacxh annotation

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);//mocks initialized
    }
    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("ram").password("jkfbgsj").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
