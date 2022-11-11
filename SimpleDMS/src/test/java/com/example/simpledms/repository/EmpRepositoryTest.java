package com.example.simpledms.repository;

import com.example.simpledms.model.Emp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : com.example.simpledms.repository
 * fileName       : EmpRepositoryTest
 * author         : ds
 * date           : 2022-11-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-11-04        ds       최초 생성
 */
// @ExtendWith(SpringExtension.class) : 테스트시 스프링 함수 또는 기능을 제공해주는 어노테이션
@ExtendWith(SpringExtension.class)
// @DataJpaTest : Repository 를 테스트하기 위한 어노테이션
//            DB 가 필요, 테스트 후 자동 롤백시켜줌 ( 데이터를 insert/update/delete 했으면 명령 취소 )
@DataJpaTest
// DB 접근 위한 어노테이션
// #데이터베이스 : 간이(내장) DB (스프링부트 있음)
// 아래 옵션은 외장 DB ( 오라클 DB )로 테스트 진행한다는 옵션
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmpRepositoryTest {

    @Autowired
    private EmpRepository empRepository;


    @Test
    void findAllByEnameContaining() {

        //        1) 가짜 데이터 설정
        Optional<Emp> optionalEmp = Optional.ofNullable(Emp.builder()
                .eno(7777)
                .ename("DODO")
                .job("CLERK")
                .manager(8888)
                .hiredate("2022-11-04")
                .salary(900)
                .commission(500)
                .dno(10)
                .build()
        );

//        임시로 가짜 데이터를 insert
        Emp emp2 = empRepository.save(optionalEmp.get());

//        2) 테스트 실행
        List<Emp> list = empRepository.findAllByEnameContaining("DODO");

//        3) 테스트 검증
        assertThat(list.get(0).getEname()).isEqualTo("DODO");
    }

    //    save()
    @Test
    void save() {

        //        1) 가짜 데이터 설정
        Optional<Emp> optionalEmp = Optional.ofNullable(Emp.builder()
                .eno(7777)
                .ename("DODO")
                .job("CLERK")
                .manager(8888)
                .hiredate("2022-11-04")
                .salary(900)
                .commission(500)
                .dno(10)
                .build()
        );

//        2) 테스트 실행
        Emp emp2 = empRepository.save(optionalEmp.get());
//        3) 테스트 검증
//          테스트 검증 후 자동 데이터 insert 취소(롤백)
        assertThat(emp2.getEname()).isEqualTo("DODO");
    }

    @Test
    void deleteAll() {

        //        1) 가짜 데이터 설정
        Optional<Emp> optionalEmp = Optional.ofNullable(Emp.builder()
                .eno(7777)
                .ename("DODO")
                .job("CLERK")
                .manager(8888)
                .hiredate("2022-11-04")
                .salary(900)
                .commission(500)
                .dno(10)
                .build()
        );

        //        임시로 가짜 데이터를 insert
        empRepository.save(optionalEmp.get());

//        2) 테스트 실행 : 모두 삭제
        empRepository.deleteAll();

//        3) 테스트 검증
        assertThat(empRepository.findAll()).isEqualTo(Collections.emptyList());
    }


}