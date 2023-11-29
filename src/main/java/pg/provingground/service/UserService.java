package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.User;
import pg.provingground.repository.UserRepository;

import java.util.List;
@Service
@Transactional(readOnly = true) // readOnly=true 옵션도 고려
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /** 회원가입 */
    @Transactional
    public Long join(User user) {
        // TODO: user정보 DTO로 받아오기
        // TODO: 유효한 회원인지 등의 유효성 검사 필요
        userRepository.save(user);
        return user.getUserId();
    }

    public List<User> findUsers() {
        // TODO: DTO로 변환해서 반환하기
        // 근데 어차피 이거 관리자기능이기는 함...
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

}
