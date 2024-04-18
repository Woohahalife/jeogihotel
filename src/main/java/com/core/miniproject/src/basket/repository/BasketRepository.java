package com.core.miniproject.src.basket.repository;

import com.core.miniproject.src.basket.domain.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query("""
            select b
            from Basket b
            LEFT JOIN FETCH b.room r
            where b.member.id =:memberId
            """)
    List<Basket> findAllBasketByMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query("""
            delete from Basket b
            where b.id =:basketId
            and b.member.id =:memberId
            """)
    Integer deleteBasketByMemberIdAndBasketId(@Param("memberId") Long memberId,
                                          @Param("basketId") Long basketId);

    @Query("""
            SELECT b
            FROM Basket b
            WHERE b.room.id = :roomId
            AND (
                (b.checkIn >= :checkIn AND b.checkOut <= :checkOut)
                OR (b.checkIn < :checkIn AND b.checkOut > :checkOut)
                OR (b.checkIn < :checkOut AND b.checkOut > :checkIn)
            )
            """)
    List<Basket> findByRoomIdAndCheckInAndCheckOut(
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("roomId") Long roomId);

    @Query("""
            select b
            from Basket b
            where b.member.id =:memberId
            and b.id in :baskIds
            """)
    List<Basket> findAllByMemberIdAndIdIn(@Param("memberId") Long memberId, @Param("baskIds") List<Long> baskIds);
}
