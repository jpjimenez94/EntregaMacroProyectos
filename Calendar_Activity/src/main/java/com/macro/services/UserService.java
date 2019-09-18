/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macro.services;


import com.macro.domain.User;
import java.util.Date;


public interface UserService {
       
    public User getUserByUserId(int idUser);
    
}
