package pg.provingground.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import pg.provingground.domain.Ground;
import pg.provingground.dto.admin.GroundForm;
import pg.provingground.repository.GroundRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroundServiceTest {
    @Mock
    private GroundRepository groundRepository;

    @InjectMocks
    private GroundService groundService;

    @Test
    void 시험장_추가등록_성공() {
        //given
        GroundForm groundForm = new GroundForm("테스트 시험장", "Description", "10", "100");
        when(groundRepository.isDuplicateGroundName(anyString())).thenReturn(false);

        //when
        // 시험장 추가등록 메소드 호출
        groundService.addGround(groundForm);

        //then
        verify(groundRepository).save(any(Ground.class));
    }

    @Test
    void addGround_DuplicateName_ThrowsException() {
        //given
        GroundForm groundForm = new GroundForm("중복 시험장", "Description", "10", "100");
        // 중복된 이름이 입력된다고 가정.
        when(groundRepository.isDuplicateGroundName("중복 시험장")).thenReturn(true);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> groundService.addGround(groundForm));
    }

    @Test
    void 주행시험장_삭제() {
        // given
        Long groundId = 1L;
        Ground mockGround = new Ground();
        when(groundRepository.findOne(groundId)).thenReturn(mockGround);

        // when
        groundService.deleteGround(groundId);

        // then
        verify(groundRepository).delete(mockGround);
    }

    @Test
    void 시험장_전체조회() {
        // given
        List<Ground> grounds = List.of(new Ground(), new Ground());
        when(groundRepository.findAll()).thenReturn(grounds);

        // when
        List<Ground> result = groundService.findItems();

        // then
        assertEquals(grounds, result);
    }

    @Test
    void 아이디로_조회() {
        // given
        Long groundId = 1L;
        Ground mockGround = new Ground();
        when(groundRepository.findOne(groundId)).thenReturn(mockGround);

        // when
        Ground result = groundService.findOne(groundId);

        // then
        assertEquals(mockGround, result);
    }


}