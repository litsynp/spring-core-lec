package com.litsynp.core.discount;

import com.litsynp.core.annotation.MainDiscountPolicy;
import com.litsynp.core.member.Grade;
import com.litsynp.core.member.Member;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy")
//@Primary
@MainDiscountPolicy // Qualifier은 문자열을 넣어서 컴파일 시점에 잡을 수 없으므로 어노테이션을 만든다
public class FixDiscountPolicy implements DiscountPolicy {

    private final int discountFixAmount = 1000;  // 1000원 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
