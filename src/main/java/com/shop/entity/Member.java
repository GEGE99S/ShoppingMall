package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Blob;

@Getter
@Setter
@ToString
@Table
@Entity
public class Member extends BaseEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String provider;
    private String password;
    private String address;
    private String telNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(columnDefinition = "LONGTEXT")
    private String picture;

    public Member(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        role = Role.USER;
    }

    public Member() {

    }

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setTelNumber(memberFormDto.getTelNumber());
        member.setRole(Role.USER);
        member.setProvider("normal");
        return member;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public void modify(String name, Role role){
        this.name = name;
        this.role = role;
    }
}