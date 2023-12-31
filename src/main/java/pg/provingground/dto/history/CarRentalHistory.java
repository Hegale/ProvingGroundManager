package pg.provingground.dto.history;


import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.FileMetaData;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
/** 차량 대여 내역의 목록에 넘겨주기 위한 dto */
public class CarRentalHistory {

    private Long carRentalId;

    // 별도로 취소 여부는 기록하지 않고, 반납 = 취소를 동일시
    private String returned;

    /* 출력을 위해 변환된 값들 */
    private String carName;

    private String userName;

    private String fileName;

    private String filePath;

    private Long userId;

    private LocalDateTime startTime;

    /* 대여 시행일 이전이며, 선반납되지 않았다면 */
    private String cancelable;

    /* 취소 불가하며, 아직 반납되지 않았다면 반납 가능. */
    private String returnable;

    // TODO: fetch join 고려
    public CarRentalHistory(CarRental carRental) {
        carRentalId = carRental.getCarRentalId();
        returned = carRental.getReturned();
        startTime = carRental.getStartTime();
        carName = carRental.getCar().getType().getName();
        cancelable = (returned.equals("N") && startTime.isAfter(LocalDate.now().plusDays(1).atStartOfDay()))
                ? "Y" : "N";
        returnable = (returned.equals("N") && cancelable.equals("N")) ? "Y" : "N";
        this.userId = carRental.getUser().getUserId();
        this.userName = carRental.getUser().getNickname();
    }

    public CarRentalHistory(Long carRentalId, String carName, LocalDateTime startTime) {
        this.carRentalId = carRentalId;
        this.carName = carName;
        this.startTime = startTime;
    }

    public CarRentalHistory(Long carRentalId, String carName, LocalDateTime startTime, FileMetaData fileMetaData) {
        this.carRentalId = carRentalId;
        this.carName = carName;
        this.startTime = startTime;
        if (fileMetaData != null) {
            this.fileName = fileMetaData.getFileName();
            this.filePath = fileMetaData.getFilePath();
        }
    }

}
