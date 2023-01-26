package com.joins.helloworld.repository;

import com.joins.helloworld.domain.Member;
import org.springframework.data.repository.CrudRepository;

//JpaRepository를 상속했기 때문에 @Repository 어노테이션 불필요
public interface MemberRepository extends CrudRepository<Member, String> {

}