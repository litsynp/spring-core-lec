package com.litsynp.core;

import com.litsynp.core.member.Grade;
import com.litsynp.core.member.Member;
import com.litsynp.core.member.MemberService;
import com.litsynp.core.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member);
        System.out.println("find member = " + findMember);
    }
}
