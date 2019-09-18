/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.macro.services;

import com.macro.Persistence.CalendarRepository;
import com.macro.domain.CalendarByAction;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    @Override
    public List<Object[]> getActionByUserId(int idUser, Date dateStart, Date dateEnd) {

        return calendarRepository.selectUserById(idUser, dateStart, dateEnd);

    }

    @Override
    public void setActionCalendar(int idUser, String activity, String date) {

        calendarRepository.setActionCalendar(idUser, activity, date);

    }

    @Override
    public List<Object[]> getActionByUserIdAndDate(int idUser, String date) {

        return calendarRepository.getActionByUserIdAndDate(idUser, date);

    }
    
    @Override
    public List<Object[]> getActionByUserIdAndActivity(int idUser, String activity) {

        return calendarRepository.getActionByUserIdAndActivity(idUser, activity);

    }

    @Override
    public void updateActionCalendar(int idUser, String description, String date) {
        calendarRepository.updateActionCalendar(idUser, description, date);
    }

    @Override
    public void deleteActionCalendar(int idUser, String activity ) {
        calendarRepository.deleteActionCalendar(idUser, activity);
    }

}
