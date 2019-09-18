package com.macro.api;

import com.macro.domain.Response;
import com.macro.domain.Greeting;
import com.macro.domain.CalendarByAction;
import com.macro.*;
import com.macro.domain.User;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.macro.services.CalendarService;
import com.macro.services.UserService;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = {"getCalendar/{idUser}/{dateStart}/{dateEnd}"})
    public ResponseEntity getActionByUserId(@PathVariable("idUser") int idUser, @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(dateStart);
            Date endDate = sdf.parse(dateEnd);
            String dateString = sdf.format(new Date());
            Date dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            if (startDate.compareTo(dateNow) > 0 || endDate.compareTo(dateNow) > 0) {
                Response response = new Response();
                response.setMessage("Las fechas ingresada no pueden ser mayor a la actual");
                response.setCode(HttpStatus.BAD_REQUEST.toString());
                response.setType(HttpStatus.BAD_REQUEST.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Object[]> calendarByAction = calendarService.getActionByUserId(idUser, startDate, endDate);

            if (calendarByAction.isEmpty()) {
                Response response = new Response();
                response.setMessage("No existe un registro en el calendarion con los datos ingresados");
                response.setCode(HttpStatus.NOT_FOUND.toString());
                response.setType(HttpStatus.NOT_FOUND.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.status(HttpStatus.OK).body(calendarByAction);

        } catch (Exception ex) {
            Response response = new Response();
            response.setMessage(String.format("No ha sido posible completar el proceso, intente nuevamente ERROR: %s", ex));
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response.setType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = {"setCalendarActivities/{idUser}/{action}/{date}"})
    public ResponseEntity setCalendarActivities(@PathVariable("idUser") int idUser, @PathVariable("action") String action, @PathVariable("date") String date) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date clientDate = sdf.parse(date);
            String clientDateString = sdf.format(clientDate);

            String dateString = sdf.format(new Date());
            Date dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            if (clientDate.compareTo(dateNow) < 0) {
                Response response = new Response();
                response.setMessage("La fecha ingresada no pueden ser menor a la actual");
                response.setCode(HttpStatus.BAD_REQUEST.toString());
                response.setType(HttpStatus.BAD_REQUEST.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            User user = userService.getUserByUserId(idUser);

            if (user == null) {
                Response response = new Response();
                response.setMessage("No existe un usuario con el id usuario ingresado");
                response.setCode(HttpStatus.NOT_FOUND.toString());
                response.setType(HttpStatus.NOT_FOUND.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Object[]> calendarByAction = calendarService.getActionByUserIdAndDate(idUser, clientDateString);

            if (!calendarByAction.isEmpty()) {
                Response response = new Response();
                response.setMessage("No se puede ingresar dos actividades el mismo dia");
                response.setCode(HttpStatus.BAD_REQUEST.toString());
                response.setType(HttpStatus.BAD_REQUEST.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            calendarService.setActionCalendar(idUser, action, clientDateString);

            return ResponseEntity.status(HttpStatus.OK).body("Actividad ingresada correctamente en el calendario");

        } catch (Exception ex) {
            Response response = new Response();
            response.setMessage(String.format("No ha sido posible completar el proceso, intente nuevamente ERROR: %s", ex));
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response.setType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    
    @RequestMapping(method = RequestMethod.PUT, value = {"updateCalendarActivities/{idUser}/{action}/{date}/{description}"})
    public ResponseEntity updateCalendarActivities(@PathVariable("idUser") int idUser, @PathVariable("action") String action, @PathVariable("date") String date, @PathVariable("description") String description) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date clientDate = sdf.parse(date);
            String clientDateString = sdf.format(clientDate);

            String dateString = sdf.format(new Date());
            Date dateNow = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            if (clientDate.compareTo(dateNow) < 0) {
                Response response = new Response();
                response.setMessage("La fecha ingresada no pueden ser menor a la actual");
                response.setCode(HttpStatus.BAD_REQUEST.toString());
                response.setType(HttpStatus.BAD_REQUEST.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            User user = userService.getUserByUserId(idUser);

            if (user == null) {
                Response response = new Response();
                response.setMessage("No existe un usuario con el id usuario ingresado");
                response.setCode(HttpStatus.NOT_FOUND.toString());
                response.setType(HttpStatus.NOT_FOUND.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Object[]> calendarByAction = calendarService.getActionByUserIdAndActivity(idUser, action);

            if (calendarByAction.isEmpty()) {
                Response response = new Response();
                response.setMessage("No se puede actualizar la actividad por que no existe esa actividad asociada a el usuario indicado");
                response.setCode(HttpStatus.BAD_REQUEST.toString());
                response.setType(HttpStatus.BAD_REQUEST.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            calendarService.updateActionCalendar(idUser, description, clientDateString);

            return ResponseEntity.status(HttpStatus.OK).body("Actividad actualizada correctamente en el calendario");

        } catch (Exception ex) {
            Response response = new Response();
            response.setMessage(String.format("No ha sido posible completar el proceso, intente nuevamente ERROR: %s", ex));
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response.setType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = {"deleteCalendarActivities/{idUser}/{action}"})
    public ResponseEntity deleteCalendarActivities(@PathVariable("idUser") int idUser, @PathVariable("action") String action) {
        try {

            User user = userService.getUserByUserId(idUser);

            if (user == null) {
                Response response = new Response();
                response.setMessage("No existe un usuario con el id usuario ingresado");
                response.setCode(HttpStatus.NOT_FOUND.toString());
                response.setType(HttpStatus.NOT_FOUND.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Object[]> calendarByAction = calendarService.getActionByUserIdAndActivity(idUser, action);

            if (calendarByAction.isEmpty()) {
                Response response = new Response();
                response.setMessage("No se puede eliminar la actividad por que no existe esa actividad asociada a el usuario indicado");
                response.setCode(HttpStatus.BAD_REQUEST.toString());
                response.setType(HttpStatus.BAD_REQUEST.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            calendarService.deleteActionCalendar(idUser, action);

            return ResponseEntity.status(HttpStatus.OK).body("Actividad eliminada correctamente en el calendario");

        } catch (Exception ex) {
            Response response = new Response();
            response.setMessage(String.format("No ha sido posible completar el proceso, intente nuevamente ERROR: %s", ex));
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response.setType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


}
