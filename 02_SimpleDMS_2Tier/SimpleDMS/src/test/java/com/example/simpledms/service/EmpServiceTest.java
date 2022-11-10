package com.example.simpledms.service;

import com.example.simpledms.model.Dept;
import com.example.simpledms.model.Emp;
import com.example.simpledms.repository.DeptRepository;
import com.example.simpledms.repository.EmpRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * packageName : com.example.simpledms.service
 * fileName : EmpServiceTest
 * author : ds
 * date : 2022-11-04
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-04         ds          최초 생성
 */
@ExtendWith(MockitoExtension.class)
class EmpServiceTest {

    @Mock
    private EmpRepository empRepository; // @Mock 붙이면 가짜 리파지토리

    // @InjectMocks 붙이면 가짜 리파지토리를 사용해서 서비스를 쓸수있게 만듬
    @InjectMocks
    private EmpService empService;

    @Test
    void findAll() {
//        1) given() : 결과 예측치 정의
        List<Emp> list = new ArrayList<>();

        list.add(Emp.builder()
                .eno(8888)
                .ename("홍길동")
                .job("SALES")
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .dno(10)
                .build()
        );
        list.add(Emp.builder()
                .eno(8889)
                .ename("장길산")
                .job("SALES")
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .dno(10)
                .build()
        );

//        1) given() : 기댓값 설정
        given(empRepository.findAll()).willReturn(list);

//        2) 테스트 실행
        List<Emp> list2 = empService.findAll();

//        3) 결과 검증(검토) : assert()(사용법 복잡), asserThat()(사용법 간소함) 등
//        assertThat(테스트실행값).비교함수(기댓값) : 일치하면 테스트 통과, 불일치하면 실패
        assertThat(list2.get(0).getEname()).isEqualTo("홍길동");
        assertThat(list2.get(1).getEname()).isEqualTo("장길산");
    }

    @Test
    void removeAll() {

//      1) 테스트 실행
        empService.removeAll();

//      2) 위의 함수가 몇번 실행되었는지 확인
//        verify(리파지토리객체, times(몇번)) : 함수 실행 횟수 점검
        verify(empRepository, times(1)).deleteAll();
    }

    @Test
    void save() {
        //        1) 기댓값 설정 : deptRepository  객체 이용
        Emp emp = Emp.builder()
                .eno(8888)
                .ename("홍길동")
                .job("SALES")
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .dno(10)
                .build();

        given(empRepository.save(any()))
                .willReturn(emp);

//        2) 테스트 실행
        Emp emp2 = empService.save(emp);

//        3) 테스트 결과 점검/검토
        assertThat(emp2.getEname()).isEqualTo("홍길동");
    }

    @Test
    void findById() {
        Optional<Emp> empOptional = Optional.ofNullable(Emp.builder()
                .eno(8888)
                .ename("홍길동")
                .job("SALES")
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .dno(10)
                .build());

        given(empRepository.findById(anyInt())).willReturn(empOptional);

//        2) 테스트 실행
        Optional<Emp> optionalEmp = empService.findById(anyInt());

//        3) 결과 검토/검증
        assertThat(optionalEmp.get().getEname()).isEqualTo("홍길동");
    }

    @Test
    void removeById() {
        //        1) given() : 가정, 전제 , 기댓값 설정
        given(empRepository.existsById(anyInt())).willReturn(true);

//        2) 테스트 실행
        boolean bSuccessed = empService.removeById(anyInt()); // 서비스 함수 삭제 실행

//        3) 몇번 실행되었는지 검토 / true 가 나오는 지 검토
        verify(empRepository, times(1)).deleteById(anyInt());
        assertThat(bSuccessed).isEqualTo(true);
    }

    @Test
    void findAllByEnameContaining() {
//        1) given() : 결과 예측치 정의
        List<Emp> list = new ArrayList<>();

        list.add(Emp.builder()
                .eno(8888)
                .ename("홍길동")
                .job("SALES")
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .dno(10)
                .build()
        );
        list.add(Emp.builder()
                .eno(8889)
                .ename("장길산")
                .job("SALES")
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .dno(10)
                .build()
        );

        given(empRepository.findAllByEnameContaining(any())).willReturn(list);

//        2) 테스트 실행
        List<Emp> list2 = empService.findAllByEnameContaining(any());

//        3) 테스트 검증
        assertThat(list2.get(0).getEname()).isEqualTo("홍길동");
        assertThat(list2.get(1).getEname()).isEqualTo("장길산");
    }
}