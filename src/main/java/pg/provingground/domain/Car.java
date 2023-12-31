package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.dto.admin.CarForm;

import java.util.List;

@Entity
@Getter @Setter
@Table(indexes = {
        @Index(name = "idx_car_id", columnList = "car_id"),
        @Index(name = "idx_car_type", columnList = "car_type")
})
/** 차량 엔티티 */
public class Car {

    @Id @GeneratedValue
    @Column(name = "car_id")
    private Long carId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_type_id")
    private CarType type;

    @Column(unique = true)
    private String number;

    private Long fuel; // 잔여 연료, cc단위

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarRental> carRentals;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Refuel> refuels;

    // === 생성 메서드 === //
    public static Car createCar(CarType carType, String carNumber) {
        Car car = new Car();
        car.type = carType;
        car.number = carNumber;
        car.fuel = 0L;
        return car;
    }

    public void edit(CarForm carForm) throws NumberFormatException {
        if (carForm.getNumber() != null) {
            this.number = carForm.getNumber();
        }
        if (carForm.getFuelAmount() != null) {
            this.fuel = Long.parseLong(carForm.getFuelAmount());
            if (fuel < 0 || fuel > this.type.getFuelCapacity()) {
                throw new NumberFormatException("유효하지 않은 연료량입니다.");
            }
        }
    }

    public void fueling(Long amount) {
        this.fuel += amount;
    }

}
