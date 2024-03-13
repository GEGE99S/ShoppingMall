package com.shop.repository;

import com.shop.entity.Item;
import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> , QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {

    Optional<Member> findByEmail(String email);

}
