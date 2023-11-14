//package org.blank.projectmanagementsystem.handler;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@ControllerAdvice
//@Slf4j
//public class ExceptionHandlerController {
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ModelAndView handleError403(HttpServletRequest request, Exception e)   {
//        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
//        return new ModelAndView("access-denied");
//    }
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
//        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Request: " + request.getRequestURL() + " raised " + e);
//        return new ModelAndView("not-found");
//    }
//}