package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

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

    private String partner; // 별도의 partner를 두어 여러 협력사를 관리하는 것 고려하기

    // TODO: 기타 NULL 가능한 속성 추가
    //private LineSt path;

    @OneToMany(mappedBy = "test")
    private List<CarRental> carRentalIds;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ground_rental_id")
    private GroundRental groundRentalID;

}
