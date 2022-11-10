package com.example.simpledms.repository;

import com.example.simpledms.model.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName : com.example.jpaexam.repository
 * fileName : EmpRepository
 * author : ds
 * date : 2022-10-20
 * description : Emp 리파지토리(== DAO ) CRUD 용 함수가 있는 인터페이스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer> {
//    ename like 검색 함수
    List<Emp> findAllByEnameContaining(String ename);

}









