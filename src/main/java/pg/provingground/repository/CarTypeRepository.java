package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.CarType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarTypeRepository {
    private final EntityManager em;

    public void save(CarType carType) {
        em.persist(carType);
    }

    public CarType findOne(Long id) {
        return em.find(CarType.class, id);
    }

    public List<CarType> findAll() {
        return em.createQuery("select c from CarType c", CarType.class).getResultList();
    }

    /** 이름을 통한 차종 검색 */
    public List<CarType> findByName(String name) {
        String jpql = "select t from CarType t where t.name like :name";
        return em.createQuery(jpql, CarType.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    // TODO: 코드 최적화
    /** 조건과 일치하는 차종 검색 (엔진, 차종) */
    public List<CarType> findByCondition(String engine, String type) {
        String jpql = "select t from CarType t";
        if (!engine.isEmpty() || !type.isEmpty()) {
            jpql += " where ";
        }
        if (!engine.isEmpty()) {
            jpql += "t.engine like :engine";
        }
        if (!engine.isEmpty() && !type.isEmpty()) {
            jpql += " and";
        }
        if (!type.isEmpty()) {
            jpql += "t.type like :type";
        }

        TypedQuery<CarType> query = em.createQuery(jpql, CarType.class);

        // 파라미터 바인딩
        if (!engine.isEmpty()) {
            query.setParameter("engine", "%" + engine + "%");
        }
        if (!type.isEmpty()) {
            query.setParameter("type", "%" + type + "%");
        }

        return em.createQuery(jpql, CarType.class)
                .getResultList();
    }

}