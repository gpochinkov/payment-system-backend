package com.emerchantpay.emerchantpaypaymentsystem;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ValidationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.emerchantpay.emerchantpaypaymentsystem.exception.AuthorizationException;
import com.emerchantpay.emerchantpaypaymentsystem.exception.ResourceNotFoundException;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", new Date());

    //Get all errors
    List<String> errors = ex.getBindingResult()
                            .getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.toList());

    body.put("errors", errors);

    return new ResponseEntity<>(body, headers, status);

  }

  @ExceptionHandler(ValidationException.class)
  protected ResponseEntity<Object> handleValidationException(ValidationException ex) {
    return exceptionResponse(ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleException(Exception ex) {
    logger.error(ex.getMessage(), ex);

    return exceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected void handleResourceNotFound() {
  }

  @ExceptionHandler(AuthorizationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  protected void handleAuthorizationException() {
  }

  private ResponseEntity<Object> exceptionResponse(Exception ex, HttpStatus status) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", new Date());

    //Get all errors
    List<String> errors = new ArrayList<>();
    errors.add(ex.getMessage() != null ? ex.getMessage() : getStackTrace(ex));

    body.put("errors", errors);

    return new ResponseEntity<>(body, status);
  }

  private static String getStackTrace(Exception ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    pw.close();

    return sw.toString();
  }
}
