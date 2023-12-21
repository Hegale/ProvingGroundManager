package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.UserDto;
import pg.provingground.dto.admin.UserSearchForm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserSearchRepository {
    private final EntityManager em;

    public List<UserDto> findByCriteria(UserSearchForm searchForm) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchForm.getUserId() != null) {
            predicates.add(cb.equal(user.get("userId"), searchForm.getUserId()));
        }
        if (searchForm.getRole() != null) {
            predicates.add(cb.equal(user.get("role"), searchForm.getRole()));
        }
        if (StringUtils.hasText(searchForm.getUsername())) {
            predicates.add(cb.like(user.get("username"), "%" + searchForm.getUsername() + "%"));
        }
        if (StringUtils.hasText(searchForm.getNickname())) {
            predicates.add(cb.like(user.get("nickname"), "%" + searchForm.getNickname() + "%"));
        }
        if (StringUtils.hasText(searchForm.getPhoneNum())) {
            predicates.add(cb.like(user.get("phoneNum"), "%" + searchForm.getPhoneNum() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<User> query = em.createQuery(cq);
        List<User> resultList = query.getResultList();

        return resultList.stream()
                .map(u -> new UserDto(u.getUserId(), u.getRole(), u.getUsername(), u.getNickname(), u.getPhoneNum()))
                .collect(Collectors.toList());
    }
}
