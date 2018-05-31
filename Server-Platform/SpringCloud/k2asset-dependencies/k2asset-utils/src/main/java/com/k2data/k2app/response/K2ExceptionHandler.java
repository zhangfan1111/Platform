package com.k2data.k2app.response;

import com.k2data.k2app.constant.ResponseCode;
import com.k2data.k2app.exception.K2ResponseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Global Exception Handler
 *
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 * @see HttpStatus
 */
@RestControllerAdvice
public class K2ExceptionHandler {

    private static final Log logger = LogFactory.getLog(K2ExceptionHandler.class);

    // Empty result
    private static final String EMPTY_RESULT = "";

    /**
     * Custom Exception Handler
     */
    @ExceptionHandler(value = K2ResponseException.class)
    public ResponseEntity<Object> handleK2BaseException(K2ResponseException ex, HttpServletRequest request) {
        logger.error(String.format("Server error, uri: %s", request.getRequestURI()), ex);

        return responseEntityWrapper(statusExtractor(ex.getCode()), ex.getCode(), ex.getMessage());
    }

    /**
     * Default Exception Handler (5xx)
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Object> handleDefaultException(Throwable ex, HttpServletRequest request) {
        logger.error(String.format("Server error, uri: %s", request.getRequestURI()), ex);

        return responseEntityWrapper(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.GENERAL_SERVER_ERROR,
                String.format("%s. %s", ResponseCode.SERVER_ERROR, ex.getMessage()));
    }

    /**
     * 4xx BAD_REQUEST Exception Handler
     */
    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            TypeMismatchException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class,
            DuplicateKeyException.class
    })
    public ResponseEntity<Object> handleBadRequestException(Throwable ex, HttpServletRequest request) {
        logger.error(String.format("Client error, uri: %s", request.getRequestURI()), ex);

        return responseEntityWrapper(HttpStatus.BAD_REQUEST, ResponseCode.GENERAL_CLIENT_ERROR,
                String.format("%s. %s", ResponseCode.CLIENT_ERROR, ex.getMessage()));
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(Throwable ex, HttpServletRequest request) {
        logger.error(String.format("Client error, uri: %s", request.getRequestURI()), ex);

        return responseEntityWrapper(HttpStatus.METHOD_NOT_ALLOWED, ResponseCode.GENERAL_CLIENT_ERROR,
                String.format("%s", "HTTP 405, Method not allowed!"));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Object> handleHttpMessageNotReadableException(Throwable ex, HttpServletRequest request) {
        logger.error(String.format("Client error, uri: %s", request.getRequestURI()), ex);

        return responseEntityWrapper(HttpStatus.BAD_REQUEST, ResponseCode.GENERAL_CLIENT_ERROR,
                String.format("%s", "HTTP 400, Http message not readable!"));
    }

    /**
     * 5xx INTERNAL_SERVER_ERROR Exception Handler
     */
    @ExceptionHandler({
            ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class,
            MissingPathVariableException.class,
            AsyncRequestTimeoutException.class,
            NullPointerException.class
    })
    public ResponseEntity<Object> handleInternalServerErrorException(Throwable ex, HttpServletRequest request) {
        logger.error(String.format("Server error, uri: %s", request.getRequestURI()), ex);

        return responseEntityWrapper(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.GENERAL_SERVER_ERROR,
                String.format("%s. %s", ResponseCode.SERVER_ERROR, ex.getMessage()));
    }

    /**
     * Wrap ResponseEntity
     *
     * @param status  Standard http status
     * @param code    Error code
     * @param message Error message
     */
    private ResponseEntity<Object> responseEntityWrapper(HttpStatus status, int code, String message) {
        CommonResultResponse<String> response = new CommonResultResponse<>();

        response.setCode(code);
        response.setMessage(message);
        response.setResult(EMPTY_RESULT);

        return new ResponseEntity<>(response, status);
    }

    /**
     * Extract standard http status from custom error code
     *
     * @param code Custom Error Code
     */
    private static HttpStatus statusExtractor(final int code) {
        char series = String.valueOf(code).charAt(0);

        switch (series) {
            case '1':
                return HttpStatus.CONTINUE;
            case '2':
                return HttpStatus.OK;
            case '3':
                return HttpStatus.MULTIPLE_CHOICES;
            case '4':
                return HttpStatus.BAD_REQUEST;
            case '5':
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
