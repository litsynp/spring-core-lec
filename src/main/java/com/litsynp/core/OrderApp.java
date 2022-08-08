package com.litsynp.core;

import com.litsynp.core.member.Grade;
import com.litsynp.core.member.Member;
import com.litsynp.core.member.MemberService;
import com.litsynp.core.member.MemberServiceImpl;
import com.litsynp.core.order.Order;
import com.litsynp.core.order.OrderService;
import com.litsynp.core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice = " + order.calculatePrice());
    }
}
