package com.example.simpledms.controller;


import com.example.simpledms.model.Dept;
import com.example.simpledms.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.example.jpaexam.controller.exam07
 * fileName       : Dept07Controller
 * author         : ds
 * date           : 2022-10-21
 * description    : 부서 컨트롤러 쿼리 메소드
 * 요약 :
 * 쿼리 메소드 : 자동으로 사용자 정의 sql 문을 생성해주는 함수
 * 목적 : 기본 함수보다 다양한 sql 문 작성하기 위해 사용
 * 사용법 : 함수이름으로 sql 문장을 작성함 ( Repository 안에 함수명만 작성 )
 * ex) JPA 클래스 == 대상 테이블
 * find == select
 * all == *
 * by == from
 * 속성 == where 컬럼
 * orderBy == order by
 * 속성 desc == 컬럼 desc
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-21        ds       최초 생성
 */
@Slf4j
// CORS 보안 : 한사이트레서 포트를 달리 사용 못함
// @CrossOrigin(허용할 사이트주소(Vue 사이트주소:포트) : CORS 보안을 허용해주는 어노테이션
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DeptController {

    @Autowired
    DeptService deptService; // @Autowired : 스프링부트가 가동될 때 생성된 객체를 하나 받아오기

//    frontend url ( 쿼리 스트링 방식) : ? 매개변수 전송방식 사용했으면 ------> backend @RequestParam
//    frontend url (파라메터 방식) : /{} 매개변수 전송방식 사용했으면 ------> backend @PathVariable
    @GetMapping("/dept")
    public ResponseEntity<Object> getDeptAll(@RequestParam(required = false) String dname) {

        try {
//            1) dname 이 null 일 경우 : 전체 검색
//            2) dname 에 값이 있을 경우 : 부서명 like 검색
            List<Dept> list = Collections.emptyList(); // null 대신 초기화

            //     1) dname 이 null 일 경우 : 전체 검색
            if(dname == null) {
                list = deptService.findAll();
            } else {
                //  2) dname 에 값이 있을 경우 : 부서명 like 검색
                list = deptService.findAllByDnameContaining(dname);
            }

            if (list.isEmpty() == false) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트에)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
//           서버 에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/dept/all")
    public ResponseEntity<Object> removeAll() {

        try {
            deptService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/dept")
    public ResponseEntity<Object> createDept(@RequestBody Dept dept) {

        try {
            Dept dept2 = deptService.save(dept);

            return new ResponseEntity<>(dept2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    Optional 객체 : null 방지 객체
//    주요함수 : get() 안에 있는 객체 꺼내기 함수
//             .isPresent() 안에 있으면 true , 없으면 false
    @GetMapping("/dept/{dno}")
    public ResponseEntity<Object> getDeptId(@PathVariable int dno) {

        try {
            Optional<Dept> optionalDept = deptService.findById(dno);

            if (optionalDept.isPresent() == true) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(optionalDept.get(), HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트에)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
//           서버 에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/dept/{dno}")
    public ResponseEntity<Object> updateDept(@PathVariable int dno,
                                             @RequestBody Dept dept) {

        try {
            Dept dept2 = deptService.save(dept);

            return new ResponseEntity<>(dept2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/dept/deletion/{dno}")
    public ResponseEntity<Object> deleteDept(@PathVariable int dno) {

        try {
            boolean bSuccess = deptService.removeById(dno);

            if (bSuccess == true) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트에)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
//           서버 에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
