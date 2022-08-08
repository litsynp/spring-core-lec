package com.litsynp.core;

import com.litsynp.core.discount.DiscountPolicy;
import com.litsynp.core.discount.RateDiscountPolicy;
import com.litsynp.core.member.MemberRepository;
import com.litsynp.core.member.MemberService;
import com.litsynp.core.member.MemberServiceImpl;
import com.litsynp.core.member.MemoryMemberRepository;
import com.litsynp.core.order.OrderService;
import com.litsynp.core.order.OrderServiceImpl;

// 사용 영역과 구성 영역의 분리
// Discount Policy가 달라져도 사용 영역의 코드는 전혀 변경할 필요가 없다.
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
