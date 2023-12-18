package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.CarType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarTypeRepositoryImpl {
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

    /** 조건과 일치하는 차종 검색 (엔진, 차종) */
    public List<CarType> findByCondition(String engine, String type, String name) {
        String jpql = getSearchString(engine, type, name);

        TypedQuery<CarType> query = em.createQuery(jpql, CarType.class);

        // 파라미터 바인딩
        if (engine != null && !engine.isEmpty()) {
            query.setParameter("engine", "%" + engine + "%");
        }
        if (type != null && !type.isEmpty()) {
            query.setParameter("type", "%" + type + "%");
        }
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }

        return query.getResultList();
    }

    private String getSearchString(String engine, String type, String name) {
        String jpql = "select t from CarType t";
        boolean hasCondition = false;
        if (engine != null && !engine.isEmpty()) {
            jpql += " where t.engine like :engine";
            hasCondition = true;
        }
        if (type != null && !type.isEmpty()) {
            jpql += (hasCondition ? " and" : " where") + " t.type like :type";
            hasCondition = true;
        }
        if (name != null && !name.isEmpty()) {
            jpql += (hasCondition ? " and" : " where") + " t.name like :name";
            hasCondition = true;
        }
        return jpql;
    }

}