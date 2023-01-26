package com.joins.helloworld;

import com.joins.helloworld.domain.Member;
import com.joins.helloworld.domain.MemberRole;
import com.joins.helloworld.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.java.Log;

@SpringBootTest
@Commit
@Log
public class MemberTests {
    @Autowired
    private MemberRepository repo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void testInsert(){
        for(int i = 0; i <= 100; i++){
            Member member = new Member();
            member.setUid("user" + i);
            member.setUpw("pw" +i);
            member.setUname("사용자" + i);

            MemberRole role = new MemberRole();
            if(i <= 80){
                role.setRoleName("BASIC");
            } else if (i <= 90) {
                role.setRoleName("MANAGER");
            }else{
                role.setRoleName("ADMIN");
            }
            member.setRoles(Arrays.asList(role));
            repo.save(member);
        }
    }
    @Test
    public void testRead(){
        String uid = "user85";
        Optional<Member> result = repo.findById(uid);

        if(result.isPresent()){
            Member member = result.get();
            System.out.println(member);
            System.out.println(member.getUname());

        }
    }
    @Test
    public void testUpdateOleMember(){
        List<String> ids = new ArrayList<>();
        for(int i = 0; i <= 100; i++){
            ids.add("user"+i);
        }
        repo.findAllById(ids).forEach(member -> {
            member.setUpw(passwordEncoder.encode(member.getUpw()));
            repo.save(member);
        });
    }
}
