package com.joins.helloworld.security;

import com.joins.helloworld.repository.MemberRepository;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Log
@Service
public class ZerockUserService implements UserDetailsService {
    //UserDetailsService : DB에서 유저 정보를 가져오는 역할
    @Autowired
    MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{ //사용자 정보 가지오기

        return memberRepository.findById(username).filter(m -> m != null)
                .map(m -> new ZerockSecurityUser(m)).get();

//        User sampleUser = new User(username, "{noop}1111", Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//        return sampleUser;
    }

}