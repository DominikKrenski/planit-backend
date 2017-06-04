package com.dominik.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominik on 07.04.17.
 */

@RestControllerAdvice
public class AppErrorHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AppErrorHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.info("WEJŚCIE DO METODY OBSŁUGI BŁĘDU HTTP_MESSAGE_NOT_READABLE_EXCEPTION");

        AppError appError = new AppError(status, ex.getMessage());

        return handleExceptionInternal(ex, appError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.info("WEJŚCIE DO METODY OBSŁUGI BŁĘDU METHOD_ARGUMENT_NOT_VALID_EXCEPTION");

        Errors error = ex.getBindingResult();

        List<String> errors = new ArrayList<>();

        AppError appError = new AppError(status, "Wystąpiły błędy podczas przetwarzania formularza: " + error.getErrorCount());

        for (ObjectError objectError: error.getAllErrors()) {
            errors.add(objectError.getDefaultMessage());
        }

        appError.setErrors(errors);

        return handleExceptionInternal(ex, appError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.info("WEJŚCIE DO METODY OBSŁUGI BŁĘDU TYPE_MISMATCH_EXCEPTION");

        AppError appError = new AppError(status, ex.getMessage());

        return handleExceptionInternal(ex, appError, headers, status, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {

        logger.info("WEJŚCIE DO METODY OBSŁUGI BŁĘDU DATA_INTEGRITY_VIOLATION_EXCEPTION");

        List<String> errors = new ArrayList<>();
        AppError appError = new AppError(HttpStatus.BAD_REQUEST, ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<Object>(appError, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {

        logger.info("WEJŚCIE DO METODY OBSŁUGI BŁĘDU CUSTOM_EXCEPTION");

        AppError appError = new AppError(HttpStatus.BAD_REQUEST, ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<Object>(appError, headers, HttpStatus.BAD_REQUEST);
    }
}
