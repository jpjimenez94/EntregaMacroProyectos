/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macro.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.macro.domain.CalendarByAction;
import com.macro.domain.User;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    @Query(value= "SELECT IdUser,Name FROM users WHERE IdUser =?1 ",nativeQuery =true)    
    public User getUserByUserId (int idUser);  
    
}
