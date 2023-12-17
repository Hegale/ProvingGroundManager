package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.dto.form.CarSearchForm;
import pg.provingground.domain.CarType;
import pg.provingground.repository.CarTypeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarTypeService {

    private final CarTypeRepository carTypeRepository;

    //TODO: '차종' 추가, 변경 메소드...?

    public List<CarType> findCarTypes(){
        return carTypeRepository.findAll();
    }

    public CarType findOne(Long carTypeId) {
        return carTypeRepository.findOne(carTypeId);
    }

    /** 조건 (차종, 엔진, 차량이름) 을 통한 검색 서비스. 미입력 조건은 빈 문자열 ""로 전달된다. */
    public List<CarType> findCarTypesByCondition(CarSearchForm searchForm) {
        return carTypeRepository.findByCondition(searchForm.getEngine(), searchForm.getType(), searchForm.getName());
    }

}
