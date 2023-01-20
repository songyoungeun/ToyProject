package com.joins.helloworld.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_memeber_roles")
@EqualsAndHashCode(of = "fno")
@ToString
public class MemberRole { //회원이 가지는 권한에 대한 이름을 가짐
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;
    private String roleName;
}
