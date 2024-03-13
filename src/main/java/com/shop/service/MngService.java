package com.shop.service;

import com.shop.constant.Role;
import com.shop.dto.MemberSearchDto;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MngService {
    private final MemberRepository memberRepository;

    public Page<Member> getAdminMemberPage(MemberSearchDto memberSearchDto, Pageable pageable){
        return memberRepository.getAdminMemberPage(memberSearchDto, pageable);
    }

    public void modifyMember(Map<String, Object> user){
        String email = (String) user.get("email");
        String name = (String) user.get("name");
        String stringRole = (String) user.get("role");
        Role role;
        if (stringRole.equals("USER")){
            role=Role.USER;
        }
        else {
            role=Role.ADMIN;
        }

        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        member.modify(name,role);
        memberRepository.save(member);
    }
}