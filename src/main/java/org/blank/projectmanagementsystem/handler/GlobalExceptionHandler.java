//package org.blank.projectmanagementsystem.handler;
//
//import com.project.ecommerce.exception.ProductException;
//import com.project.ecommerce.exception.UserException;
//import com.project.ecommerce.security.InvalidJwtAuthenticationException;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(UserException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ResponseBody
//    public Map<String, String> handleUserException(UserException e) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("message", e.getMessage());
//        return errorResponse;
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ResponseBody
//    public Map<String, String> handleBadCredentialsException(BadCredentialsException e) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("message", e.getMessage());
//        return errorResponse;
//    }
//
//    @ExceptionHandler(InvalidJwtAuthenticationException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ResponseBody
//    public Map<String, String> handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException e) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("message", e.getMessage());
//        return errorResponse;
//    }
//
//    @ExceptionHandler(ProductException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public Map<String, String> handleProductException(ProductException e) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("message", e.getMessage());
//        return errorResponse;
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public Map<String, List> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        List<String> messages = e.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
//
//        Map<String, List> errorResponse = new HashMap<>();
//        errorResponse.put("message", messages);
//        return errorResponse;
//    }
//}
