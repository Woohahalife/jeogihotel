package com.core.miniproject.src.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Component
public class DateParamDefaultValueAspect {

    @Around(value = "execution(* com.core.miniproject.src.accommodation.controller.*.*(..)) && args(checkIn, checkOut, ..)", argNames = "joinPoint, checkIn, checkOut")
    public Object setDefaultParameterValues(ProceedingJoinPoint joinPoint, LocalDate checkIn, LocalDate checkOut) throws Throwable {
        // checkIn과 checkOut이 null인 경우 기본값으로 설정
        if (checkIn == null) {
            checkIn = LocalDate.now();
        }
        if (checkOut == null) {
            checkOut = LocalDate.now().plusDays(1);
        }

        // 수정된 인자로 메소드 실행(Object result : 컨트롤러 메서드 실행에 대한 결과)
        Object result = joinPoint.proceed(new Object[]{checkIn, checkOut});

//        System.out.println("result = " + result.toString());
//
//        // Aspect 내에서 수정된 값 출력(테스트용)
//        System.out.println("Modified CheckIn: " + checkIn);
//        System.out.println("Modified CheckOut: " + checkOut);

        return result;
    }
}
