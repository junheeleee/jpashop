package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //select m from member m where m.name = :name - 이 jpql 자동으로 생성
    List<Member> findByName(String name);
}