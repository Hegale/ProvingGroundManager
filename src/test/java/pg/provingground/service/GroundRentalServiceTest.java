package pg.provingground.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.GroundRentalDto;
import pg.provingground.dto.admin.GroundRentalSearchForm;
import pg.provingground.exception.NoAvailableGroundException;
import pg.provingground.repository.GroundRentalRepository;
import pg.provingground.repository.GroundRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroundRentalServiceTest {
    @Mock
    private GroundRentalRepository groundRentalRepository;
    @Mock
    private UserService userService;
    @Mock
    private GroundRepository groundRepository;

    @InjectMocks
    private GroundRentalService groundRentalService;

    private Long userId;
    private Long groundId;
    private Ground ground;
    LocalDateTime time;

    @BeforeEach
    void setUp() {
        userId = 1L;
        groundId = 1L;
        ground = new Ground();
        ground.setGroundId(groundId);
        time = LocalDateTime.now();
        //MockitoAnnotations.initMocks(this);
    }

    @Test
    public void 시험장예약_성공() {
        //given
        // 대여 가능한 상황을 가정
        when(groundRepository.findOne(groundId)).thenReturn(ground);
        when(groundRentalRepository.isRentalAble(any(), any())).thenReturn(true);

        // save의 매개변수가 되는 객체를 캡처
        ArgumentCaptor<GroundRental> captor = ArgumentCaptor.forClass(GroundRental.class);
        when(groundRentalRepository.save(captor.capture())).thenAnswer(invocation -> {
            GroundRental captured = captor.getValue();
            captured.setGroundRentalId(1L);
            return captured;
        });

        //when
        Long groundRentalId = groundRentalService.rental(userId, groundId, time);

        //then
        verify(groundRentalRepository).save(any(GroundRental.class));
        assertNotNull(groundRentalId);
        assertEquals(1L, groundRentalId);
    }

    @Test
    public void 시험장예약_실패() {
        //given
        // 대여 불가능한 상황을 가정
        when(groundRepository.findOne(groundId)).thenReturn(ground);
        when(groundRentalRepository.isRentalAble(any(), any())).thenReturn(false);

        //when
        //then
        assertThrows(NoAvailableGroundException.class, () -> {
            groundRentalService.rental(userId, groundId, time);
        });
    }

    @Test
    void 시험장예약_취소() {
        // given
        Long rentalId = 1L;
        GroundRental mockRental = mock(GroundRental.class);

        when(groundRentalRepository.findOne(rentalId)).thenReturn(mockRental);

        // when
        groundRentalService.cancelRental(rentalId);

        // then
        verify(mockRental).cancel();
    }

    @Test
    public void 예약가능_시간대_반환() {
        // given
        String selectedDate = "2024-01-01";

        Map<LocalTime, Long> countByTime = new HashMap<>();
        // 12:00과 14:00에 이미 대여가 예약된 상황
        countByTime.put(LocalTime.of(12, 0), 3L);
        countByTime.put(LocalTime.of(14, 0), 2L);

        // 12시 3대, 14시 2대 예약된 상황 설정
        when(groundRentalRepository.findAvailableTimesCount(groundId, LocalDate.parse(selectedDate))).thenReturn(countByTime);

        // when
        List<String> availableTimes = groundRentalService.getAvailableTimes(groundId, selectedDate);

        // then
        assertNotNull(availableTimes);
        assertTrue(availableTimes.contains("10")); // 10:00은 가능한 시간대
        assertFalse(availableTimes.contains("12")); // 12:00은 이미 만석
        assertTrue(availableTimes.contains("16")); // 16:00은 가능한 시간대
    }

    @Test
    void 관리자_조건검색() {
        // Given
        GroundRentalSearchForm searchForm = new GroundRentalSearchForm();
        searchForm.setStartDate("2023-04-10");
        searchForm.setEndDate("2023-04-15");

        List<GroundRental> groundRentals = new ArrayList<>();
        groundRentals.add(new GroundRental()); // Add test ground rentals
        when(groundRentalRepository.searchGroundRentals(searchForm)).thenReturn(groundRentals);

        // When
        List<GroundRentalDto> result = groundRentalService.searchGroundRentalsByConditions(searchForm);

        // Then
        assertEquals(groundRentals.size(), result.size());
        assertTrue(result.stream().allMatch(dto -> dto instanceof GroundRentalDto));
    }

}