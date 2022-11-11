package com.example.simpledms.service;


import com.example.simpledms.model.Emp;
import com.example.simpledms.repository.EmpRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.example.simpledms.service
 * fileName       : EmpServiceTest
 * author         : ds
 * date           : 2022-11-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-11-04        ds       최초 생성
 */
@ExtendWith(MockitoExtension.class)
class EmpServiceTest {

    @Mock
    private EmpRepository empRepository; //  @Mock 붙이면 가짜 리파지토리

    // @InjectMocks 붙이면 가짜 리파지토리를 사용해서 서비스를 쓸수있게 만듦
    @InjectMocks
    private EmpService empService;

    @DisplayName("findAll() : 서비스 조회 함수 ")
    @Test
    void findAll() {

        List<Emp> list = new ArrayList<>();

        list.add(Emp.builder()
                .eno(7777)
                .ename("DODO")
                .job("CLERK")
                .manager(8888)
                .hiredate("2022-11-04")
                .salary(900)
                .commission(500)
                .dno(10)
                .build() // .build() 마지막에 무조건 호출해서 객체가 생성됨
        );
        list.add(Emp.builder()
                .eno(7778)
                .ename("GOGO")
                .job("CLERK")
                .manager(8878)
                .hiredate("2022-11-04")
                .salary(800)
                .commission(400)
                .dno(20)
                .build() // .build() 마지막에 무조건 호출해서 객체가 생성됨
        );

//        1) given() : 기댓값 설정
        given(empRepository.findAll()).willReturn(list);

//        2) 테스트 실행
        List<Emp> list2 = empService.findAll();

//        3) 결과 검증(검토) : assert(), assertThat()(-> 사용법이 간소함) 등
//        assertThat(테스트실행값).비교함수(기댓값) : 일치하면 테스트 통과, 불일치하면 실패
        assertThat(list2.get(0).getEname()).isEqualTo("DODO");
        assertThat(list2.get(1).getEname()).isEqualTo("GOGO");
    }

    @DisplayName("removeAll() : 서비스 모두 삭제 함수 ")
    @Test
    void removeAll() {

        empService.removeAll();

        verify(empRepository, times(1)).deleteAll();

    }

    @DisplayName("save() : 서비스 부서정보 생성 함수 ")
    @Test
    void save() {
        Emp emp = Emp.builder()
                .eno(7777)
                .ename("DODO")
                .job("CLERK")
                .manager(8888)
                .hiredate("2022-11-04")
                .salary(900)
                .commission(500)
                .dno(10)
                .build();

        given(empRepository.save(any()))
                .willReturn(emp);

//        2) 테스트 실행
        Emp emp2 = empService.save(emp);

//        3) 테스트 결과 점검/검토
        assertThat(emp2.getEname()).isEqualTo("DODO");
    }

    @DisplayName("findById() : 서비스 부서번호로 조회 함수 ")
    @Test
    void findById() {

        //        1) 기댓값 설정
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

        given(empRepository.findById(anyInt()))
                .willReturn(optionalEmp);

//        2) 테스트 실행
        Optional<Emp> optionalEmp2 = empService.findById(anyInt());

//        3) 결과 검증
        assertThat(optionalEmp2.get().getEname()).isEqualTo("DODO");

    }

    @DisplayName("removeById() : 서비스 부서번호로 삭제 함수 ")
    @Test
    void removeById() {
        //        1) given() : 가정, 전제, 기댓값 설정
        given(empRepository.existsById(anyInt())).willReturn(true);

//        2) 테스트 실행
        boolean bSuccessed = empService.removeById(anyInt()); // 서비스 함수 삭제 실행

//        3) 몇번 실행되었는지 검토 / true 가 나오는 지 검토
        verify(empRepository, times(1)).deleteById(anyInt());
        assertThat(bSuccessed).isEqualTo(true);
    }

    @DisplayName("findAllByEnameContaining() : 서비스 사원명으로 조회 함수 ")
    @Test
    void findAllByEnameContaining() {

        //        1) 기댓값 설정,
        List<Emp> list = new ArrayList<>();

        list.add(Emp.builder()
                .eno(7777)
                .ename("DODO")
                .job("CLERK")
                .manager(8888)
                .hiredate("2022-11-04")
                .salary(900)
                .commission(500)
                .dno(10)
                .build() // .build() 마지막에 무조건 호출해서 객체가 생성됨
        );
        list.add(Emp.builder()
                .eno(7778)
                .ename("GOGO")
                .job("CLERK")
                .manager(8878)
                .hiredate("2022-11-04")
                .salary(800)
                .commission(400)
                .dno(20)
                .build() // .build() 마지막에 무조건 호출해서 객체가 생성됨
        );

        given(empRepository.findAllByEnameContaining(any())).willReturn(list);

//        2) 테스트 실행
        List<Emp> list2 = empService.findAllByEnameContaining(any());

//        3) 테스트 검증
        assertThat(list2.get(0).getEname()).isEqualTo("DODO");
        assertThat(list2.get(1).getEname()).isEqualTo("GOGO");

    }
}