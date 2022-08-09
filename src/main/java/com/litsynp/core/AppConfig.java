package com.litsynp.core;

import com.litsynp.core.discount.DiscountPolicy;
import com.litsynp.core.discount.RateDiscountPolicy;
import com.litsynp.core.member.MemberRepository;
import com.litsynp.core.member.MemberService;
import com.litsynp.core.member.MemberServiceImpl;
import com.litsynp.core.member.MemoryMemberRepository;
import com.litsynp.core.order.OrderService;
import com.litsynp.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 사용 영역과 구성 영역의 분리
// Discount Policy가 달라져도 사용 영역의 코드는 전혀 변경할 필요가 없다.
@Configuration  // @Configuration을 붙이지 않으면?
// 1. CGLIB이 동작하지 않아 일반 AppConfig가 반환된다.
// 2. 싱글톤이 깨진다. (@Bean이 붙어도 빈으로는 등록되지만 여러 번 객체가 생성되어 싱글톤이 보장되지 않는다.)
// => 고민하지 말고 @Configuration을 붙여주자.
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        // @Configuration을 붙이지 않으면 CGLIB이 동작하지 않아 memberRepository()가 그대로 호출되므로 싱글톤이 아니다.
        // @Autowired를 붙여주는 방법도 있다.
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
