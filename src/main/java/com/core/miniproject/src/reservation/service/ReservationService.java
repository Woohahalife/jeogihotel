package com.core.miniproject.src.reservation.service;

import com.core.miniproject.src.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;


}
