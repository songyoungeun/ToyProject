package com.joins.helloworld.interceptor;

import lombok.extern.java.Log;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
public class LoginCheckInterceptor  extends HandlerInterceptorAdapter {

    //preHandle 은 컨트롤러 호출 전에 동작함
    //예) 댓글을 추가하기 위해 로그인으로 이동하는 경우, 로그인 후에는 현재 페이지를 보이도록 지정하는 것
    // 로그인이 처리되어야 하는 상황이라면 '/login?dest=로그인 후 이동 경로' 를 url로 처리한다. front에서
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        log.info("preHandle......");
        String dest = request.getParameter("dest");
        if(dest != null){
            request.getSession().setAttribute("dest",dest); //파라미터의 이름에 dest가 존재한다면 새션에 저장

        }
        return super.preHandle(request,response,handler);
    }
    //postHandler 컨트롤러 메서드 실행 직후 view페이지 렌더링 되기 전에 실행됨
    //afterCompletion view페이지가 렌더링 되고 난 후 실행됨
}
