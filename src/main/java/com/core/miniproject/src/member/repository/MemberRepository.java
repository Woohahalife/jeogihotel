package com.core.miniproject.src.member.repository;

import com.core.miniproject.src.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("""
            select m
            from Member m
            where m.email = :email
            """)
    Optional<Member> findByMemberEmail(@Param("email") String email);
}
