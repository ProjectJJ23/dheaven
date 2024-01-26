package com.jj.dheaven.service;

import com.jj.dheaven.domain.User;
import com.jj.dheaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> memlist(){
        return userRepository.findAll();
    }




}
