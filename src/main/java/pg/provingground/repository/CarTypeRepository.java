package pg.provingground.repository;

import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import pg.provingground.domain.CarType;

import java.util.List;

public interface CarTypeRepository extends JpaRepository<CarType, Long> {

    public CarType findOne(Long id);

    public List<CarType> findByName(String name);
    public List<CarType> findByCondition(String engine, String type, String name);

}
