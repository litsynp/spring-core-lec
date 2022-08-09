package com.litsynp.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * singleton bean과 prototype bean을 같이 사용할 때 발생하는 주의사항
 * <p>
 * 주입 시점에만 prototype bean이 생성된다.
 */
class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
//        assertThat(count2).isEqualTo(2);  // inner prototype bean is not created; only referenced when registering client bean
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")  // default
    static class ClientBean {

        // DL (Dependency Lookup) -- ObjectProvider, JSR-330 Provider
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;  // Dependency Lookup을 간편하게 해주는 ObjectProvider (prototype 전용은 아니다)
//        private ObjectFactory<PrototypeBean> prototypeBeanProvider; // 같은 뜻, ObjectProvider는 편의 기능 제공

        // => ObjectProvider, ObjectFactory 둘 다 스프링에 의존하므로, JSR-330 Provider을 쓸 수 있다!
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;
        // - 자바 표준 (스프링 의존성이 없다)
        // - 기능이 단순하므로 단위 테스트를 만들거나 mock 코드 만들기 쉽다
        // - 딱 필요한 DL 정도만의 기능을 제공한다.
        // 단점은 너무 심플하다는 점.

//        private final PrototypeBean prototypeBean;  // uses prototype bean inside

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            // 이 때 prototype bean을 만들어서 주입된다. 따라서 singleton bean인 ClientBean은 같은 prototype bean을 참조한다.
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();  // 프로토타입 빈을 찾아주는 기능만 제공 - 필요할 때마다 스프링 컨테이너에 빈 생성 요청하는 기능
            PrototypeBean prototypeBean = prototypeBeanProvider.get(); // JSR-330 Provider
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }


    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
