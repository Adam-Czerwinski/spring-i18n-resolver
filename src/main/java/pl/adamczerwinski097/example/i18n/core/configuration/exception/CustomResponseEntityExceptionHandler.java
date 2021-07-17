package pl.adamczerwinski097.example.i18n.core.configuration.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final Collection<String> errors = ex.getAllErrors().stream().map(this::getMessage).collect(Collectors.toList());
        final String message = getMessage(ex);
        return ResponseEntity.status(status).body(buildExceptionResponse(message, status.value(), errors));
    }

    @ExceptionHandler({BusinessLogicException.class})
    protected ResponseEntity<ExceptionResponse> handleBusinessLogicException(BusinessLogicException ex) {
        final String message = getMessage(ex, ex.getArgs());
        return ResponseEntity.badRequest().body(buildExceptionResponse(message,HttpStatus.BAD_REQUEST.value()));
    }

    private String getMessage(Exception ex, Object[] args) {
        return messageSource.getMessage(getExceptionMessageName(ex), args, getLocale());
    }

    private String getMessage(ObjectError error) {
        return messageSource.getMessage(error, getLocale());
    }

    private String getMessage(Exception ex) {
        return getMessage(ex, null);
    }

    private ExceptionResponse buildExceptionResponse(String message, int status) {
        return buildExceptionResponse(message, status, null);
    }

    private ExceptionResponse buildExceptionResponse(String message, int status, Collection<String> errors) {
        return new ExceptionResponse(message, status, errors);
    }

    private String getExceptionMessageName(Exception ex) {
        return ex.getClass().getName();
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
