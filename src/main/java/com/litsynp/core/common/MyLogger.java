package com.litsynp.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
// 적용 대상이 클래스면 TARGET_CLASS, 인터페이스면 INTERFACES를 선택
// 이렇게 하면 MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다.
// Scope가 request여서 실행 시에 문제가 발생해 추가한 Provider인 ObjectProvider<MyLogger>가 아니라 MyLogger로 바로 주입할 수 있다!
// - 스프링 컨테이너에 myLogger라는 이름으로 CGLIB으로 생성한 MyLogger 프록시 객체를 등록한다.
// - *** Provider, 프록시의 핵심 아이디어는 진짜 객체 조회를 꼭 필요한 시점까지 지연처리 한다는 점이다.
// - 꼭 웹 스코프가 아니어도 프록시는 사용할 수 있다.
// - 가장 큰 장점은, **클라이언트의 코드를 전혀 바꾸지 않아도 된다는 점이다.**
public class MyLogger {

    private String uuid;
    private String requestUrl;

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestUrl + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean created: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }
}
