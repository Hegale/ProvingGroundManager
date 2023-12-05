package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class GroundRental {

    @Id @GeneratedValue
    @Column(name = "ground_rental_id")
    private Long groundRentalId;

    @Column(name = "start_time", columnDefinition = "DATETIME")
    private LocalDateTime startTime;

    /* 시험장은 반납 시스템이 없으며, 취소만 가능함. */
    private String canceled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ground_id")
    private Ground ground;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 유저, 시험장, 날짜를 통해 차 대여 생성.
     */
    public static GroundRental createGroundRental(User user, Ground ground, LocalDateTime time) {
        GroundRental groundRental = new GroundRental();
        groundRental.user = user;
        groundRental.ground = ground;
        groundRental.startTime = time;
        groundRental.canceled = "N";
        return groundRental;
    }

    // === 비즈니스 로직 === //
    /** 시험장 예약 취소 */
    public void cancel(){
        // 취소하려는 차량이 이미 반납된 상태이면
        if (this.canceled.equals("Y")) {
            throw new IllegalStateException("이미 취소된 예약입니다.");
        } else if (!LocalDateTime.now().isBefore(this.startTime)) {
            // TODO: 이걸 여기서 체크하지 말고 controller에서 체크. 버튼을 아예 다르게 주기
            throw new IllegalStateException("이미 예약 시간이 지난 건입니다.");
        }
        // 취소 로직을 아예 이력을 없애버리는 걸로 할지, 혹은 이르게 반납 완료된 걸로 할지는 고민해보기..
        this.canceled = "Y";
        // 반납 이후 대기열에서 없애는 쪽으로 갈지 고민....
    }
}
