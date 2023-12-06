package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        System.out.println("안녕하세요!!");
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    /** 스프링 시큐리티에서 로그인 시 호출, 유저 정보를 받아오기 위함 */
    public User findByLoginId(String id) {
        return em.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public void delete(User user) {
        if (user != null) {
            em.remove(user);
        }
    }

    // TODO: 조건에 따른 검색들 구현하기

}
