package pg.provingground.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.UserDto;
import pg.provingground.dto.admin.UserForm;
import pg.provingground.dto.admin.UserSearchForm;
import pg.provingground.dto.form.JoinForm;
import pg.provingground.dto.form.LoginForm;
import pg.provingground.repository.UserRepository;
import pg.provingground.repository.UserSearchRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // readOnly=true 옵션도 고려
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSearchRepository userSearchRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * [관리자] 유저 검색
     */
    public List<UserDto> getUsersByConditions(UserSearchForm userSearchForm) {
        return userSearchRepository.findByCriteria(userSearchForm);
    }

    /**
     * [관리자] 유저 삭제
     */
    @Transactional
    public void delete(Long userId) {
        // 유저가 존재하는 경우에만 삭제
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
    }

    /**
     * [관리자] 유저 정보 수정
     */
    @Transactional
    public void edit(User user, UserForm userForm) {
        userForm.setPassword(encoder.encode(userForm.getPassword()));
        user.edit(userForm);
    }

    /**
     * username 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     * */
    public boolean checkLogInDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * 회원가입 기능 1
     * 비밀번호를 그대로 저장.
     * 중복체크는 Contoller에서 진행 -> 에러메시지 출력 위함!
     * */
    @Transactional
    public void join(JoinForm form) {
        userRepository.save(form.toEntity());
    }

    /**
     * 회원가입 기능 2
     * 비밀번호를 암호화해서
     * 중복체크는 Contoller에서 진행 -> 에러메시지 출력 위함!
     * */
    @Transactional
    public void join2(JoinForm form) {
        userRepository.save(form.toEntity(encoder.encode(form.getPassword())));
    }

    /**
     * 로그인 기능
     * 화면에서 로그인 정보를 입력받아 usernamae과 password가 일치하면 User return
     * username이 존재하지 않거나 password가 일치하지 않으면 Null return
     */
    public User login(LoginForm form) {
        Optional<User> optionalUser = userRepository.findByUsername(form.getUsername());

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(form.getPassword())) {
            return null;
        }

        return user;
    }

    /**
     * userId를 입력받아 user를 return.
     * 인증, 인가 시 사용
     * userId가 null이거나 userId로 찾아온 user가 없으면 Null return
     * 존재하면 user return
     */
    public User getLoginUserById(Long userId) {
        if (userId == null) return null;

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    /**
     * username(String)을 입력받아 user를 return
     * 인증, 인가 시 사용
     */
    public User getLoginUserByUsername(String username) {
        if (username == null) return null;

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }


}
