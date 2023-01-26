package com.joins.helloworld.controller;

import com.joins.helloworld.domain.Member;
import com.joins.helloworld.domain.MemberRole;
import com.joins.helloworld.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log
@RequestMapping("/member/")
public class MemberController {
    @Autowired
    PasswordEncoder pwEncoder;
    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/join")
    public void join(){

    }
    @Transactional
    @PostMapping("/join")
    public String joinPost(@ModelAttribute("member")Member member){
        log.info("MEMBER: " +member);
        String encryptPw = pwEncoder.encode(member.getUpw()); //회원가입때 친 비번 가져와서 인코딩
        log.info("en: " + encryptPw);
        member.setUpw(encryptPw);//암호화한 비번 넣기
        memberRepository.save(member);
        //현재 db에 basic admin manager 3개가 다 save 되고 있음 
        //front 에서 check된 값만 받아서 하면 될듯
        return "/member/joinResult";
    }
}
