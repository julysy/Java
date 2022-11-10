package com.example.simpledms.service;

import com.example.simpledms.model.Dept;
import com.example.simpledms.repository.DeptRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
 * fileName : DeptServiceTest
 * author : ds
 * date : 2022-11-04
 * description : Service(서비스) 테스트
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-04         ds          최초 생성
 */
// @ExtendWith(MockitoExtension.class) : 서비스쪽 테스트할때 주로 붙이는 @
//                         MockitoExtension.class : 가짜객체 기능이 있는 클래스
// 서비스쪽 테스트 : 순수하게 단위 테스트만 실행하면됨 ( 스프링쪽 기능이 필요없음 )
@ExtendWith(MockitoExtension.class)
class DeptServiceTest {

    @Mock
    private DeptRepository deptRepository; // @Mock 붙이면 가짜 리파지토리

    // @InjectMocks 붙이면 가짜 리파지토리를 사용해서 서비스를 쓸수있게 만듬
    @InjectMocks
    private DeptService deptService;

    @DisplayName("findAll() : 서비스 조회 함수 ")
    @Test
    void findAll() {
        List<Dept> list = new ArrayList<>();

        list.add(Dept.builder()
                .dno(10)
                .dname("SALES")
                .loc("SEOUL")
                .build() // .build() 마지막에 무조건 호출해서 객체가 생성됨
        );
        list.add(Dept.builder()
                .dno(20)
                .dname("ACCOUNTING")
                .loc("BUSAN")
                .build() // .build() 마지막에 무조건 호출해서 객체가 생성됨
        );

//        1) given() : 기댓값 설정
        given(deptRepository.findAll()).willReturn(list);

//        2) 테스트 실행
        List<Dept> list2 = deptService.findAll();

//        3) 결과 검증(검토) : assert()(사용법 복잡), asserThat()(사용법 간소함) 등
//        assertThat(테스트실행값).비교함수(기댓값) : 일치하면 테스트 통과, 불일치하면 실패
        assertThat(list2.get(0).getDname()).isEqualTo(list.get(0).getDname());
        assertThat(list2.get(1).getDname()).isEqualTo(list.get(1).getDname());
    }

    @DisplayName("removeAll() : 서비스 모두 삭제 함수 ")
    @Test
    void removeAll() {

//      1) 테스트 실행
        deptService.removeAll();

//      2) 위의 함수가 몇번 실행되었는지 확인
//        verify(리파지토리객체, times(몇번)) : 함수 실행 횟수 점검
        verify(deptRepository, times(1)).deleteAll();
    }

    @DisplayName("save() : 서비스 부서정보 생성 함수 ")
    @Test
    void save() {
//        1) 기댓값 설정 : deptRepository  객체 이용
        Dept dept = Dept.builder()
                .dno(10)
                .dname("SALES")
                .loc("SEOUL")
                .build();

        given(deptRepository.save(any()))
                .willReturn(dept);

//        2) 테스트 실행
        Dept dept2 = deptService.save(dept);

//        3) 테스트 결과 점검/검토
        assertThat(dept2.getDname()).isEqualTo(dept.getDname());
    }

    @DisplayName("findById() : 서비스 부서번호로 조회하는 함수 ")
    @Test
    void findById() {
//        1) 기댓값 설정
        Optional<Dept> optionalDept = Optional.ofNullable(Dept.builder()
                .dno(10)
                .dname("SALES")
                .loc("SEOUL")
                .build());

////        2) 기댓값이 서비스 함수가 실행되면서 _해킹이 붙어있음
//        Optional<Dept> optionalDept3 = Optional.ofNullable(Dept.builder()
//                .dno(10)
//                .dname("SALES_해킹")
//                .loc("SEOUL")
//                .build());

//        given(deptService.findById(anyInt())).willReturn(optionalDept);
        given(deptRepository.findById(anyInt())).willReturn(optionalDept);

//        2) 테스트 실행
        Optional<Dept> optionalDept2 = deptService.findById(anyInt());

//        3) 결과 검토/검증
        assertThat(optionalDept2.get().getDname())
                .isEqualTo(optionalDept.get().getDname());
    }

    @DisplayName("removeById() : 서비스 부서번호로 삭제하는 함수 ")
    @Test
    void removeById() {
//        1) given() : 가정, 전제 , 기댓값 설정
        given(deptRepository.existsById(anyInt())).willReturn(true);

//        2) 테스트 실행
        boolean bSuccessed = deptService.removeById(anyInt()); // 서비스 함수 삭제 실행

//        3) 몇번 실행되었는지 검토 / true 가 나오는 지 검토
        verify(deptRepository, times(1)).deleteById(anyInt());
        assertThat(bSuccessed).isEqualTo(true);
    }

    @DisplayName("findAllByDnameContaining() : 서비스 부서명으로 검색하는 함수 ")
    @Test
    void findAllByDnameContaining() {
//        1) 기댓값 설정,
        List<Dept> list = new ArrayList<>();

        list.add(Dept.builder().dno(10)
                .dname("SALES")
                .loc("SEOUL")
                .build()
        );

        list.add(Dept.builder().dno(20)
                .dname("ACCOUNTING")
                .loc("PUSAN")
                .build()
        );

        given(deptRepository.findAllByDnameContaining(any())).willReturn(list);

//        2) 테스트 실행
        List<Dept> list2 = deptService.findAllByDnameContaining(any());

//        3) 테스트 검증
        assertThat(list2.get(0).getDname()).isEqualTo("SALES");
        assertThat(list2.get(1).getDname()).isEqualTo("ACCOUNTING");
    }
}








