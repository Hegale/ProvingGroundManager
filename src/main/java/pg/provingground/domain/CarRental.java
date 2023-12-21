package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
// carTypeId + returned로 검색 잦음
@Table(name = "car_rental", indexes = {
        @Index(name = "idx_car_rental_id", columnList = "car_rental_id"),
        @Index(name = "idx_start_time_returned", columnList = "start_time, returned"),
        @Index(name = "idx_car", columnList = "car_id"),
        @Index(name = "idx_user", columnList = "user_id"),
        @Index(name = "idx_test", columnList = "test_id"),
})
public class CarRental {

    @Id
    @GeneratedValue
    @Column(name = "car_rental_id")
    private Long carRentalId;

    @Column(columnDefinition = "DATETIME", name = "start_time")
    private LocalDateTime startTime;

    private String returned;

    private FileMetaData fileMetaData;

    @ManyToOne(fetch = FetchType.LAZY)// 고려
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    // === 생성 메서드 === //

    /**
     * 유저, 대여 차량, 날짜를 통해 차 대여 생성.
     */
    public static CarRental createCarRental(User user, Car car, LocalDateTime time) {
        CarRental carRental = new CarRental();
        carRental.user = user;
        carRental.car = car;
        carRental.startTime = time;
        carRental.returned = "N";
        return carRental;
    }

    // === 비즈니스 로직 === //
    /** 차량 대여 취소 */
    public void cancel(){
        // 취소하려는 차량이 이미 반납된 상태이면
        if (this.returned.equals("Y")) {
            throw new IllegalStateException("이미 반납된 차량입니다.");
        } else if (!LocalDateTime.now().isBefore(this.startTime)) {
            // TODO: 이걸 여기서 체크하지 말고 controller에서 체크. 버튼을 아예 다르게 주기
            throw new IllegalStateException("이미 예약 시간이 지난 차량입니다.");
        }
        // 취소 로직을 아예 이력을 없애버리는 걸로 할지, 혹은 이르게 반납 완료된 걸로 할지는 고민해보기..
        this.returned = "Y";
        // 반납 이후 대기열에서 없애는 쪽으로 갈지 고민....
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setFileMetaData(FileMetaData fileMetaData) {
        System.out.println("메타데이터 세팅!");
        this.fileMetaData = fileMetaData;
    }

    public void setCarRentalId(long l) {
        this.carRentalId = l;
    }

    // 테스트 위함
    public void setCar(Car car) {
        this.car = car;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setReturned(String returned) {
        this.returned = returned;
    }
}
