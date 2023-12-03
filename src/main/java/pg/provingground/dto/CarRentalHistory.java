package pg.provingground.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
/** 차량 대여 내역의 목록에 넘겨주기 위한 dto */
public class CarRentalHistory {

    private Long carRentalId;

    private String carName;

    private String rentalTime;

    private String returned;

}
