package com.litsynp.core;

import com.litsynp.core.discount.FixDiscountPolicy;
import com.litsynp.core.member.MemberService;
import com.litsynp.core.member.MemberServiceImpl;
import com.litsynp.core.member.MemoryMemberRepository;
import com.litsynp.core.order.OrderService;
import com.litsynp.core.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
