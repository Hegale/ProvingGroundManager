package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import pg.provingground.dto.GroundRentalHistory;
import pg.provingground.dto.TestForm;

import java.util.List;

@Entity
@RequiredArgsConstructor
public class Test {
    @Id
    @GeneratedValue
    @Column(name = "test_id")
    private Long testId;

    private String type;

    //private String path;

    private String result;

    private String partners; // 별도의 partner를 두어 여러 협력사를 관리하는 것 고려하기

    private String title;

    private String contents;

    // TODO: 기타 NULL 가능한 속성 추가
    //private LineSt path;

    @OneToMany(mappedBy = "test")
    private List<CarRental> carRentals;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ground_rental_id")
    private GroundRental groundRental;

    // === 생성 메서드 === //
    public static Test createTest(String title, String contents, String partners,
                           List<CarRental> carRentals, GroundRental groundRental) {
        Test test = new Test();
        test.title = title;
        test.contents = contents;
        if (partners!= null) {
            test.partners = partners;
        }
        test.carRentals = carRentals;
        test.groundRental = groundRental;
        return test;
    }

}
