package com.example.simpledms.repository;



import com.example.simpledms.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName    : com.example.jpaexam.repository
 * fileName       : faqRepository
 * author         : ds
 * date           : 2022-10-20
 * description    : JPA CRUD를 위한 인터페이스(==DAO)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-10-20        ds       최초 생성
 */
@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer> {

//    title 으로 조회하는 like 검색 함수
//     1) 쿼리메소드 방식으로 함수 정의
    List<Faq> findAllByTitleContaining(String title);
}
