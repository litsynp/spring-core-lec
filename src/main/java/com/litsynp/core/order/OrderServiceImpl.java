package com.litsynp.core.order;

import com.litsynp.core.discount.DiscountPolicy;
import com.litsynp.core.discount.FixDiscountPolicy;
import com.litsynp.core.member.Member;
import com.litsynp.core.member.MemberRepository;
import com.litsynp.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
