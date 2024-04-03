package com.core.miniproject.src.member.domain.entity;

import com.core.miniproject.src.common.constant.Role;
import com.core.miniproject.src.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void grantRoleToAdmin(Role role) {
        if(this.role == Role.USER && role == Role.ADMIN) {
            System.out.println("권한이 변경되었습니다.");
            this.role = role;
        }
    }
}
