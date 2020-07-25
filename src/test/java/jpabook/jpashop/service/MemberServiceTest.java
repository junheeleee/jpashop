package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit에 Spring을 엮어서 사용할래!
@SpringBootTest
@Transactional // Test에서 기본적으로 트랜잭션 끝나면 Rollback 시킴. 당연히 서비스나 이런 곳에선 롤백안됨.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    //Test는 기본이 Rollback이다. 왜냐하면 DB에 남지 않아야 테스트가 원활하게 반복할 수 있기 때문이다.
    @Test
    public void 회원가입() throws Exception {
        //given 
        Member member = new Member();
        member.setName("kim");

        //when 
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class) // try catch문으로 예외 안잡아도 된다.
    public void 중복_회원_예외() throws Exception {
        //given 
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
//        try {
        memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }

        //then
        fail("예외가 발생해야 한다.");
    }
}