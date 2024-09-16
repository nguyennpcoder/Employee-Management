package com.vti.configuration.exception;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler
    implements AuthenticationEntryPoint, AccessDeniedHandler

{
    @Autowired
    private MessageSource messageSource;

    private String getMessage(String code, Object... args){
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    @Autowired
    private ObjectWriter objectWriter;

    // Object... args : seen as an arrays; truyen vao bn bien cung dc
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exception.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.put(field, message);
        }
        String message = getMessage("MethodArgumentNotValidException.message");
        // String detailMessage = exception.getLocalizedMessage();
        ErrorResponse response = new ErrorResponse(1, message, errors);
        return  new ResponseEntity<>(response, headers, status);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException exception
    ) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> constraint : exception.getConstraintViolations()) {
            String field = constraint.getPropertyPath().toString();
            String value = constraint.getMessage();
            errors.put(field, value);
        }
        String message = getMessage("ConstraintViolationException.message");
        ErrorResponse response = new ErrorResponse(2, message, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handlerException(Exception exception){
        String message = getMessage("Exception.message");
        ErrorResponse response = new ErrorResponse(3, message);
        // log.error(message, exception);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        String message = getMessage(
                "NoHandlerFoundException.message",
                exception.getHttpMethod(), exception.getRequestURL() // ~ {0} , {1} trong messsage
        );
        // String detailMessage = exception.getLocalizedMessage();
        ErrorResponse response = new ErrorResponse(4, message); // bo (, detail message)
        // log.error(message, exception);
        return new ResponseEntity<>(response, headers, status);
    }

    // 405Method Not Allowed
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        String message = getMessage(
                "HttpRequestMethodNotSupportedException.message",
                exception.getMethod()
        );
        // String detailMessage = exception.getLocalizedMessage();
        ErrorResponse response = new ErrorResponse(5, message);
        return new ResponseEntity<>(response, headers, status);
    }

    // 415Unsupported Media Type: gửi lên kiểu text thay vì json
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        String message = getMessage(
                "HttpMediaTypeNotSupportedException.message",
                exception.getContentType()
        );
        // String detailMessage = exception.getLocalizedMessage();
        ErrorResponse response = new ErrorResponse(4, message);
        return new ResponseEntity<>(response, headers, status);
    }

    // 400Bad Request: lỗi do ko truyền lên param bắt buộc
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        String message = getMessage(
                "MissingServletRequestParameterException.message",
                exception.getParameterName(),
                exception.getParameterType()
        );
        // String detailMessage = exception.getLocalizedMessage();
        ErrorResponse response = new ErrorResponse(7, message);
        return new ResponseEntity<>(response, headers, status);
    }

    // 400Bad Request: truyền lên sai kiểu dữ liệu
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException exception,
            HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        if (exception instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) exception;
            Class<?> requiredType = ex.getRequiredType();
            String message = getMessage(
                    "MethodArgumentTypeMismatchException.message",
                    ex.getName(),
                    requiredType == null ? "null" : requiredType.getName()
            );
            // String detailMessage = exception.getLocalizedMessage();
            ErrorResponse response = new ErrorResponse(8, message);
            return new ResponseEntity<>(response, headers, status);
        }
        return super.handleTypeMismatch(exception, headers, status, request);
    }

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        String message = getMessage("AuthenticationException.message");
        // String detailMessage = exception.getLocalizedMessage();
        ErrorResponse res = new ErrorResponse(9, message);
        String json = objectWriter.writeValueAsString(res);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);
    }

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException exception
    ) throws IOException {
        String message = getMessage("AccessDeniedException.message");
        // String detailMessage = exception.getLocalizedMessage();
        ErrorResponse res = new ErrorResponse(10, message);
        String json = objectWriter.writeValueAsString(res);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);
    }

}
