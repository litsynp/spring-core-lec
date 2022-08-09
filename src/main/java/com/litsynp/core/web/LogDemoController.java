package com.litsynp.core.web;

import com.litsynp.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestUrl = request.getRequestURL().toString();
        System.out.println("myLogger = " + myLogger.getClass());  // 진짜 MyLogger가 아닌, CGLIB이 추가한 MyLogger - 클라이언트는 동일하게 사용할 수 있다 (다형성)
        myLogger.setRequestUrl(requestUrl);

        myLogger.log("controller test");  // 프록시 객체는 MyLogger의 진짜 log() 함수를 실행한다.
        Thread.sleep(1000);
        logDemoService.logic("testId");  // 같은 HTTP 요청이면 서비스에서도 같은 UUID가 나올 것이다.
        return "OK";
    }
}
