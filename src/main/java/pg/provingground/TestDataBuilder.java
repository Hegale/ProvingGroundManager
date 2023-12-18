package pg.provingground;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.*;
import pg.provingground.repository.*;
import pg.provingground.service.TestService;
import pg.provingground.service.UserService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TestDataBuilder {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CarTypeRepository carTypeRepository;
    private final GroundRepository groundRepository;
    private final CarRentalRepository carRentalRepository;
    private final GroundRentalRepository groundRentalRepository;
    private final StationRepositoryImpl stationRepository;
    private final TestRepository testRepository;
    private final UserService userService;
    private final TestService testService;
    private final EntityManager em;
    private final BCryptPasswordEncoder encoder;

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    @Transactional
    /** 차종 테스트 데이터 생성 */
    public void buildCarTypeTestData() {

        List<String> names = List.of(
                "더 뉴 아반떼", "쏘나타 디 엣지", "디 올 뉴 그랜저",
                "디 올 뉴 싼타페", "베뉴", "디 올 뉴 코나", "투싼",
                "팰리세이드", "아이오닉 6", "넥쏘");
        List<String> types = List.of(
                "승용", "승용", "승용", "SUV", "SUV", "SUV", "SUV", "SUV", "수소/전기차", "수소/전기차");
        List<String> engines = List.of(
                "가솔린", "가솔린", "가솔린", "가솔린", "가솔린", "가솔린", "디젤", "디젤", "전기", "수소");
        List<Double> fuelEfficiencys = List.of(
                15.3, 13.5, 11.7, 11.0, 13.7, 13.6, 14.5, 12.4, 6.2, 96.2);
        List<Integer> displacements = List.of(
                1598, 1999, 3470, 2497, 1598, 1999, 1998, 3778, 0, 0);

        List<String> descriptions = List.of(
                "더욱 스타일리시하게. 한층 더 진보된 기본기와 함께, 디테일이 다른 새로운 라이프스타일이 시작됩니다.",
                "탄탄한 헤리티지와 선도적인 디자인, 첨단 기술의 조합. 익숙함도 완전히 새롭게, 쏘나타 디 엣지.",
                "세상의 기대와 예상을 뛰어넘어 스스로를 혁신한 그랜저. 전통과 미래, 세대와 취향, 기술과 감성의 경계를 지운 새로운 경험을 선사합니다.",
                "완전히 새로운 상품성으로 차별화된 경험과 다양한 라이프 스타일을 제안합니다.",
                "혼영, 혼밥, 혼휴. 혼자가 좋은 당신에게 오로지 당신을 위한 즐거움, 지금은 혼라이프를 즐길 시간.",
                "미래지향적인 새로운 스타일과 차급을 넘나드는 상품성으로 새로운 차원의 경험을 선사합니다.",
                "당신만의 특별한 공간 투싼. 더 오래 머물수록 그 가치는 빛이 납니다.",
                "현대자동차의 플래그십 SUV 팰리세이드와 함께 당신만의 세상을 향한 멋진 여정을 준비하십시오.",
                "나의 취향과 감각으로 만들어가는 새로운 세상이 열립니다. 내가 만드는 세상, IONIQ 6를 경험하세요.",
                "수소와 산소의 결합으로 어떠한 오염물질 없이 오직 에너지와 물만을 발생시키고 달리면서 PM2.5 이하의 초미세먼지까지 걸러내는 궁극의 친환경차 NEXO. '미래 자동차 기술의 현재화'라는 개발 철학 아래, 가장 진보된 기술로 완성된 미래 모빌리티를 가장 먼저 경험하십시오."
        );

        List<Long> fuelCapacitys = List.of(47000L, 60000L, 50000L, 67000L, 45000L, 47000L, 52000L, 71000L, 697L, 156000L);

        for (int i = 0; i < 10; ++i) {
            CarType carType = new CarType();
            carType.setName(names.get(i));
            carType.setType(types.get(i));
            carType.setEngine(engines.get(i));
            carType.setFuelEfficiency(fuelEfficiencys.get(i));
            carType.setDisplacement(displacements.get(i));
            carType.setDescription(descriptions.get(i));
            carType.setFuelCapacity(fuelCapacitys.get(i));
            carTypeRepository.save(carType);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    @Order(1)
    // 유저 테스트 데이터 생성
    public void buildUserTestData() {

        List<String> names = List.of(
                "김주연", "박지환", "김형진", "홍지민");
        List<String> ids = List.of(
                "19011051", "32916610", "12702294", "73113289");
        List<String> passwds = List.of(
                "juyeon5491", "jihwan1234", "kim1234", "jimin1234");
        List<String> phoneNums = List.of(
                "010-5919-5491", "010-3153-3121", "010-9312-1234", "010-8214-5555");
        for (int i = 0; i < 4; ++i) {
            User user = User.createUser(
                    ids.get(i), encoder.encode(passwds.get(i)), names.get(i), UserRole.USER, phoneNums.get(i));
            em.persist(user);
        }
        User user = User.createUser("a", encoder.encode("a"), "tester", UserRole.USER, "000");
        em.persist(user);
    }


    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    @Transactional
    public void buildCarTestData() {

        for (int i = 0; i < 20; ++i){
            Car car = new Car();
            CarType carType = carTypeRepository.findOne((long)i % 10 + 1);
            car.setType(carType);
            car.setNumber(generateRandomString());
            car.setFuel(0L);
            carRepository.save(car);
        }
    }


    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    @Transactional
    public void buildGroundData() {
        List<String> names = List.of(
                "다목적 주행 코스", "제동 코스", "마른 노면 서킷", "고속주회로",
                "젖은 노면 서킷", "킥 플레이트 코스", "젖은 원선회 코스", "오프로드 코스"
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
            //ground.setFuel_consumption(3000); // ml 단위
            groundRepository.save(ground);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(3)
    @Transactional
    public void buildCarRentalData() {
        User user = userService.getLoginUserById(1L); // pikachu
        for (int i = 0; i < 12; ++i) {
            Car car = carRepository.findOne((long) i + 1);
            CarRental carRental = CarRental.createCarRental(user, car,
                    LocalDateTime.now().plusDays(i + 1).with(LocalTime.MIDNIGHT).withHour(10));
            carRentalRepository.save(carRental);
        }
        // 3번 차량에 대한 테스트 데이터 생성
        Car car = carRepository.findOne(3L);
        CarRental carRental;

        // 3번 차량 두 대를 5일 후에 한하여 전 시간 매진
        for (int i = 0; i < 5; ++i) {
            carRental = CarRental.createCarRental(user, car,
                    LocalDateTime.now().plusDays(5).with(LocalTime.MIDNIGHT).withHour(10 + 2 * i));
            carRentalRepository.save(carRental);
        }
        car = carRepository.findOne(13L);
        for (int i = 0; i < 5; ++i) {
            carRental = CarRental.createCarRental(user, car,
                    LocalDateTime.now().plusDays(5).with(LocalTime.MIDNIGHT).withHour(10 + 2 * i));
            carRentalRepository.save(carRental);
        }

    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(3)
    @Transactional
    public void buildGroundRentalData() {
        User user = userService.getLoginUserById(1L); // pikachu
        for (int i = 0; i < 12; ++i) {
            Ground ground = groundRepository.findOne((long) i % 8 + 1);
            GroundRental groundRental = GroundRental.createGroundRental(user, ground,
                    LocalDateTime.now().plusDays(i + 1).with(LocalTime.MIDNIGHT).withHour(10));
            groundRentalRepository.save(groundRental);
        }
    }


    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    @Transactional
    public void buildStationData() {
        List<String> names = List.of("주유구 A", "주유구 B", "주유구 C", "주유구 D", "주유구 E");
        List<FuelType> fuelTypes = List.of(FuelType.GASOLINE, FuelType.DIESEL, FuelType.ELECTRIC, FuelType.LPG, FuelType.HYDROGEN);
        for (int i = 0; i < 5; ++i) {
            Station station = Station.createStation(names.get(i),fuelTypes.get(i));
            stationRepository.save(station);
        }
    }


    @EventListener(ApplicationReadyEvent.class)
    @Order(4)
    @Transactional
    public void buildTestData() {
        User user = userService.getLoginUserById(1L); // juyeon
        List<String> titles = List.of(
                "차량 제동거리 비교실험", "젖은 노면에서의 주행경로 오차", "오프로드 환경에서의 연료효율성", "차량 주행 안정성 비교", "최고속도 시 차량상태 비교확인");
        String contents = "첨부파일을 확인해 주세요.";
        List<String> partners = List.of(
                "A사", "A사, B사", "K사", "H사", "A사, B사, K사, H사");


        for (long i = 0; i < 5; ++i) {
            GroundRental groundRental = groundRentalRepository.findOne(i + 1);
            List<CarRental> carRentals = new ArrayList<>();
            carRentals.add(carRentalRepository.findOne(i + 1));
            LocalDateTime date = groundRental.getStartTime();
            Test test = Test.createTest(titles.get((int)i), contents, partners.get((int)i), date,
                    user, carRentals, groundRental);
            testRepository.save(test);
            // 각 테스트를 carRental에 적용
            for (CarRental carRental : carRentals) {
                carRental.setTest(test);
            }
        }

    }


    private String generateRandomString() {
        Random random = new Random();

        // 01부터 69까지의 숫자 생성
        int number = 1 + random.nextInt(69);
        String numberStr = String.format("%02d", number);

        // 주어진 한글 문자들 중 하나 선택
        String[] koreanChars = {"가", "나", "다", "라", "마", "거", "너", "더", "러", "머", "버", "서", "어", "저", "고", "노", "도", "로", "모", "보", "소", "오", "조", "구", "누", "두", "루", "무", "부", "수", "우", "주"};
        String chosenChar = koreanChars[random.nextInt(koreanChars.length)];

        // 랜덤한 네 자리 숫자 생성
        int randomNum = 1000 + random.nextInt(9000);

        return numberStr + chosenChar + " " + randomNum;
    }

}
