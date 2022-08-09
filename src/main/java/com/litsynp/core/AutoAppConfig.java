package com.litsynp.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( // @Component 붙은 컴포넌트를 전부 찾아 등록
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)  // AppConfig, TestConfig 제외
)
public class AutoAppConfig {
}
