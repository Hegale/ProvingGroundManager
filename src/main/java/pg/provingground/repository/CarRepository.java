package pg.provingground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    public Car findOne(Long id);

    public List<Car> findByNumber(String number);
    public boolean isDuplicateCarNumber(String carNumber);
    public List<Car> findByCarTypes(List<CarType> types);
    //public void delete(Car car);
    public long countCarsPerCarType(Long carTypeId);
    public List<Car> findByCarType(Long typeId);
}
