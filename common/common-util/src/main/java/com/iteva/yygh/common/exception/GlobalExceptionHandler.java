package com.iteva.yygh.common.exception;

import com.iteva.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(YyghException.class)
    public Result yyghExceptionHandler(YyghException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
