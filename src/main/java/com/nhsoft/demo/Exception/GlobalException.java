package com.nhsoft.demo.Exception;

import com.nhsoft.demo.Dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error(e.getMessage());
    }
}
