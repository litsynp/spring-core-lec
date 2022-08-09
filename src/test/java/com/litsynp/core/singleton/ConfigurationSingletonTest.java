package com.litsynp.core.singleton;

import com.litsynp.core.AppConfig;
import com.litsynp.core.member.MemberRepository;
import com.litsynp.core.member.MemberServiceImpl;
import com.litsynp.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // 세 개 다 같다!
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);  // 스프링이 빈을 등록하는 과정에서 바이트코드를 조작 -- 실제로는 AppConfig의 자식 타입

        System.out.println("bean = " + bean.getClass());  // CGLIB 바이트 조작 라이브러리로 해당 클래스를 상속 받은 다른 클래스를 스프링 빈으로 등록한다.
        // 따라서 AppConfig에서 호출한 클래스가 바로 싱글톤이 보장되도록 한다.  (이미 빈이 등록됐다면 컨테이너에서 찾아서, 등록되지 않았다면 기존 로직을 호출해 새로 객체를 생성해 컨테이너에 등록해 반환)
    }
}
