package pg.provingground.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataBuilder {

    private final CarRepository carRepository;
    private final CarTypeRepository carTypeRepository;
    private final GroundRepository groundRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    /** 차종 테스트 데이터 생성 */
    public void buildCarTypeTestData() {

        List<CarType> carTypes = new ArrayList<CarType>();
        List<String> names = List.of(
                "더 뉴 아반떼", "쏘나타 디 엣지", "디 올 뉴 그랜저",
                "디 올 뉴 싼타페", "베뉴", "디 올 뉴 코나", "투싼",
                "팰리세이드", "아이오닉 6", "넥쏘");
        List<String> types = List.of(
                "승용", "승용", "승용", "SUV", "SUV", "SUV", "SUV", "SUV", "수소/전기차", "수소/전기차");
        List<String> engines = List.of(
                "가솔린", "가솔린", "가솔린", "가솔린", "가솔린", "가솔린", "디젤", "디젤", "전기", "수소");
        for (int i = 0; i < 10; ++i) {
            CarType carType = new CarType();
            carType.setName(names.get(i));
            carType.setType(types.get(i));
            carType.setEngine(engines.get(i));
            carTypeRepository.save(carType);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void buildCarTestData() {
        for (int i = 0; i < 30; ++i){
            Car car = new Car();
            CarType carType = carTypeRepository.findOne((long)i % 10 + 1);
            car.setType(carType);
            car.setNumber("12가 3456");
            carRepository.save(car);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void buildGroundData() {
        List<String> names = List.of(
                "다목적 주행 코스", "제동 코스", "마른 노면 서킷", "고속주회로",
                "젖은 노면 서킷", "킥 플레이트 코스", "젖은 언선회 코스", "오프로드 코스"
        );
        List<String> descriptions = List.of(
                "슬라럼, 짐카나 등 다양한 모듈로 구성되어 있는 코스로 드라이빙의 기본기부터 자동차의 가속 성능까지 종합적으로 경험할 수 있습니다.",
                "다양한 노면과 상황 속에서 브레이크의 성능을 최대로 이끌어 내는 코스로 위급 상황 시 안전하게 대처하는 테크닉을 익힐 수 있습니다.",
                "3.4km, 16개의 코너로 구성된 서킷에서 드라이빙 스킬을 종합적으로 경험할 수 있는 코스입니다. 레벨에 따라 두 개의 코스로 나누어 경험할 수 있습니다.",
                "38.87도의 뱅크각의 4.6km 오벌 트랙에서 자동차의 한계 속도와 극한의 중력 가속도를 체험할 수 있습니다.",
                "11개 코너, 1.6km의 젖은 서킷을 달리는 코스로 젖은 노면에서도 안전하게 자동차를 컨트롤하는 방법을 배울 수 있습니다.",
                "돌발 상황에 대처하는 능력을 키우는데 특화된 코스로 킥 플레이트의 인위적인 힘에 의해 불안정해진 차량을 안전하게 컨트롤하는 능력을 키울 수 있습니다.",
                "서로 다른 노면 위에서 눈으로만 보았던 드리프트를 실제로 경험해보는 코스입니다. 카운터 스티어링을 비롯한 고급 기술을 경험할 수 있습니다.",
                "보기만 해도 아찔한 구간를 통과하며 SUV의 내구성과 오프로드 성능을 체험하는 코스입니다. 언덕 경사, 모랫길, 계단 등 11가지 즐거운 장애물이 기다리고 있습니다."
                );
        for (int i = 0; i < 8; ++i) {
            Ground ground = new Ground();
            ground.setName(names.get(i));
            ground.setDescription(descriptions.get(i));
            ground.setDistance(100);
            ground.setCar_maximum(3);
            ground.setFuel_consumption(3000); // ml 단위
            groundRepository.save(ground);
        }
    }

}
