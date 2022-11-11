package com.example.simpledms.controller;


import com.example.simpledms.model.Emp;
import com.example.simpledms.service.EmpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * packageName    : com.example.simpledms.controller
 * fileName       : EmpControllerTest
 * author         : ds
 * date           : 2022-11-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-11-04        ds       최초 생성
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmpController.class)
class EmpControllerTest {

    //    가짜 객체 받기
    @Autowired
    private MockMvc mockMvc; // 가짜 객체

    @MockBean
    private EmpService empService; // 서비스에 가짜객체 넣기

    //    잭슨(jackson) 객체 생성 : 객체(모델) to Json, json to 객체(모델) 자동 변환시켜주는 라이브러리
    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("getEmpAll() : 사원 모두 조회 함수 테스트")
    @Test
    void getEmpAll() throws Exception{

        //        1) 가짜 데이터 설정
        List<Emp> list = new ArrayList<>();

//        builder 디자인 패턴 : 생성자를 대신해서 객체를 생성하는 기능,
//        장점 : 생성자보다 사용하기 편함, Lombok 에서도 지원함(@Builder)
//        사용법 : 모델. builder()
//                 .속성()
//                 .속성2()
//                  ...
//                 .build()
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


//        2) given 설정 : 가짜 데이터를 결과로 미리 예측
        given(empService.findAll())
                .willReturn(list);
//        3) when 설정 : 테스팅 실행 -> 결과 == 확인로 미리 예측확인 ( 등일 : OK , 틀리면 : 에러 )
//        1> url/api/dept 인가?
        mockMvc.perform(get("/api/emp"))
//                2> 실행 후 OK 메세지가 나오는가?
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("removeAll() : 사원 전체 삭제 함수 테스트")
    @Test
    void removeAll() throws Exception{

        //        given() : 기댓값 설정 ( void 함수 , 리턴값이 없음 )
//        willDoNothing().given(서비스객체).함수명() : 리턴값이 없는 함수에 기댓값 설정하는 방법
        willDoNothing().given(empService).removeAll();

        mockMvc.perform(delete("/api/emp/all")) // 2) 테스트 실행
                .andExpect(status().isOk())                // 3) 테스트 결과 검토
                .andDo(print());
    }

    @DisplayName("createEmp() : 사원 생성 함수 테스트")
    @Test
    void createEmp() throws Exception{

        Emp emp = Emp.builder()
                .eno(7777)
                .ename("DODO")
                .job("CLERK")
                .manager(8888)
                .hiredate("2022-11-04")
                .salary(900)
                .commission(500)
                .dno(10)
                .build(); // .build() 마지막에 무조건 호출해서 객체가 생성됨

        given(empService.save(any()))
                .willReturn(emp);

//        테스팅 실행
        mockMvc.perform(post("/api/emp")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(객체))) : 객체 to Json 변환 => 문자열 또 변환
                        .content(objectMapper.writeValueAsString(emp)))
//                        .content("{ \"dno\" : 10, \"dname\" : \"SALES\", \"loc\": \"SEOUL\"}"))
                .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.dname").value("SALES"))
                .andDo(print());  // test 과정을 화면에 출력하는 함수
    }

    @DisplayName("getEmpId() : 사원번호로 조회 함수 테스트")
    @Test
    void getEmpId() throws Exception{

        Optional<Emp> optionalEmp = Optional.ofNullable(Emp.builder()
                .eno(7777)
                .ename("DODO")
                .job("CLERK")
                .manager(8888)
                .hiredate("2022-11-04")
                .salary(900)
                .commission(500)
                .dno(10)
                .build());

//        2) given 설정 : 가짜 데이터를 결과로 미리 예측
        given(empService.findById(anyInt()))
                .willReturn(optionalEmp);
//        3) when 설정 : 테스팅 실행 -> 결과 == 확인로 미리 예측확인 ( 등일 : OK , 틀리면 : 에러 )
//        1> url/api/dept 인가?
        mockMvc.perform(get("/api/emp/7777"))
//                2> 실행 후 OK 메세지가 나오는가?
                .andExpect(status().isOk())
//                3> Content-Type 이 APPLICATION_JSON 인가?
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                4> json 객체의 0번 데이터의 dname 이 "SALES"인가?
                .andExpect(jsonPath("$.ename").value("DODO"))
                .andDo(print());
    }

    @DisplayName("updateEmp() : 사원번호로 수정 함수 테스트")
    @Test
    void updateEmp() throws Exception{

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

        given(empService.save(any()))
                .willReturn(emp);

//        테스팅 실행
        mockMvc.perform(put("/api/emp/7777")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
//                        .content("{ \"dno\" : 10, \"dname\" : \"SALES2\", \"loc\": \"SEOUL2\"}"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.dname").value("SALES2"))
                .andDo(print());  // test 과정을 화면에 출력하는 함수
    }

    @DisplayName("deleteEmp() : 사원번호로 삭제 함수 테스트")
    @Test
    void deleteEmp() throws Exception{

        given(empService.removeById(anyInt()))
                .willReturn(true);

        mockMvc.perform(delete("/api/emp/deletion/7777")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}