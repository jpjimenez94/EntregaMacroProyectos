/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macro.services;

import com.macro.domain.CalendarByAction;
import java.util.Date;
import java.util.List;


public interface CalendarService {
    
    public List<Object[]> getActionByUserId(int idUser,Date dateStart, Date dateEnd);
    
    public List<Object[]> getActionByUserIdAndDate(int idUser, String date);
    
    public List<Object[]> getActionByUserIdAndActivity(int idUser, String activity);
    
    public void setActionCalendar(int idUser,String activity, String date);
    
    public void updateActionCalendar(int idUser,String description, String date);
    
    public void deleteActionCalendar(int idUser,String activity);
    
}
