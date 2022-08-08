package com.litsynp.core.singleton;

public class SingletonService {

    // 자기 자신을 static으로 갖는다 (클래스 레벨에 단 하나만 생성)
    // JVM 실행 시에 객체를 하나 생성하고 참조를 넣어둠
    private static final SingletonService instance = new SingletonService();

    // 생성자를 막는다
    private SingletonService() {
    }

    // 유일한 참조 방법
    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
