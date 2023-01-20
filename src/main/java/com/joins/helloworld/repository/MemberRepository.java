package com.joins.helloworld.repository;

import com.joins.helloworld.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {

}