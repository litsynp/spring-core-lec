package com.litsynp.core.order;

import com.litsynp.core.discount.DiscountPolicy;
import com.litsynp.core.member.Member;
import com.litsynp.core.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 생성자가 하나면 @Autowired를 생략할 수 있다.
    // @Autowired는 1. 타입 2. 필드명, 파라미터명 순으로 빈 이름을 매칭한다.
    // [두 개 이상일 때 해결하는 방법]
    // 1. 필드명, 파라미터명을 맞춰본다 e.g., DiscountPolicy rateDiscountPolicy
    // 2. @Qualifier를 지정한다 (@Qualifier("mainDiscountPolicy")를 클래스와 필드/파라미터 위에 넣어준다)
    // 3. @Primary로 우선순위를 지정한다 (가장 많이 사용됨) e.g., Main DB, Secondary DB
    // => 우선 순위를 @Primary로 지정하고, 서브가 필요할 때 @Qualifer를 쓰면 된다.
    // * 둘 다 붙었을 때 우선순위는 Qualifer > Primary.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
