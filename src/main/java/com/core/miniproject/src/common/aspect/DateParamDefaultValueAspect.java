package com.core.miniproject.src.common.aspect;

import com.core.miniproject.src.common.exception.BaseException;
import com.core.miniproject.src.common.response.BaseResponseStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Aspect
@Component
public class DateParamDefaultValueAspect {

    @Around(value = "execution(* com.core.miniproject.src.accommodation.controller.AccommodationPublicController.find*(..))", argNames = "joinPoint")
    public Object setDefaultParameterValues(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        System.out.println("args = " + args.length);

//         checkIn과 checkOut이 null인 경우 기본값으로 설정

        try {
            LocalDate checkIn = args[0] != null ? (LocalDate) args[0] : LocalDate.now();
            LocalDate checkOut = args[1] != null ? (LocalDate) args[1] : LocalDate.now().plusDays(1);
            String locationType = args[2] != null ? (String) args[2] : "서울";
            String accommodationType = args[3] != null ? (String) args[3] : "호텔";
            int fixedMember = args[4] != null ? (int) args[4] : 2;
            int price = args[5] != null ? (int) args[5] : 30000;

        return joinPoint.proceed(new Object[]{checkIn, checkOut, locationType, accommodationType, fixedMember, price, args[6], args[7]});

        } catch (DateTimeParseException | NumberFormatException e) {
            throw new BaseException(BaseResponseStatus.TYPE_MISMATCH);
        }
    }
}
