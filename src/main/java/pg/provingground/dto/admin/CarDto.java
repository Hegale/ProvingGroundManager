package pg.provingground.dto.admin;

import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;

@Getter @Setter
/** 차량 dto. 사용범위 : 관리자의 전체 차량 조회(+수정), 주유 시의 차량 조회 */
public class CarDto {

    private Long carId;
    private String number;
    private Long fuel;

    private String name;
    private String type;
    private String engine;

    public CarDto(Long carId, String number, Long fuel,
                  String name, String type, String engine) {
        this.carId = carId;
        this.number = number;
        this.fuel = fuel;
        this.name = name;
        this.type = type;
        this.engine = engine;

    }

}
