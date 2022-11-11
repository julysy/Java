package com.example.simpledms.repository;


import com.example.simpledms.model.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.example.jpaexam.repository
 * fileName       : DeptRepository
 * author         : ds
 * date           : 2022-10-20
 * description    : JPA CRUD를 위한 인터페이스(==DAO)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-20        ds       최초 생성
 */
@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {

//    부서명(dname)으로 조회하는 like 검색 함수
//     1) 쿼리메소드 방식으로 함수 정의
    List<Dept> findAllByDnameContaining(String dname);
}
