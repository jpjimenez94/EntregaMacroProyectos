/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macro.services;

import com.macro.Persistence.CalendarRepository;
import com.macro.Persistence.UserRepository;
import com.macro.domain.CalendarByAction;
import com.macro.domain.User;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public  User getUserByUserId(int idUser) {

        return userRepository.getUserByUserId(idUser);

    }

}
