package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.CarType;
import pg.provingground.repository.CarTypeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarTypeService {

    private final CarTypeRepository carTypeRepository;

    //TODO: '차종' 추가, 변경 메소드. 가 필요할까?

    public List<CarType> findCarTypes(){
        return carTypeRepository.findAll();
    }

    public CarType findOne(Long carTypeId) {
        return carTypeRepository.findOne(carTypeId);
    }

    // TODO: 조건에 따른 차종 검색 서비스 구현

}
