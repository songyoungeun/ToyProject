package com.joins.helloworld.security;

import com.joins.helloworld.domain.Member;
import com.joins.helloworld.domain.MemberRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
public class ZerockSecurityUser extends User {
    private static final String ROLE_PREFIX = "ROLE_";
    private Member member;

    public ZerockSecurityUser( String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public ZerockSecurityUser(Member member){
        super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));
        //{noop} 암호를 인코딩하지 않으려면 NoopPasswordEncoder를 사용할 수 있습니다.
        //NoOpPasswordEncoder 는 레거시 구현임을 나타 내기 위해 더 이상 사용되지 않으며 이를 사용하는 것은 안전하지 않은 것으로 간주됨
        this.member = member;
    }
    private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles){
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
        return list;
    }
}
