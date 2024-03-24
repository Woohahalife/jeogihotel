package com.core.miniproject.src.reservation.repository;

import com.core.miniproject.src.common.constant.IsVisited;
import com.core.miniproject.src.reservation.model.entity.Reservation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void 예약_등록_성공() {
        // given
        Reservation reservation = Reservation.builder()
                .roomName("객실 1")
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(1))
                .price(200000)
                .fixedNumber(2)
                .maxedNumber(4)
                .isVisited(IsVisited.VISITED)
                .build();

        // when
        Reservation saveReservation = reservationRepository.save(reservation);

        // then
        assertThat(saveReservation)
                .extracting(
                        Reservation::getId,
                        Reservation::getRoomName,
                        Reservation::getCheckIn,
                        Reservation::getCheckOut,
                        Reservation::getPrice,
                        Reservation::getFixedNumber,
                        Reservation::getMaxedNumber,
                        Reservation::getIsVisited
                ).containsExactly(
                        1L,
                        reservation.getRoomName(),
                        reservation.getCheckIn(),
                        reservation.getCheckOut(),
                        reservation.getPrice(),
                        reservation.getFixedNumber(),
                        reservation.getMaxedNumber(),
                        reservation.getIsVisited()
                );
    }
}
