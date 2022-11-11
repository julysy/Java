package com.example.simpledms.service;

import com.example.simpledms.model.Faq;
import com.example.simpledms.repository.FaqRepository;
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
 * fileName       : FaqServiceTest
 * author         : ds
 * date           : 2022-11-04
 * description    : Service(서비스) 테스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-11-04        ds       최초 생성
 */
// @ExtendWith(MockitoExtension.class) : 서비스 쪽 테스트할 때 붙이는 어노테이션
//  MockitoExtension.class : 가짜객체 기능이 있는 클래스
//    서비스쪽 테스트는 순수하게 단위 테스트만 실행하면 됨 ( 스프링쪽 기능이 필요없음 )
@ExtendWith(MockitoExtension.class)
class FaqServiceTest {

    @Mock
    private FaqRepository faqRepository; //  @Mock 붙이면 가짜 리파지토리

    // @InjectMocks 붙이면 가짜 리파지토리를 사용해서 서비스를 쓸수있게 만듦
    @InjectMocks
    private FaqService faqService;

    @DisplayName("findAll() : 서비스 조회 함수 ")
    @Test
    void findAll() {

        List<Faq> list = new ArrayList<>();

        list.add(Faq.builder()
                .no(10)
                .title("제목10")
                .content("내용10")
                .build()
        );
        list.add(Faq.builder()
                .no(20)
                .title("제목20")
                .content("내용20")
                .build());

//        1) given() : 기댓값 설정
        given(faqRepository.findAll()).willReturn(list);

//        2) 테스트 실행
        List<Faq> list2 = faqService.findAll();

//        3) 결과 검증(검토) : assert(), assertThat()(-> 사용법이 간소함) 등
//        assertThat(테스트실행값).비교함수(기댓값) : 일치하면 테스트 통과, 불일치하면 실패
        assertThat(list2.get(0).getTitle()).isEqualTo(list.get(0).getTitle());
        assertThat(list2.get(1).getTitle()).isEqualTo(list.get(1).getTitle());
    }

    @DisplayName("removeAll() : 서비스 모두 삭제 함수 ")
    @Test
    void removeAll() {

//        1) 테스트 실행
        faqService.removeAll();

//        2) 위의 함수가 몇 번 실행되었는지 확인
//        verify(리파지토리객체, times(몇번)) : 함수 실행 횟수 점검
        verify(faqRepository, times(1)).deleteAll();
    }

    @DisplayName("save() : 서비스 부서정보 생성 함수 ")
    @Test
    void save() {

//        1) 기댓값 생성 : faqRepository 객체 이용
        Faq faq = Faq.builder()
                .no(10)
                .title("제목10")
                .content("내용10")
                .build();

        given(faqRepository.save(any()))
                .willReturn(faq);

//        2) 테스트 실행
        Faq faq2 = faqService.save(faq);

//        3) 테스트 결과 점검/검토
        assertThat(faq2.getTitle()).isEqualTo(faq.getTitle());
    }

    @DisplayName("findById() : 서비스 부서번호로 조회하는 함수 ")
    @Test
    void findById() {
//        1) 기댓값 설정
        Optional<Faq> optionalFaq = Optional.ofNullable(Faq.builder()
                .no(10)
                .title("제목10")
                .content("내용10")
                .build());

//        Optional<Faq> optionalFaq3 = Optional.ofNullable(Faq.builder()
//                .dno(10)
//                .dname("SALES_해킹")
//                .loc("SEOUL")
//                .build());

        given(faqRepository.findById(anyInt()))
                .willReturn(optionalFaq);

//        2) 테스트 실행
        Optional<Faq> optionalFaq2 = faqService.findById(anyInt());

//        3) 결과 검증
        assertThat(optionalFaq2.get().getTitle()).isEqualTo(optionalFaq.get().getTitle());

    }

    @DisplayName("removeById() : 서비스 부서번호로 삭제하는 함수 ")
    @Test
    void removeById() {
//        1) given() : 가정, 전제, 기댓값 설정
        given(faqRepository.existsById(anyInt())).willReturn(true);

//        2) 테스트 실행
        boolean bSuccessed = faqService.removeById(anyInt()); // 서비스 함수 삭제 실행

//        3) 몇번 실행되었는지 검토 / true 가 나오는 지 검토
        verify(faqRepository, times(1)).deleteById(anyInt());
        assertThat(bSuccessed).isEqualTo(true);
    }

    @DisplayName("findAllByTitleContaining() : 서비스 부서명으로 검색하는 함수 ")
    @Test
    void findAllByTitleContaining() {
//        1) 기댓값 설정,
        List<Faq> list = new ArrayList<>();

        list.add(Faq.builder()
                .no(10)
                .title("제목10")
                .content("내용10")
                .build());

        list.add(Faq.builder()
                .no(20)
                .title("제목20")
                .content("내용20")
                .build());

        given(faqRepository.findAllByTitleContaining(any())).willReturn(list);

//        2) 테스트 실행
        List<Faq> list2 = faqService.findAllByTitleContaining(any());

//        3) 테스트 검증
        assertThat(list2.get(0).getTitle()).isEqualTo("제목10");
        assertThat(list2.get(1).getTitle()).isEqualTo("제목20");

    }


}