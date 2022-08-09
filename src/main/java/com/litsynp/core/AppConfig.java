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
@Configuration
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()

    @Bean
    public MemberService memberService() {

        System.out.println("call AppConfig.memberService");
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
