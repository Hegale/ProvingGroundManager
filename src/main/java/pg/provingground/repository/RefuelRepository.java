package pg.provingground.repository;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.JpaRepository;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.RefuelDto;
import pg.provingground.dto.admin.RefuelSearchForm;

import java.time.LocalDateTime;
import java.util.List;

public interface RefuelRepository extends JpaRepository<Refuel, Long> {
    public Refuel findOne(Long id);
    public List<Refuel> findAllByUser(User user);
    public List<Refuel> findAllByUserAndTimeInterval(User user, LocalDateTime startDate, LocalDateTime endDate);
    public List<Refuel> findUserRefuel(Long userId);
    public List<RefuelDto> searchRefuels(RefuelSearchForm searchForm);

}
