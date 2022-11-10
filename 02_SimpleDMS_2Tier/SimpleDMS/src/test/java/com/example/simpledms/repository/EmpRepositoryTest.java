package com.example.simpledms.repository;

import com.example.simpledms.model.Dept;
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
 * packageName : com.example.simpledms.repository
 * fileName : EmpRepositoryTest
 * author : ds
 * date : 2022-11-04
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-04         ds          최초 생성
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmpRepositoryTest {

    @Autowired
    private EmpRepository empRepository;

    @Test
    void findAllByEnameContaining() {

//        1) 가짜 데이터 설정
        Optional<Emp> empOptional = Optional.ofNullable(Emp.builder()
                .ename("홍길동")
                .job("SALES")
                .manager(8888)
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .build());

//        임시로 가짜 데이터를 insert
        Emp emp2 = empRepository.save(empOptional.get());

//        2) 테스트 실행
        List<Emp> list = empRepository.findAllByEnameContaining("홍길동");

//        3) 테스트 검증
        assertThat(list.get(0).getEname()).isEqualTo("홍길동");
    }

    @Test
    void save() {
//        1) 가짜 데이터 설정
        Optional<Emp> empOptional = Optional.ofNullable(Emp.builder()
                .ename("홍길동")
                .job("SALES")
                .manager(8888)
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .build());

//        2) 테스트 실행
        Emp emp2 = empRepository.save(empOptional.get());

//        3) 테스트 검증
        assertThat(emp2.getEname()).isEqualTo("홍길동");
    }

    @Test
    void deleteAll() {
//        1) 가짜 데이터 설정
        Optional<Emp> empOptional = Optional.ofNullable(Emp.builder()
                .ename("홍길동")
                .job("SALES")
                .manager(8888)
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .build());

//      가짜 임시 데이터 insert
        empRepository.save(empOptional.get());

//        2) 테스트 실행 : 모두 삭제
        empRepository.deleteAll();

//        3) 테스트 검증
        assertThat(empRepository.findAll()).isEqualTo(Collections.emptyList());
    }
}












