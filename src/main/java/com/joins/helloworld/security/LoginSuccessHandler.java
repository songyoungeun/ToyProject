package com.joins.helloworld.security;

import lombok.extern.java.Log;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
public class LoginSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {
    //HttpSession 에 dest값이 존재하는 경우 redirect경로를 dest값으로 지정
    // dest값이 존재하지 않는 경우 기존 방식으로 동작
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response){
        log.info("-----------determineTargetUrl------------------");
        Object dest = request.getSession().getAttribute("dest");
        String nextURL = null;
        if(dest != null){
            request.getSession().removeAttribute("dest");
            nextURL = (String) dest;
        }
        else{
            nextURL = super.determineTargetUrl(request, response);
        }
        log.info("-----------"+nextURL+"------------------");
        return nextURL;
    }

}
