package com.example.simpledms.controller;

import com.example.simpledms.model.Dept;
import com.example.simpledms.model.Emp;
import com.example.simpledms.service.DeptService;
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

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName : com.example.simpledms.controller
 * fileName : EmpControllerTest
 * author : ds
 * date : 2022-11-04
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-04         ds          최초 생성
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmpController.class)
class EmpControllerTest {

    @Autowired
    private MockMvc mockMvc; // 가짜 객체

    @MockBean
    private EmpService empService; // 서비스에 가짜객체 넣기

    //    잭슨(jackson) 객체 생성 : 객체(모델) to Json, Json to 객체(모델) 자동 변환시켜주는 라이브러리
    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("getEmpAll() : 사원 조회 함수 테스트")
    @Test
    void getEmpAll() throws Exception {

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

        given(empService.findAll())
                .willReturn(list);
// -----------------------------------------------------
//        2) 테스트 실행

        mockMvc.perform(get("/api/emp")) // 2) 테스트 실행
                .andExpect(status().isOk())        // 3) 테스트 실행 결과 검토
                .andDo(print());                   // 참고) 화면에 결과 출력(생략)
    }

    @Test
    void removeAll() throws Exception {
//        1) given() : 기댓값 설정(전제)
        willDoNothing().given(empService).removeAll();

//        2) 테스트 실행 / 결과 검토
        mockMvc.perform(delete("/api/emp/all")) // 테스트 실행
                .andExpect(status().isOk())               // 테스트 결과 검토
                .andDo(print());                          // 테스트 과정/결과를 화면에 출력
    }

    @DisplayName("createEmp() : 사원 생성 함수 테스트")
    @Test
    void createEmp() throws Exception {
//        1) 테스트를 위한 기댓값 설정
        Emp emp = Emp.builder()
                .eno(8888)
                .ename("홍길동")
                .job("SALES")
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .dno(10)
                .build();

        given(empService.save(any()))
                .willReturn(emp);

        //실행(.perform()) / 결과검토(.andExpect())
        mockMvc.perform(post("/api/emp")    // 2) .perform() : 테스트 실행 함수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())     // 3) .andExpect() : 테스트 결과 검토
                .andDo(print()); // print() 테스트 과정을 화면에 출력하는 함수
    }

    @Test
    void getEmpId() throws Exception {

//        1) Optional 객체 함수 :
//             Optional.ofNullable(객체) : 객체 넣기
        Optional<Emp> empOptional = Optional.ofNullable(Emp.builder()
                    .eno(8888)
                    .ename("홍길동")
                    .job("SALES")
                    .hiredate("1982-01-23 00:00:00")
                    .salary(1300)
                    .dno(10)
                    .build());

        given(empService.findById(anyInt()))
                .willReturn(empOptional);
// -----------------------------------------------------
//        2) 테스트 실행

        mockMvc.perform(get("/api/emp/8888")) // 2) 테스트 실행
                .andExpect(status().isOk())        // 3) 테스트 실행 결과 검토
                .andDo(print());                   // 참고) 화면에 결과 출력(생략)
    }

    @Test
    void updateEmp() throws Exception {
        //        1) 테스트를 위한 기댓값 설정
        Emp emp = Emp.builder()
                .eno(8888)
                .ename("홍길동")
                .job("SALES")
                .hiredate("1982-01-23 00:00:00")
                .salary(1300)
                .dno(10)
                .build();

        given(empService.save(any()))
                .willReturn(emp);

        //실행(.perform()) / 결과검토(.andExpect())
        mockMvc.perform(put("/api/emp/8888")    // 2) .perform() : 테스트 실행 함수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())     // 3) .andExpect() : 테스트 결과 검토
                .andDo(print()); // print() 테스트 과정을 화면에 출력하는 함수
    }

    @Test
    void deleteEmp() throws Exception {
        //        1) given() : 기댓값 설정(전제)
        given(empService.removeById(anyInt())).willReturn(true);

//        2) 테스트 실행 / 결과 검토
        mockMvc.perform(delete("/api/emp/deletion/8888")) // 테스트 실행
                .andExpect(status().isOk())               // 테스트 결과 검토
                .andDo(print());                          // 테스트 과정/결과를 화면에 출력
    }
}