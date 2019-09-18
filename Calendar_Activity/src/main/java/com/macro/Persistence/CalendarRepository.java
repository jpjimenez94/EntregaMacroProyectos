/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macro.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.macro.domain.CalendarByAction;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface CalendarRepository extends JpaRepository<CalendarByAction, Integer>{
    
    @Query(value= "SELECT IdUser,Activity,Date FROM calendar WHERE IdUser =?1 AND Date BETWEEN ?2 AND ?3 ",nativeQuery =true)    
    public List<Object[]> selectUserById (int idUser,Date dateStart, Date dateEnd); 
    
    @Query(value= "SELECT IdUser,Activity,Date FROM calendar WHERE IdUser =?1 AND Date =?2 ",nativeQuery =true)    
    public  List<Object[]> getActionByUserIdAndDate(int idUser, String date); 
   
    @Query(value= "SELECT IdUser,Activity,Date FROM calendar WHERE IdUser =?1 AND Activity =?2 ",nativeQuery =true)    
    public  List<Object[]> getActionByUserIdAndActivity(int idUser, String activity);
    
    @Modifying
    @Query(value= "INSERT INTO calendar (IdUser, Activity, Date) VALUES (?1, ?2, ?3)",nativeQuery =true)  
    @Transactional    
    public void setActionCalendar(int idUser, String activity, String date);
    
    @Modifying
    @Query(value= "UPDATE calendar SET Description = ?2 WHERE IdUser =?1 AND Date =?3  ",nativeQuery =true)  
    @Transactional    
    public void updateActionCalendar(int idUser, String description, String date);
    
    @Modifying
    @Query(value= "DELETE FROM calendar WHERE IdUser =?1 AND Activity =?2  ",nativeQuery =true)  
    @Transactional    
    public void deleteActionCalendar(int idUser, String activity);
    
}
